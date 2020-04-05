package com.github.jorge2m.testmaker.repository.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.jorge2m.testmaker.conf.Log4jConfig;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.TestRunBean;
import com.github.jorge2m.testmaker.service.webdriver.maker.FactoryWebdriverMaker.WebDriverType;


public class TestRunsDAO {
    static Logger pLogger = LogManager.getLogger(Log4jConfig.log4jLogger);
    
	private final ConnectorBD connector;
    
    private static String SQLSelectTestRunsSuite = 
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
        "ORDER BY TEST, INICIO";

    private static String SQLInsertTestRun = 
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
    
    private static String SQLDeleteHistorical = 
        "DELETE FROM TESTRUNS " +
        "WHERE 	IDEXECSUITE < ?;";    
    
    public TestRunsDAO(ConnectorBD connector) {
    	this.connector = connector;
    }
    
    public List<TestRunBean> getListTestRuns(String idSuite) throws Exception {
    	List<TestRunBean> listTestRuns = new ArrayList<>();
        try (Connection conn = connector.getConnection();
            PreparedStatement select = conn.prepareStatement(SQLSelectTestRunsSuite)) {
            select.setString(1, idSuite);
            try (ResultSet resultado = select.executeQuery()) {
                while (resultado.next()) {
                	listTestRuns.add(getTestRun(resultado));
                }
            }
            return listTestRuns;
        } 
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        } 
        catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }   
    
    private TestRunBean getTestRun(ResultSet rowTestRun) throws Exception {
    	TestRunBean testRunData = new TestRunBean();
    	testRunData.setIdExecSuite(rowTestRun.getString("IDEXECSUITE"));
    	testRunData.setSuiteName(rowTestRun.getString("SUITE"));
    	testRunData.setName(rowTestRun.getString("NAME"));
    	testRunData.setResult(State.valueOf(rowTestRun.getString("RESULT")));
    	testRunData.setDevice(rowTestRun.getString("DEVICE"));
    	
        String inicioDate = rowTestRun.getString("INICIO");
        testRunData.setInicioDate(getDateFormat().parse(inicioDate));
        String finDate = rowTestRun.getString("FIN");
        testRunData.setInicioDate(getDateFormat().parse(finDate));
    	
    	testRunData.setDurationMillis(rowTestRun.getFloat("TIME_MS"));
    	testRunData.setNumberTestCases(rowTestRun.getInt("NUMBER_TESTCASES"));
    	testRunData.setWebDriverType(WebDriverType.valueOf(rowTestRun.getString("BROWSER")));
    	return testRunData;
    }
    
    public void insertTestRun(TestRunBean testRun) {
        try (Connection conn = connector.getConnection()) {
            try (PreparedStatement insert = conn.prepareStatement(SQLInsertTestRun)) {
    	        insert.setString(1, testRun.getIdExecSuite());
    	        insert.setString(2, testRun.getSuiteName()); 
    	        insert.setString(3, testRun.getName()); 
    	        insert.setString(4, testRun.getDevice()); 
    	        insert.setString(5, testRun.getResult().name()); 
	        	insert.setString(6, getDateFormat().format(testRun.getInicioDate()));
	        	insert.setString(7, getDateFormat().format(testRun.getFinDate()));
    	        insert.setFloat(8, testRun.getDurationMillis());
    	        insert.setInt(9, testRun.getNumberTestCases());
    	        insert.setString(10, testRun.getWebDriverType().name());
                insert.executeUpdate();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        } 
        catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void deleteTestRunsBefore(String idSuite) {
        try (Connection conn = connector.getConnection();
            PreparedStatement delete = conn.prepareStatement(SQLDeleteHistorical)) {
            delete.setString(1, idSuite);
            delete.executeUpdate();
         } 
         catch (SQLException ex) {
             throw new RuntimeException(ex);
         } 
         catch (ClassNotFoundException ex) {
             throw new RuntimeException(ex);
         }    
    }    
    
    private static SimpleDateFormat getDateFormat() {
    	return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
}
