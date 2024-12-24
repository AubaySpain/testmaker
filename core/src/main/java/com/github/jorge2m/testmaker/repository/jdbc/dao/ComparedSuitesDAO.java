package com.github.jorge2m.testmaker.repository.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import com.github.jorge2m.testmaker.domain.ComparedSuite;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteBean;

public class ComparedSuitesDAO {

	private final ConnectorBD connector;
	
	private static final String SQL_INSERT_OR_REPLACE_COMPARED_SUITES = 
		"INSERT OR REPLACE INTO COMPARED_SUITES (IDEXECSUITE_1, IDEXECSUITE_2, PATH_REPORT, URL_REPORT, DATE)" +
		"VALUES (?,?,?,?,?)";

	private static final String SQL_SELECT_COMPARED_SUITES = 
		"SELECT IDEXECSUITE_1, IDEXECSUITE_2, PATH_REPORT, URL_REPORT, DATE"  +
		"  FROM COMPARED_SUITES " +
		"WHERE IDEXECSUITE_1 = ? AND IDEXECSUITE_2 = ? ";

	private static final String SQL_DELETE_COMPARED_SUITES = 
		"DELETE FROM COMPARED_SUITES " +
		"WHERE IDEXECSUITE_1 = ?";
	
	public ComparedSuitesDAO(ConnectorBD connector) {
		this.connector = connector;
	}
	
	public void insertComparedSuite(SuiteBean suite1, SuiteBean suite2) {
		try (Connection conn = connector.getConnection()) {
			try (PreparedStatement insert = conn.prepareStatement(SQL_INSERT_OR_REPLACE_COMPARED_SUITES)) {
				insert.setString(1, suite1.getIdExecSuite());
				insert.setString(2, suite2.getIdExecSuite()); 
				insert.setString(3, suite1.getPathComparativeReport(suite2.getIdExecSuite())); 
				insert.setString(4, suite1.getUrlComparativeReportHtml(suite2.getIdExecSuite())); 
				insert.setString(5, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))); 
				
				insert.executeUpdate();
			} catch (SQLException ex) {
				throw new RuntimeException(ex);
			}
		}
		catch (SQLException | ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public Optional<ComparedSuite> getComparedSuite(String idSuite1, String idSuite2) {
		
		try (Connection conn = connector.getConnection()) {
			try (PreparedStatement select = conn.prepareStatement(SQL_SELECT_COMPARED_SUITES)) {
				select.setString(1, idSuite1);
				select.setString(2, idSuite2);
				try (ResultSet result = select.executeQuery()) {
					if (result.next()) {
						ComparedSuite comparedSuite = new ComparedSuite();
						comparedSuite.setIdExecSuite1(result.getString("IDEXECSUITE_1"));
						comparedSuite.setIdExecSuite2(result.getString("IDEXECSUITE_2"));
						comparedSuite.setPathReport(result.getString("PATH_REPORT"));
						comparedSuite.setUrlReport(result.getString("URL_REPORT"));
						comparedSuite.setDate(result.getDate("DATE"));
						
						return Optional.of(comparedSuite);
					} else {
						return Optional.empty(); 
					}
				}
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		} catch (ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public void delete(String idSuite) {
		try (Connection conn = connector.getConnection();
			PreparedStatement delete = conn.prepareStatement(SQL_DELETE_COMPARED_SUITES)) {
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
