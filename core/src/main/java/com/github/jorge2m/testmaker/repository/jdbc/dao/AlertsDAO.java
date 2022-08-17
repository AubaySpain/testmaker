package com.github.jorge2m.testmaker.repository.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.notifications.DataAlert;

public class AlertsDAO {
	
	private final ConnectorBD connector;
	


	private static final String SQLInsertAlert = 
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
			"INFO_EXECUTION, " + 
			"METHOD_VALIDATION, " +
			"URL_REPORT, " + 
			"FECHA) " +
		"VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private static final String TAG_OPERATOR = "@TagOperator";
	private static final String SQLSelectAlertsWithTag =
		"SELECT " +
			"IDEXECSUITE, " + 
			"SUITE, " +
			"METHOD, " + 
			"STEP_NUMBER, " + 
			"VALIDATION_NUMBER, " +
			"RESULTADO, " +
			"LEVEL, " +
			"DESCRIPTION_STEP, " +
			"DESCRIPTION_CHECK, " +
			"INFO_EXECUTION, " + 
			"METHOD_VALIDATION, " +
			"URL_REPORT, " + 
			"FECHA " +
		"FROM ALERTS " + 
		"WHERE DESCRIPTION_CHECK = ? AND " +
			"METHOD_VALIDATION " + TAG_OPERATOR + " ?";	
	
	public AlertsDAO(ConnectorBD connector) {
		this.connector = connector;
	}
	
	private String getSqlSelectAlerts(DataAlert dataAlert) {
		if (dataAlert.getMethodValidation()==null) {
			return SQLSelectAlertsWithTag.replace(TAG_OPERATOR, "IS");
		}
		return SQLSelectAlertsWithTag.replace(TAG_OPERATOR, "=");
	}	
	
	public void insertAlert(Check check, ChecksTM checksParent) {
		DataAlert dataAlert = DataAlert.of(check, checksParent);
		try (Connection conn = connector.getConnection()) {
			try (PreparedStatement insert = conn.prepareStatement(SQLInsertAlert)) {
				insert.setString(1, dataAlert.getIdExecSuite());
				insert.setString(2, dataAlert.getSuiteName());
				insert.setString(3, dataAlert.getTestCaseName());
				insert.setInt(4, dataAlert.getStepNumber());
				insert.setInt(5, dataAlert.getValidationNumber());
				insert.setString(6, dataAlert.isResultado().toString());
				insert.setString(7, dataAlert.getLevelCheck().name());
				insert.setString(8, dataAlert.getStepDescription());
				insert.setString(9, dataAlert.getCheckDescription());
				insert.setString(10, dataAlert.getInfoExecution());
				insert.setString(11, dataAlert.getMethodValidation());
				insert.setString(12, dataAlert.getUrlReportSuite());
				insert.setString(13, dataAlert.getFechaFormatted());
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
	
	public List<DataAlert> getAlerts(Check check, ChecksTM checksParent) {
		List<DataAlert> listAlerts = new ArrayList<>();
		DataAlert dataAlert = DataAlert.of(check, checksParent);
		try (Connection conn = connector.getConnection();
			PreparedStatement select = conn.prepareStatement(getSqlSelectAlerts(dataAlert))) {
			select.setString(1, dataAlert.getCheckDescription());
			select.setString(2, dataAlert.getMethodValidation());
			try (ResultSet resultado = select.executeQuery()) {
				while (resultado.next()) {
					listAlerts.add(getAlert(resultado));
				}
			}
			return listAlerts;
		} 
		catch (SQLException ex) {
			throw new RuntimeException(ex);
		} 
		catch (ParseException ex) {
			throw new RuntimeException(ex);
		}		
		catch (ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	private DataAlert getAlert(ResultSet rowSuite) throws SQLException, ParseException {
		return new DataAlert(
			rowSuite.getString("IDEXECSUITE"),
			rowSuite.getString("SUITE"),
			rowSuite.getString("METHOD"),
			rowSuite.getInt("STEP_NUMBER"),
			rowSuite.getInt("VALIDATION_NUMBER"),
			Boolean.valueOf(rowSuite.getBoolean("RESULTADO")),
			State.valueOf(rowSuite.getString("LEVEL")),
			rowSuite.getString("DESCRIPTION_STEP"),
			rowSuite.getString("DESCRIPTION_CHECK"),
			rowSuite.getString("INFO_EXECUTION"),
			rowSuite.getString("METHOD_VALIDATION"),
			DataAlert.getFechaParsed(rowSuite.getString("FECHA")),
			rowSuite.getString("URL_REPORT"));
	}
	
}
