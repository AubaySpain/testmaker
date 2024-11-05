package com.github.jorge2m.testmaker.conf.defaultstorer;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteConfig.LockingMode;

import com.github.jorge2m.testmaker.conf.ConstantesTM;
import com.github.jorge2m.testmaker.domain.ComparedSuite;
import com.github.jorge2m.testmaker.domain.RepositoryI;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteBean;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseBean;
import com.github.jorge2m.testmaker.domain.suitetree.TestRunBean;
import com.github.jorge2m.testmaker.repository.jdbc.dao.AlertsDAO;
import com.github.jorge2m.testmaker.repository.jdbc.dao.ComparedSuitesDAO;
import com.github.jorge2m.testmaker.repository.jdbc.dao.ConnectorBD;
import com.github.jorge2m.testmaker.repository.jdbc.dao.StepsDAO;
import com.github.jorge2m.testmaker.repository.jdbc.dao.SuitesDAO;
import com.github.jorge2m.testmaker.repository.jdbc.dao.TestCasesDAO;
import com.github.jorge2m.testmaker.repository.jdbc.dao.TestRunsDAO;
import com.github.jorge2m.testmaker.repository.jdbc.dao.ValidationsDAO;
import com.github.jorge2m.testmaker.service.notifications.DataAlert;
import com.github.jorge2m.testmaker.testreports.html.ResourcesExtractor;

public class RepositorySQLite implements RepositoryI {
	
	private boolean sqliteBdGrabed = false;
	private final ConnectorBD connector = getConnectorBD(); 
	private final SuitesDAO suitesDAO = new SuitesDAO(connector);
	private final ComparedSuitesDAO comparedSuitesDAO = new ComparedSuitesDAO(connector);
	private final TestRunsDAO testRunsDAO = new TestRunsDAO(connector);
	private final TestCasesDAO testCasesDAO = new TestCasesDAO(connector);
	private final StepsDAO stepsDAO = new StepsDAO(connector);
	private final ValidationsDAO validationsDAO = new ValidationsDAO(connector);
	private final AlertsDAO alertsDAO = new AlertsDAO(connector);
	
	@Override
	public void store(SuiteBean suite, StoreUntil until) {
		storeSuiteAndChildren(suite, until);
	}
	
	@Override
	public void storeAlert(Check check, ChecksTM checksParent) {
		alertsDAO.insertAlert(check, checksParent);
	}
	
	@Override
	public List<DataAlert> getAlertsInPeriod(int periodMinutes, Check check, ChecksTM checksParent) {
		List<DataAlert> alertsAll = alertsDAO.getAlerts(check, checksParent);
		LocalDateTime fechaFrom = LocalDateTime.now().minusMinutes(periodMinutes);
		return alertsAll.stream()
			.filter(s -> convertToLocalDateTime(s.getFecha()).isAfter(fechaFrom))
			.collect(Collectors.toList());
	}
	
	@Override
	public void delete(String suiteIdExec) {
		deleteSuiteAndChildren(suiteIdExec);
	}
	
	@Override
	public SuiteBean getSuite(String suiteExecId) throws Exception {
		return (suitesDAO.getSuite(suiteExecId));
	}
	
	@Override
	public synchronized List<SuiteBean> getListSuitesBetween(Date desde, Date hasta) throws Exception {
		return (suitesDAO.getSuitesBetween(desde, hasta));
	}
	
	@Override
	public synchronized List<SuiteBean> getListSuites() throws Exception {
		return (suitesDAO.getListSuitesIdDesc());
	}
	
	@Override
	public synchronized List<TestCaseBean> getListTestCases(String suiteExecId) throws Exception {
		return (testCasesDAO.getListTestCases(suiteExecId));
	}
	
	@Override
	public synchronized void insertComparedSuites(SuiteBean suite1, SuiteBean suite2) {
		comparedSuitesDAO.insertComparedSuite(suite1, suite2);
	}
	
	@Override
	public synchronized Optional<ComparedSuite> getComparedSuite(String idSuite1, String idSuite2) {
		return comparedSuitesDAO.getComparedSuite(idSuite1, idSuite2);
	}
	
	@Override
	public boolean removeBD() {
		String pathBD = getSQLiteFilePathAutomaticTestingSchema();
		return new File(pathBD).delete();
	}
	
	private ConnectorBD getConnectorBD() {
		return ( 
			new ConnectorBD() {
				@Override
				public Connection getConnection() throws ClassNotFoundException, SQLException {
					grabSqliteBDifNotExists();
					Connection conn;
					Class.forName("org.sqlite.JDBC");
					System.getProperty("user.dir");
					SQLiteConfig config = new SQLiteConfig();
					//config.setReadOnly(forReadOnly); 
					config.setBusyTimeout(30000);
					config.setLockingMode(LockingMode.NORMAL);
					
					conn = DriverManager.getConnection(
							"jdbc:sqlite:" + 
							getSQLiteFilePathAutomaticTestingSchema(), 
							config.toProperties());
					return conn;
				}
			}
		);
	}
	
	private synchronized void storeSuiteAndChildren(SuiteBean suite, StoreUntil until) {
		if (!until.storeSuite()) {
			return;
		}
		suitesDAO.insertOrReplaceSuite(suite);
		if (!until.storeTestrun()) {
			return;
		}
		for (TestRunBean testRun : suite.getListTestRun()) {
			testRunsDAO.insertTestRun(testRun);
			if (until.storeTestcase()) {
				for (TestCaseBean testCase : testRun.getListTestCase()) {
					testCasesDAO.insertTestCase(testCase);
					if (until.storeStep()) {
						for (StepTM step : testCase.getListStep()) {
							stepsDAO.insertStep(step);
							if (until.storeValidation()) {
								for (ChecksTM checks : step.getListChecksTM()) {
									validationsDAO.insertValidation(checks);
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void deleteSuiteAndChildren(String suiteIdExec) {
		suitesDAO.deleteSuite(suiteIdExec);
		testRunsDAO.deleteTestRuns(suiteIdExec);
		testCasesDAO.deleteTestCases(suiteIdExec);
		stepsDAO.deleteSteps(suiteIdExec);
		validationsDAO.deleteValidations(suiteIdExec);
		comparedSuitesDAO.delete(suiteIdExec);
	}

	@Override
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		return connector.getConnection();
	}

	private void grabSqliteBDifNotExists() {
		if (!sqliteBdGrabed) {
			File fileSqliteBD = new File(getSQLiteFilePathAutomaticTestingSchema());
			if (!fileSqliteBD.exists()) {
				ResourcesExtractor resExtractor = ResourcesExtractor.getNew();
				resExtractor.copyDirectoryResources(
					"sqlite/", 
					getSQLitePathDirectory());
				
				sqliteBdGrabed = true;
			}
		}
	}	

	private String getSQLitePathDirectory() {
		String path = 
			System.getProperty("user.dir") + File.separator + 
			ConstantesTM.DIRECTORY_OUTPUT_TESTS + File.separator + 
			"sqlite" + File.separator;
		return path;
	}

	private String getSQLiteFilePathAutomaticTestingSchema() {
		return (
			getSQLitePathDirectory() + 
			ConstantesTM.SQLITE_FILE_AUTOMATIC_TESTING_SCHEMA);
	}
	
	private LocalDateTime convertToLocalDateTime(Date dateToConvert) {
	    return new Timestamp(dateToConvert.getTime()).toLocalDateTime();
	}
	
}
