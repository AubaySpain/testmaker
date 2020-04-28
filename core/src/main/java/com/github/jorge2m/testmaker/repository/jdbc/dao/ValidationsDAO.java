package com.github.jorge2m.testmaker.repository.jdbc.dao;

import java.sql.Connection;
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


public class ValidationsDAO {
	
	private final ConnectorBD connector;
	
	private final static String lineSeparator = System.lineSeparator();
	
	public static String SQLSelectValidationsStep =
		"SELECT " +
			"IDEXECSUITE, " + 
			"SUITE, " +
			"TEST, " + 
			"METHOD, " + 
			"STEP_NUMBER, " + 
			"VALIDATION_NUMBER, " +
			"RESULTADO, " +
			"LIST_CHECKS, " + 
			"LIST_LEVELS, " +
			"LIST_OVERCOMED " +
		"FROM VALIDATIONS " + 
		"WHERE IDEXECSUITE = ? AND " +
			"TEST=? AND " +
			"METHOD=? AND " + 
			"STEP_NUMBER = ?";

	public static String SQLInsertValidation = 
		"INSERT INTO VALIDATIONS (" +
			"IDEXECSUITE, " + 
			"SUITE, " +
			"TEST, " + 
			"METHOD, " + 
			"STEP_NUMBER, " + 
			"VALIDATION_NUMBER, " +
			"RESULTADO, " +
			"LIST_CHECKS, " + 
			"LIST_LEVELS, " +
			"LIST_OVERCOMED ) " +
		"VALUES (?,?,?,?,?,?,?,?,?,?)";
    
    public static String SQLDeleteValidations = 
        "DELETE FROM VALIDATIONS " +
        "WHERE IDEXECSUITE = ?;";

	public ValidationsDAO(ConnectorBD connector) {
		this.connector = connector;
	}

	public List<ChecksTM> getListValidations(String idSuite, String testRun, String testCase, int stepNumber) 
	throws Exception {
		List<ChecksTM> listValidations = new ArrayList<>();
		try (Connection conn = connector.getConnection();
			PreparedStatement select = conn.prepareStatement(SQLSelectValidationsStep)) {
			select.setString(1, idSuite);
			select.setString(2, testRun);
			select.setString(3, testCase);
			select.setInt(4, stepNumber);
			try (ResultSet resultado = select.executeQuery()) {
				while (resultado.next()) {
					listValidations.add(getValidation(resultado));
				}
			}
			return listValidations;
		} 
		catch (SQLException ex) {
			throw new RuntimeException(ex);
		} 
		catch (ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		}
	}

	private ChecksTM getValidation(ResultSet rowValidation) throws Exception {
		ChecksTM validationData = new ChecksTM();
		validationData.setStateValidation(State.valueOf(rowValidation.getString("RESULTADO")));
		
		List<Check> listChecks = getListChecks(
				rowValidation.getString("LIST_CHECKS"), 
				rowValidation.getString("LIST_LEVELS"), 
				rowValidation.getString("LIST_OVERCOMED"));
		validationData.setListChecks(listChecks);

		return validationData;
	}
	
	public void insertValidation(ChecksTM validations) {
		try (Connection conn = connector.getConnection()) {
			try (PreparedStatement insert = conn.prepareStatement(SQLInsertValidation)) {
				SuiteBean suite = validations.getSuiteParent().getSuiteBean(); 
				insert.setString(1, suite.getIdExecSuite());
				insert.setString(2, suite.getName()); 
				insert.setString(3, validations.getTestRunParent().getName()); 
				insert.setString(4, validations.getTestCaseParent().getName());
				insert.setInt(5, validations.getStepParent().getNumber()); 
				insert.setInt(6, validations.getPositionInStep());
				insert.setString(7, validations.getStateValidation().toString());
				
				List<String> listTextChecks = validations.getTextValidations();
				insert.setString(8, listTextChecks.stream().collect(Collectors.joining(lineSeparator))); 
				
				List<State> listStateChecks = validations.getListStateValidations();
				insert.setString(9, listStateChecks.stream().map(e -> e.toString()).collect(Collectors.joining(lineSeparator)));
				
				List<Boolean> listOvercomedChecks = validations.getListOvercomedValidations();
				insert.setString(10, listOvercomedChecks.stream().map(e -> e.toString()).collect(Collectors.joining(lineSeparator)));
				
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
	
	List<Check> getListChecks(String checksStr, String levelsStr, String overcomedsStr) {
		List<Check> listChecksReturn = new ArrayList<>();
		List<String> checksDescr = new ArrayList<>();
		List<String> levels = new ArrayList<>();
		List<Boolean> overcomeds = new ArrayList<>();
		if (checksStr==null || "".compareTo(checksStr)==0) {
			return listChecksReturn;
		}
		
		checksDescr = Arrays.asList(checksStr.split(lineSeparator));
		if (levelsStr!=null && "".compareTo(levelsStr)!=0) {
			levels = Arrays.asList(levelsStr.split(lineSeparator));
		}
		if (overcomedsStr!=null && "".compareTo(overcomedsStr)!=0) {
			List<String> listOvercomeds = Arrays.asList(overcomedsStr.split(lineSeparator));
			for (String overcomedStr : listOvercomeds) {
				if ("true".compareTo(overcomedStr)==0) {
					overcomeds.add(Boolean.TRUE);
				} else {
					overcomeds.add(Boolean.FALSE);
				}
			}
		}
		
		for (int i=0; i<checksDescr.size(); i++) {
			Check check = new Check();
			check.setDescription(checksDescr.get(i));
			check.setId(i);
			if (levels.size()>i) {
				check.setLevelResult(State.valueOf(levels.get(i)));
			}
			if (overcomeds.size()>i) {
				check.setOvercomed(overcomeds.get(i));
			}
			listChecksReturn.add(check);
		}
		return listChecksReturn;
	}

	public void deleteValidations(String idSuite) {
		try (Connection conn = connector.getConnection();
			PreparedStatement delete = conn.prepareStatement(SQLDeleteValidations)) {
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
}
