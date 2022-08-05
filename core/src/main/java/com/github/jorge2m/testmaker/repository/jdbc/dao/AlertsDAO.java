package com.github.jorge2m.testmaker.repository.jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteBean;
import com.github.jorge2m.testmaker.domain.suitetree.TestRunBean;
import com.github.jorge2m.testmaker.service.notifications.DataAlert;

import static com.github.jorge2m.testmaker.repository.jdbc.dao.Utils.DateFormat.*;


public class AlertsDAO {
	
	private final ConnectorBD connector;
	
	private final static String lineSeparator = System.lineSeparator();
	
//	public static String SQLSelectAlert =
//		"SELECT " +
//			"IDEXECSUITE, " + 
//			"SUITE, " +
//			"METHOD, " + 
//			"STEP_NUMBER, " + 
//			"VALIDATION_NUMBER, " +
//			"RESULTADO, " +
//			"LEVEL," +
//			"DESCRIPTION_STEP," +
//			"DESCRIPTION_CHECK," +
//			"FECHA " +
//		"FROM ALERTS " + 
//		"WHERE DESCRIPTION_CHECK = ? AND " +
//			"METHOD_VALIDATION=?";

	public static String SQLInsertAlert = 
		"INSERT INTO ALERTS (" +
			"IDEXECSUITE, " + 
			"SUITE, " +
			"METHOD, " + 
			"STEP_NUMBER, " + 
			"VALIDATION_NUMBER, " +
			"RESULTADO, " +
			"LEVEL, " +
			"DESCRIPTION_STEP, " +
			"DESCRIPTION_CHECK, " +
			"METHOD_VALIDATION, " + 
			"FECHA) " +
		"VALUES (?,?,?,?,?,?,?,?,?,?,?)";
    
	public AlertsDAO(ConnectorBD connector) {
		this.connector = connector;
	}
	
	public void insertAlert(Check check, ChecksTM checksParent) {
		DataAlert dataAlert = DataAlert.of(check, checksParent);
		try (Connection conn = connector.getConnection()) {
			try (PreparedStatement insert = conn.prepareStatement(SQLInsertAlert)) {
				SuiteBean suite = checksParent.getSuiteParent().getSuiteBean(); 
				insert.setString(1, suite.getIdExecSuite());
				insert.setString(2, suite.getName());
				insert.setString(3, checksParent.getTestCaseParent().getNameUnique());
				insert.setInt(4, checksParent.getStepParent().getNumber());
				insert.setInt(5, dataAlert.getPositionValidationInStep());
				insert.setString(6, checksParent.getStateValidation().toString());

				insert.setString(7, dataAlert.geteLevelCheck().name());
				insert.setString(8, dataAlert.getStepDescription());
				insert.setString(9, dataAlert.getCheckDescription());
				insert.setString(10, dataAlert.getMethodValidation());
				insert.setString(11, Utils.getDateFormat(ToMillis).format(System.currentTimeMillis()));
				
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
}
