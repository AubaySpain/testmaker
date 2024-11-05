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
	
	private static final String SQL_SELECT_STEPS_TESTCASE =
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
			"STATE, " +
			"NAME_CLASS, " +
			"NAME_METHOD " +
		"FROM STEPS " + 
		"WHERE IDEXECSUITE = ? AND " +
			"TEST=? AND " +
			"METHOD=? ";

	private static final String SQL_INSERT_STEP = 
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
			"STATE, " +
			"NAME_CLASS, " + 
			"NAME_METHOD ) " +
		"VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	private static final String SQL_DELETE_STEPS = 
		"DELETE FROM STEPS " + 
		"WHERE IDEXECSUITE = ?";
		
	public StepsDAO(ConnectorBD connector) {
		this.connector = connector;
	}

	public List<StepTM> getListSteps(String idSuite, String testRun, String testCase) 
			throws Exception {
		List<StepTM> listSteps = new ArrayList<>();
		try (Connection conn = connector.getConnection();
			var select = conn.prepareStatement(SQL_SELECT_STEPS_TESTCASE)) {
			select.setString(1, idSuite);
			select.setString(2, testRun);
			select.setString(3, testCase);
			try (var result = select.executeQuery()) {
				while (result.next()) {
					listSteps.add(getStep(result, idSuite, testRun, testCase));
				}
			}
			return listSteps;
		} 
		catch (SQLException | ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		} 
	}

	private StepTM getStep(ResultSet rowStep, String idSuite, String testRun, String testCase) throws Exception {
		StepTM stepData = new StepTM();
		stepData.setNumber(rowStep.getInt("STEP_NUMBER"));
		stepData.setDescripcion(rowStep.getString("DESCRIPTION"));
		stepData.setResExpected(rowStep.getString("RES_EXPECTED"));
		stepData.setResultSteps(State.from(rowStep.getString("RESULTADO")));
		stepData.setExcepExists(rowStep.getBoolean("EXCEPCION"));
		stepData.setNameClass(rowStep.getString("NAME_CLASS"));
		stepData.setNameMethod(rowStep.getString("NAME_METHOD"));
		
		Date dateInicio = Utils.getDateFormat(ToMillis).parse(rowStep.getString("INICIO"));
		stepData.setTimeInicio(dateInicio.getTime());
		Date dateFin = Utils.getDateFormat(ToMillis).parse(rowStep.getString("FIN"));
		stepData.setTimeFin(dateFin.getTime());
		
		stepData.setState(StateExecution.from(rowStep.getString("STATE")));
		
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
			try (var insert = conn.prepareStatement(SQL_INSERT_STEP)) {
				SuiteBean suite = step.getSuiteParent().getSuiteBean(); 
				insert.setString(1, suite.getIdExecSuite());
				insert.setString(2, suite.getName()); 
				insert.setString(3, step.getTestRunParent().getName()); 
				insert.setString(4, step.getTestCaseParent().getNameUnique()); 
				insert.setInt(5, step.getNumber()); 
				insert.setString(6, step.getDescripcion()); 
				insert.setString(7, step.getResExpected()); 
				insert.setString(8, step.getResultSteps().toString());
				insert.setBoolean(9, step.isExcepExists());
				insert.setString(10, Utils.getDateFormat(ToMillis).format(step.getHoraInicio()));
				insert.setString(11, Utils.getDateFormat(ToMillis).format(step.getHoraFin()));
				insert.setFloat(12, step.getTimeFin() - step.getTimeInicio());
				insert.setString(13, step.getState().toString());
				insert.setString(14, step.getNameClass());
				insert.setString(15, step.getNameMethod());
				insert.executeUpdate();
			} catch (SQLException ex) {
				throw new RuntimeException(ex);
			}
		}
		catch (SQLException | ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		} 
	}

	public void deleteSteps(String idExecSuite) {
		try (Connection conn = connector.getConnection();
			PreparedStatement delete = conn.prepareStatement(SQL_DELETE_STEPS)) {
			delete.setString(1, idExecSuite);
			delete.executeUpdate();
		} 
		catch (SQLException | ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		} 
	} 
	
}
