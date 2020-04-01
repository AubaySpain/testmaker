package org.aubay.testmaker.conf.defaultstorer;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.aubay.testmaker.conf.ConstantesTM;
import org.aubay.testmaker.domain.RepositoryI;
import org.aubay.testmaker.domain.suitetree.SuiteBean;
import org.aubay.testmaker.domain.suitetree.SuiteTM;
import org.aubay.testmaker.domain.suitetree.TestCaseBean;
import org.aubay.testmaker.domain.suitetree.TestCaseTM;
import org.aubay.testmaker.domain.suitetree.TestRunTM;
import org.aubay.testmaker.repository.jdbc.dao.ConnectorBD;
import org.aubay.testmaker.repository.jdbc.dao.SuitesDAO;
import org.aubay.testmaker.repository.jdbc.dao.TestCasesDAO;
import org.aubay.testmaker.repository.jdbc.dao.TestRunsDAO;
import org.aubay.testmaker.testreports.html.ResourcesExtractor;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteConfig.LockingMode;

public class RepositorySQLite implements RepositoryI {
	
	private boolean sqliteBdGrabed = false;
	private final ConnectorBD connector = getConnectorBD(); 
	private final SuitesDAO suitesDAO = new SuitesDAO(connector);
	private final TestRunsDAO testRunsDAO = new TestRunsDAO(connector);
	private final TestCasesDAO testCasesDAO = new TestCasesDAO(connector);
	
	@Override
	public void storeAll(SuiteTM suite) {
		storeSuiteAndChildren(suite);
	}
	
	@Override
	public void storeSuite(SuiteTM suite) {
		storeOnlySuite(suite);
	}
	
	@Override
	public SuiteBean getSuite(String suiteExecId) throws Exception {
		return (suitesDAO.getSuite(suiteExecId));
	}
	
	@Override
	public synchronized List<SuiteBean> getListSuitesAfter(Date fechaDesde) throws Exception {
		return (suitesDAO.getListSuitesAfter(fechaDesde));
	}
	
	@Override
	public synchronized List<SuiteBean> getListSuites() throws Exception {
		return (suitesDAO.getListSuitesIdDesc());
	}
	
	@Override
	public synchronized List<TestCaseBean> getListTestCases(String suiteExecId) throws Exception {
		return (testCasesDAO.getListTestCases(suiteExecId));
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
	
	private synchronized void storeOnlySuite(SuiteTM suite) {
		suitesDAO.insertOrReplaceSuite(suite.getSuiteBean());
	}
	
	private synchronized void storeSuiteAndChildren(SuiteTM suite) {
		suitesDAO.insertOrReplaceSuite(suite.getSuiteBean());
		for (TestRunTM testRun : suite.getListTestRuns()) {
			testRunsDAO.insertTestRun(testRun.getTestRunBean());
			for (TestCaseTM testCase : testRun.getListTestCases()) {
				testCasesDAO.insertTestCase(testCase.getTestCaseBean());
			}
		}
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
    		ConstantesTM.directoryOutputTests + File.separator + 
    		"sqlite" + File.separator;
    	return path;
    }
    
    private String getSQLiteFilePathAutomaticTestingSchema() {
    	return (
    		getSQLitePathDirectory() + 
    		ConstantesTM.SQLiteFileAutomaticTestingSchema);
    }
}
