package com.github.jorge2m.testmaker.repository.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.StateExecution;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteBean;

import static com.github.jorge2m.testmaker.repository.jdbc.dao.Utils.DateFormat.*;


public class StepsDAO {
	
	private final ConnectorBD connector;
	
	public static String SQLSelectStepsTestCase =
		"SELECT " +
			"IDEXECSUITE, " + 
			"SUITE, " +
			"TEST, " + 
			"METHOD, " + 
			"STEP_NUMBER, " + 
			"DESCRIPTION, " + 
			"RES_EXPECTED, " + 
			"RESULTADO, " + 
			"EXCEPCION, " + 
			"INICIO, " + 
			"FIN, " + 
			"TIME_MS, " + 
			"STATE " +
		"FROM STEPS " + 
		"WHERE IDEXECSUITE = ? AND " +
			"TEST=? AND " +
			"METHOD=? ";

	public static String SQLInsertStep = 
		"INSERT INTO STEPS (" +
			"IDEXECSUITE, " + 
			"SUITE, " +
			"TEST, " + 
			"METHOD, " + 
			"STEP_NUMBER, " + 
			"DESCRIPTION, " + 
			"RES_EXPECTED, " + 
			"RESULTADO, " + 
			"EXCEPCION, " + 
			"INICIO, " + 
			"FIN, " + 
			"TIME_MS, " + 
			"STATE ) " + 
		"VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	public static String SQLDeleteSteps = 
		"DELETE FROM STEPS " + 
		"WHERE IDEXECSUITE = ?";
		
//    public static String SQLDeleteHistorical = 
//        "DELETE FROM METHODS " +
//        "WHERE INICIO < ?;";

	public StepsDAO(ConnectorBD connector) {
		this.connector = connector;
	}

	public List<StepTM> getListSteps(String idSuite, String testRun, String testCase) 
	throws Exception {
		List<StepTM> listSteps = new ArrayList<>();
		try (Connection conn = connector.getConnection();
			PreparedStatement select = conn.prepareStatement(SQLSelectStepsTestCase)) {
			select.setString(1, idSuite);
			select.setString(2, testRun);
			select.setString(3, testCase);
			try (ResultSet resultado = select.executeQuery()) {
				while (resultado.next()) {
					listSteps.add(getStep(resultado, idSuite, testRun, testCase));
				}
			}
			return listSteps;
		} 
		catch (SQLException ex) {
			throw new RuntimeException(ex);
		} 
		catch (ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		}
	}

	private StepTM getStep(ResultSet rowStep, String idSuite, String testRun, String testCase) throws Exception {
		StepTM stepData = new StepTM();
		stepData.setDescripcion(rowStep.getString("DESCRIPTION"));
		stepData.setResExpected(rowStep.getString("RES_EXPECTED"));
		stepData.setResultSteps(State.valueOf(rowStep.getString("RESULTADO")));
		stepData.setExcepExists(rowStep.getBoolean("EXCEPCION"));
		
		Date dateInicio = Utils.getDateFormat(ToMillis).parse(rowStep.getString("INICIO"));
		stepData.setTimeInicio(dateInicio.getTime());
		Date dateFin = Utils.getDateFormat(ToMillis).parse(rowStep.getString("FIN"));
		stepData.setTimeInicio(dateFin.getTime());
		
		stepData.setState(StateExecution.valueOf(rowStep.getString("STATE")));
		
		ValidationsDAO validatiosnDAO = new ValidationsDAO(connector);
		stepData.setListChecksTM(validatiosnDAO.getListValidations(
				idSuite, 
				testRun, 
				testCase, 
				rowStep.getInt("STEP_NUMBER")));
		
		return stepData;
	}

	public void insertStep(StepTM step) {
		try (Connection conn = connector.getConnection()) {
			try (PreparedStatement insert = conn.prepareStatement(SQLInsertStep)) {
				SuiteBean suite = step.getSuiteParent().getSuiteBean(); 
				insert.setString(1, suite.getIdExecSuite());
				insert.setString(2, suite.getName()); 
				insert.setString(3, step.getTestRunParent().getName()); 
				insert.setString(4, step.getTestCaseParent().getName()); 
				insert.setInt(5, step.getNumber()); 
				insert.setString(6, step.getDescripcion()); 
				insert.setString(7, step.getResExpected()); 
				insert.setString(8, step.getResultSteps().toString());
				insert.setBoolean(9, step.isExcepExists());
				insert.setString(10, Utils.getDateFormat(ToMillis).format(step.getHoraInicio()));
				insert.setString(11, Utils.getDateFormat(ToMillis).format(step.getHoraFin()));
				insert.setFloat(12, step.getTimeFin() - step.getTimeInicio());
				insert.setString(13, step.getState().toString());
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

	public void deleteSteps(String idExecSuite) {
		try (Connection conn = connector.getConnection();
			PreparedStatement delete = conn.prepareStatement(SQLDeleteSteps)) {
			delete.setString(1, idExecSuite);
			delete.executeUpdate();
		} 
		catch (SQLException ex) {
			throw new RuntimeException(ex);
		} 
		catch (ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		}
	} 
}
