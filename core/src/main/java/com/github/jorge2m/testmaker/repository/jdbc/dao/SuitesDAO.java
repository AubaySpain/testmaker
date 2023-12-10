package com.github.jorge2m.testmaker.repository.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.StateExecution;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteBean;
import static com.github.jorge2m.testmaker.repository.jdbc.dao.Utils.DateFormat.*;


public class SuitesDAO {
	
	private final ConnectorBD connector;
	
	private static final String LIST_FIELDS_SUITE_TABLE = 
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

	private static final String SQL_INSERT_OR_REPLACE_SUITE = 
		"INSERT OR REPLACE INTO SUITES (" + LIST_FIELDS_SUITE_TABLE + ")" +
		"VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private static final String SQL_SELECT_SUITE = 
		"SELECT " + LIST_FIELDS_SUITE_TABLE  +
		"  FROM SUITES " +
		"WHERE IDEXECSUITE = ? " +
		"ORDER BY IDEXECSUITE DESC";

//	private static final String SQLSelectSuitesFromId = 
//		"SELECT " + ListFieldsSuiteTable + 
//		"  FROM SUITES " +
//		"WHERE IDEXECSUITE >= ?";

	private static final String SQL_DELETE_SUITE = 
		"DELETE FROM SUITES " +
		"WHERE IDEXECSUITE = ? ";

	private static final String SQL_SELECT_SUITES_ID_DESC = 
		"SELECT " + LIST_FIELDS_SUITE_TABLE + 
		"FROM SUITES  " + 
		"ORDER BY IDEXECSUITE DESC";

	public SuitesDAO(ConnectorBD connector) {
		this.connector = connector;
	}

	public List<SuiteBean> getListSuitesIdDesc() throws Exception {
		List<SuiteBean> listSuites = new ArrayList<>();
		try (Connection conn = connector.getConnection();
			PreparedStatement select = conn.prepareStatement(SQL_SELECT_SUITES_ID_DESC)) {
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
			try (PreparedStatement select = conn.prepareStatement(SQL_SELECT_SUITE)) {
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
		suiteData.setDriver(rowSuite.getString("BROWSER"));
		suiteData.setResult(State.from(rowSuite.getString("RESULT")));

		String inicioDate = rowSuite.getString("INICIO");
		suiteData.setInicioDate(Utils.getDateFormat(ToSeconds).parse(inicioDate));
		String finDate = rowSuite.getString("FIN");
		if (finDate!=null && "".compareTo(finDate)!=0) {
			suiteData.setFinDate(Utils.getDateFormat(ToSeconds).parse(finDate));
		}

		suiteData.setDurationMillis(rowSuite.getFloat("TIME_MS"));
		suiteData.setNumberTestCases(rowSuite.getInt("NUMBER_METHODS"));
		suiteData.setMoreInfo(rowSuite.getString("MORE_INFO"));
		suiteData.setUrlBase(rowSuite.getString("URLBASE"));
		suiteData.setPathReportHtml(rowSuite.getString("PATH_REPORT"));
		suiteData.setUrlReportHtml(rowSuite.getString("URL_REPORT"));
		suiteData.setStateExecution(StateExecution.valueOf(rowSuite.getString("STATE_EXECUTION")));
		
		TestRunsDAO testRunsDAO = new TestRunsDAO(connector);
		suiteData.setListTestRun(testRunsDAO.getListTestRuns(rowSuite.getString("IDEXECSUITE")));
		
		return suiteData;
	}

	public void insertOrReplaceSuite(SuiteBean suiteData) {
		try (Connection conn = connector.getConnection()) {
			try (PreparedStatement insert = conn.prepareStatement(SQL_INSERT_OR_REPLACE_SUITE)) {
				insert.setString(1, suiteData.getIdExecSuite());
				insert.setString(2, suiteData.getName()); 
				insert.setString(3, suiteData.getVersion()); 
				insert.setString(4, suiteData.getDriver()); 
				insert.setString(5, suiteData.getChannel().name()); 
				insert.setString(6, suiteData.getApp());
				insert.setString(7, suiteData.getResult().name());
				insert.setString(8, Utils.getDateFormat(ToSeconds).format(suiteData.getInicioDate()));
				if (suiteData.getFinDate()!=null) {
					insert.setString(9, Utils.getDateFormat(ToSeconds).format(suiteData.getFinDate()));
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
		catch (SQLException | ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		}
	}

	public List<SuiteBean> getSuitesBetween(final Date fechaDesde, final Date fechaHasta) throws Exception {
		Pair<Date,Date> desdeHastaPair = getDatesNormalized(fechaDesde, fechaHasta);
		if (desdeHastaPair.getLeft().after(desdeHastaPair.getRight())) {
			throw new IllegalArgumentException(desdeHastaPair.getLeft() + " after " + desdeHastaPair.getRight());
		}
		
		List<SuiteBean> suitesToReturn = new ArrayList<>();
		try (var conn = connector.getConnection();
			var selectStatement = conn.prepareStatement(SQL_SELECT_SUITES_ID_DESC);
			var resultSet = selectStatement.executeQuery()) {
			while (resultSet.next()) {
				var inicioDateSuite = Utils.getDateFormat(ToSeconds).parse(resultSet.getString("INICIO"));
				var interval = dateInterval(inicioDateSuite, desdeHastaPair);
				if (interval==Interval.BETWEEN) {
					suitesToReturn.add(getSuite(resultSet));
				}
				if (interval==Interval.BEFORE) {
					break;
				}
			}
		} 
		catch (SQLException ex) {
			throw new RuntimeException(ex);
		} 
		catch (ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		}
		
		return suitesToReturn;
	}
	
	private enum Interval { BEFORE, BETWEEN, AFTHER } 
	Interval dateInterval(Date date, Pair<Date,Date> interval) {
		if (date.before(interval.getRight())) {
			if (date.after(interval.getLeft())) { 
				return Interval.BETWEEN;
			} else {
				return Interval.BEFORE;
			}
		}
		return Interval.AFTHER;
	}
		
	private Pair<Date, Date> getDatesNormalized(final Date fechaDesde, final Date fechaHasta) {
		Date desde;
		Date hasta;
		if (fechaDesde==null) {
			desde = new Date(0);
		} else {
			desde = (Date)fechaDesde.clone();
		}
		if (fechaHasta==null) {
			Calendar cal = Calendar.getInstance();
			cal.set(9999, 11, 31);
			hasta = new Date(cal.getTimeInMillis());
		} else {
			hasta = (Date)fechaHasta.clone();
		}
		
		return Pair.of(desde, hasta);
	}

//	public List<SuiteBean> getListSuitesBetween(Date desde, Date hasta) throws Exception {
//		List<SuiteBean> listSuites = new ArrayList<>();
//		List<SuiteBean> suite = getSuitesBetween(desde, hasta);
//		if (suite!=null) {
//			try (Connection conn = connector.getConnection();
//				PreparedStatement select = conn.prepareStatement(SQLSelectSuitesFromId)) {
//				select.setString(1, suite.getIdExecSuite());
//				try (ResultSet resultado = select.executeQuery()) {
//					while (resultado.next()) {
//						listSuites.add(getSuite(resultado));
//					}
//				}
//				return listSuites;
//			} 
//			catch (SQLException ex) {
//				throw new RuntimeException(ex);
//			}
//			catch (ClassNotFoundException ex) {
//				throw new RuntimeException(ex);
//			}
//		}
//		return null;
//	}
	
	public void deleteSuite(String idSuite) {
		try (Connection conn = connector.getConnection();
			PreparedStatement delete = conn.prepareStatement(SQL_DELETE_SUITE)) {
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
