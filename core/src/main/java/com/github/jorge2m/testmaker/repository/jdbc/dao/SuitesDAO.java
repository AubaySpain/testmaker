package com.github.jorge2m.testmaker.repository.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.StateExecution;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteBean;


public class SuitesDAO {
	
	private final ConnectorBD connector;
	
	private static final String ListFieldsSuiteTable = 
		"IDEXECSUITE, " +
		"SUITE, " + 
		"VERSION, " + 
		"BROWSER, " + 
		"CHANNEL, " + 
		"APP, " + 
		"RESULT, " + 
		"INICIO, " + 
		"FIN, " + 
		"TIME_MS, " + 
		"NUMBER_METHODS, " + 
		"PATH_REPORT, " + 
		"URL_REPORT, " + 
		"MORE_INFO, " + 
		"URLBASE, " + 
		"STATE_EXECUTION ";

	private static final String SQLInsertOrReplaceSuite = 
		"INSERT OR REPLACE INTO SUITES (" + ListFieldsSuiteTable + ")" +
		"VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private static final String SQLSelectSuite = 
		"SELECT " + ListFieldsSuiteTable  +
		"  FROM SUITES " +
		"WHERE IDEXECSUITE = ? " +
		"ORDER BY IDEXECSUITE DESC";

	private static final String SQLSelectSuitesFromId = 
		"SELECT " + ListFieldsSuiteTable + 
		"  FROM SUITES " +
		"WHERE IDEXECSUITE >= ?";

	private static final String SQLDeleteSuitesBeforeId = 
		"DELETE FROM SUITES " +
		"WHERE IDEXECSUITE <= ? ";

	private static final String SQLSelectSuitesIdDesc = 
		"SELECT " + ListFieldsSuiteTable + 
		"FROM SUITES  " + 
		"ORDER BY IDEXECSUITE DESC";

	public SuitesDAO(ConnectorBD connector) {
		this.connector = connector;
	}

	public List<SuiteBean> getListSuitesIdDesc() throws Exception {
		List<SuiteBean> listSuites = new ArrayList<>();
		try (Connection conn = connector.getConnection();
			PreparedStatement select = conn.prepareStatement(SQLSelectSuitesIdDesc)) {
			try (ResultSet resultado = select.executeQuery()) {
				while (resultado.next()) {
					listSuites.add(getSuite(resultado));
				}
			}
			return listSuites;
		} 
		catch (SQLException ex) {
			throw new RuntimeException(ex);
		} 
		catch (ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		}
	}

	public SuiteBean getSuite(String suiteExecId) throws Exception {
		SuiteBean suiteData = null;
		try (Connection conn = connector.getConnection()) {
			try (PreparedStatement select = conn.prepareStatement(SQLSelectSuite)) {
				select.setString(1, suiteExecId);
				try (ResultSet resultado = select.executeQuery()) {
					if (resultado.next()) {
						suiteData = getSuite(resultado);
					}
				}
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		} catch (ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		}

		return suiteData;
	}

	private SuiteBean getSuite(ResultSet rowSuite) throws Exception {
		SuiteBean suiteData = new SuiteBean();
		suiteData.setIdExecSuite(rowSuite.getString("IDEXECSUITE"));
		suiteData.setName(rowSuite.getString("SUITE"));
		suiteData.setVersion(rowSuite.getString("VERSION"));
		suiteData.setChannel(Channel.valueOf(rowSuite.getString("CHANNEL")));
		suiteData.setApp(rowSuite.getString("APP"));
		suiteData.setBrowser(rowSuite.getString("BROWSER"));
		suiteData.setResult(State.valueOf(rowSuite.getString("RESULT")));

		String inicioDate = rowSuite.getString("INICIO");
		suiteData.setInicioDate(getDateFormat().parse(inicioDate));
		String finDate = rowSuite.getString("FIN");
		if (finDate!=null && "".compareTo(finDate)!=0) {
			suiteData.setInicioDate(getDateFormat().parse(finDate));
		}

		suiteData.setDurationMillis(rowSuite.getFloat("TIME_MS"));
		suiteData.setNumberTestCases(rowSuite.getInt("NUMBER_METHODS"));
		suiteData.setMoreInfo(rowSuite.getString("MORE_INFO"));
		suiteData.setUrlBase(rowSuite.getString("URLBASE"));
		suiteData.setPathReportHtml(rowSuite.getString("PATH_REPORT"));
		suiteData.setUrlReportHtml(rowSuite.getString("URL_REPORT"));
		suiteData.setStateExecution(StateExecution.valueOf(rowSuite.getString("STATE_EXECUTION")));
		return suiteData;
	}

	public void insertOrReplaceSuite(SuiteBean suiteData) {
		try (Connection conn = connector.getConnection()) {
			try (PreparedStatement insert = conn.prepareStatement(SQLInsertOrReplaceSuite)) {
				insert.setString(1, suiteData.getIdExecSuite());
				insert.setString(2, suiteData.getName()); 
				insert.setString(3, suiteData.getVersion()); 
				insert.setString(4, suiteData.getBrowser()); 
				insert.setString(5, suiteData.getChannel().name()); 
				insert.setString(6, suiteData.getApp());
				insert.setString(7, suiteData.getResult().name());
				insert.setString(8, getDateFormat().format(suiteData.getInicioDate()));
				if (suiteData.getFinDate()!=null) {
					insert.setString(9, getDateFormat().format(suiteData.getFinDate()));
				} else {
					insert.setString(9, null);
				}
				insert.setFloat(10, suiteData.getDurationMillis());
				insert.setInt(11, suiteData.getNumberTestCases());
				insert.setString(12, suiteData.getPathReportHtml());
				insert.setString(13,  suiteData.getUrlReportHtml());
				insert.setString(14, suiteData.getMoreInfo());
				insert.setString(15, suiteData.getUrlBase());
				insert.setString(16, suiteData.getStateExecution().name());
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

	public SuiteBean get1rstSuiteBefore(Date fechaHasta) throws Exception {
		for (SuiteBean suite : getListSuitesIdDesc()) {
			if (suite.getInicioDate().before(fechaHasta)) {
				return suite;
			}
		}
		return null;
	}

	public SuiteBean get1rstSuiteAfter(Date fechaDesde) throws Exception {
		SuiteBean suiteToReturn = null;
		for (SuiteBean suite : getListSuitesIdDesc()) {
			if (suite.getInicioDate().after(fechaDesde)) {
				suiteToReturn = suite;
			} else {
				break;
			}
		}
		return suiteToReturn;
	}

	public List<SuiteBean> getListSuitesAfter(Date fechaDesde) throws Exception {
		List<SuiteBean> listSuites = new ArrayList<>();
		SuiteBean suite = get1rstSuiteAfter(fechaDesde);
		if (suite!=null) {
			try (Connection conn = connector.getConnection();
				PreparedStatement select = conn.prepareStatement(SQLSelectSuitesFromId)) {
				select.setString(1, suite.getIdExecSuite());
				try (ResultSet resultado = select.executeQuery()) {
					while (resultado.next()) {
						listSuites.add(getSuite(resultado));
					}
				}

				return listSuites;
			} 
			catch (SQLException ex) {
				throw new RuntimeException(ex);
			}
			catch (ClassNotFoundException ex) {
				throw new RuntimeException(ex);
			}
		}
		return null;
	}

	public void deleteSuitesBefore(String idSuite) {
		try (Connection conn = connector.getConnection();
			PreparedStatement delete = conn.prepareStatement(SQLDeleteSuitesBeforeId)) {
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
