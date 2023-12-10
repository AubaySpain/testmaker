package com.github.jorge2m.testmaker.repository.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.TestRunBean;
import static com.github.jorge2m.testmaker.repository.jdbc.dao.Utils.DateFormat.*;


public class TestRunsDAO {
	
	private final ConnectorBD connector;

	private static final String SQL_SELECT_TESTRUNS_SUITE = 
		"SELECT " +
		    "IDEXECSUITE, " +
		    "SUITE, " +
		    "NAME, " +
		    "DEVICE, " +
		    "RESULT, " +
		    "INICIO, " +
		    "FIN, " +
		    "TIME_MS, " +
		    "NUMBER_TESTCASES, " +
		    "BROWSER " +
		"FROM TESTRUNS  " + 
		"WHERE IDEXECSUITE = ? " + 
		"ORDER BY INICIO";

	private static final String SQL_INSERT_TESTRUN = 
		"INSERT INTO TESTRUNS (" +
			"IDEXECSUITE, " + 
			"SUITE, " + 
			"NAME, " + 
			"DEVICE, " + 
			"RESULT, " + 
			"INICIO, " + 
			"FIN, " + 
			"TIME_MS, " + 
			"NUMBER_TESTCASES, " + 
			"BROWSER ) " + 
		"VALUES (?,?,?,?,?,?,?,?,?,?)";
	
	public static final String SQL_DELETE_TESTRUN =
		    "DELETE FROM TESTRUNS " +
		    "WHERE IDEXECSUITE = ?";

	public TestRunsDAO(ConnectorBD connector) {
		this.connector = connector;
	}

    public List<TestRunBean> getListTestRuns(String idSuite) throws Exception {
    	List<TestRunBean> listTestRuns = new ArrayList<>();
        try (Connection conn = connector.getConnection();
            PreparedStatement select = conn.prepareStatement(SQL_SELECT_TESTRUNS_SUITE)) {
            select.setString(1, idSuite);
            try (ResultSet resultado = select.executeQuery()) {
                while (resultado.next()) {
                	listTestRuns.add(getTestRun(resultado));
                }
            }
            return listTestRuns;
        } 
        catch (SQLException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        } 
    }   
    
    private TestRunBean getTestRun(ResultSet rowTestRun) throws Exception {
    	TestRunBean testRunData = new TestRunBean();
    	testRunData.setIdExecSuite(rowTestRun.getString("IDEXECSUITE"));
    	testRunData.setSuiteName(rowTestRun.getString("SUITE"));
    	testRunData.setName(rowTestRun.getString("NAME"));
    	testRunData.setResult(State.from(rowTestRun.getString("RESULT")));
    	testRunData.setDevice(rowTestRun.getString("DEVICE"));
    	
        String inicioDate = rowTestRun.getString("INICIO");
        testRunData.setInicioDate(Utils.getDateFormat(ToSeconds).parse(inicioDate));
        String finDate = rowTestRun.getString("FIN");
        testRunData.setInicioDate(Utils.getDateFormat(ToSeconds).parse(finDate));
    	
    	testRunData.setDurationMillis(rowTestRun.getFloat("TIME_MS"));
    	testRunData.setNumberTestCases(rowTestRun.getInt("NUMBER_TESTCASES"));
    	testRunData.setDriver(rowTestRun.getString("BROWSER"));
    	
    	TestCasesDAO testCasesDAO = new TestCasesDAO(connector);
    	testRunData.setListTestCase(testCasesDAO.getListTestCases(rowTestRun.getString("IDEXECSUITE")));
    	
    	return testRunData;
    }

    public void insertTestRun(TestRunBean testRun) {
        try (var conn = connector.getConnection();
             var insert = conn.prepareStatement(SQL_INSERT_TESTRUN)) {

            insert.setString(1, testRun.getIdExecSuite());
            insert.setString(2, testRun.getSuiteName());
            insert.setString(3, testRun.getName());
            insert.setString(4, testRun.getDevice());
            insert.setString(5, testRun.getResult().name());
            insert.setString(6, Utils.getDateFormat(ToSeconds).format(testRun.getInicioDate()));
            insert.setString(7, Utils.getDateFormat(ToSeconds).format(testRun.getFinDate()));
            insert.setFloat(8, testRun.getDurationMillis());
            insert.setInt(9, testRun.getNumberTestCases());
            insert.setString(10, testRun.getDriver());

            insert.executeUpdate();

        } catch (SQLException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

	public void deleteTestRuns(String idSuite) {
		try (Connection conn = connector.getConnection();
			PreparedStatement delete = conn.prepareStatement(SQL_DELETE_TESTRUN)) {
			delete.setString(1, idSuite);
			delete.executeUpdate();
		} 
		catch (SQLException | ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		} 
	}
	
}
