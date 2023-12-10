package com.github.jorge2m.testmaker.testreports.html;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteBean;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseBean;


public class HtmlEmailBuilder {

	private enum TypeSuite {NEW, OLD}
	
	private final List<SuiteTestCasesData> listSuitesNew;
	private final List<SuiteTestCasesData> listSuitesOld;
	private final String hostTestMaker;
	private final boolean isNewData;
	private final boolean isOldData;
	private Totals totals = new Totals();
	
	public HtmlEmailBuilder(
		List<SuiteTestCasesData> listSuitesNew,
		List<SuiteTestCasesData> listSuitesOld,
		String hostTestMaker) {
		this.listSuitesNew = listSuitesNew;
		this.listSuitesOld = listSuitesOld;
		this.hostTestMaker = hostTestMaker;
		isNewData = listSuitesNew!=null && !listSuitesNew.isEmpty();
		isOldData = listSuitesOld!=null && !listSuitesOld.isEmpty(); 
	}

	public String getHtml() {
		return 
		    "<table border=\"0\" style=\"font:12pt Arial; margin:-8px 0 0; border-collapse:collapse; text-align:left;\">" +
		    makeHtmlCabecera() + 
		    makeHtmlBody() +
		    makeHtmlTotals() +
		    "</table>" + 
		    makeHtmlSaludoRobot();
	}

	private String makeHtmlCabecera() {
		String html =  	
			"<thead>" +
			"<tr style=\"background-color:#11F411;border:1px solid #000000;border-collapse:collapse;\">";
		
		html = html +
			getColumn("ID") + 
			getColumn("SUITE") +
			getColumn("APP") +
			getColumn("CHANNEL") +
			getColumn("DRIVER") +
			getColumn("VERSIÓN") +
			getColumn("URL BASE") +
			getColumn("#AVAILAB") +
			getColumn("#OKs") +
		    getColumn("#KOs") +
		    getColumn("#DEFECTs") +
		    getColumn("#WARNs") +
		    getColumn("#INFOs") +
		    getColumn("#SKIPs") +
		    getColumn("MINUTES") +
		    getColumn("STATE") +
		    getColumn("REPORT");
		
		if (isOldData) {
		    html+=getColumn("OLD REPORT");
		}
		
		html = html +
		    "</tr></thead>";
		return html;
	}
	
	private String getColumn(String idColumn) {
		String TAG = "${TAG}";
		String TH_NEW = "<th style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px;\">" + TAG + "</th>";
		return TH_NEW.replace(TAG, idColumn);
	}
	
	private String makeHtmlBody() {
		String html = "<tbody>";
		if (isNewData) {
			for (SuiteTestCasesData suiteTestCasesNew : listSuitesNew) {
				SuiteTestCasesData suiteTestCasesOld = null;
				if (isOldData) {
					Optional<SuiteTestCasesData> opt = getEquivalentSuiteOld(suiteTestCasesNew);
					if (opt.isPresent()) {
						suiteTestCasesOld = opt.get();
					}
				}
				RowSuiteData rowSuiteData = makeHtmlRow(suiteTestCasesNew, suiteTestCasesOld);
				totals.add(rowSuiteData);
				html+=rowSuiteData.getHtml();
			}
		}
		return html + "</tbody>";
	}
	
	private Optional<SuiteTestCasesData> getEquivalentSuiteOld(SuiteTestCasesData suiteTestCaseNew) {
		SuiteBean suiteNew = suiteTestCaseNew.getSuite();
		for (SuiteTestCasesData suiteTestCasesOld : listSuitesOld) {
			SuiteBean suiteOld = suiteTestCasesOld.getSuite(); 
			if (suiteOld.getName().compareTo(suiteNew.getName())==0 &&
				suiteOld.getApp().compareTo(suiteNew.getApp())==0 &&
				suiteOld.getChannel().compareTo(suiteNew.getChannel())==0 &&
				suiteOld.getDriver().compareTo(suiteNew.getDriver())==0 &&
				suiteOld.getVersion().compareTo(suiteNew.getVersion())==0 &&
				suiteOld.getUrlBase().compareTo(suiteNew.getUrlBase())==0) {
				return Optional.of(suiteTestCasesOld);
			}
		}
		return Optional.empty();
	}

	private RowSuiteData makeHtmlRow(
			SuiteTestCasesData suiteTestCasesNew, SuiteTestCasesData suiteTestCasesOld) {
		
		RowSuiteData rowSuiteData = new RowSuiteData(suiteTestCasesNew, suiteTestCasesOld);
		SuiteBean suiteNew = suiteTestCasesNew.getSuite();
		
		String TD_INI = "<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px;";
		String html =   
			"<tr>" + 
			TD_INI + "\">" + suiteNew.getIdExecSuite() + "</td>" + 
			TD_INI + "\">" + suiteNew.getName() + "</td>" +
			TD_INI + "\">" + suiteNew.getApp() + "</td>" +
			TD_INI + "\">" + suiteNew.getChannel() + "</td>" +
			TD_INI + "\">" + suiteNew.getDriver() + "</td>" +
			TD_INI + "\">" + suiteNew.getVersion() + "</td>";
		
		html+= TD_INI + "text-align:center;\"><a href='" + suiteNew.getUrlBase() + "'>url</a></td>";
		html+=TD_INI + " text-align:center; color:" + "darkGreen" + ";\">" + rowSuiteData.getAvailabilityTests(TypeSuite.NEW) + "</td>";
		
	    html+=TD_INI + " text-align:center; color:" + State.OK.getColorCss() + ";\">" + getNumTestCasesHtml(State.OK, rowSuiteData) + "</td>";
		html+=TD_INI + " text-align:center; color:" + State.KO.getColorCss() + ";\">" + getNumTestCasesHtml(State.KO, rowSuiteData) + "</td>";
		html+=TD_INI + " text-align:center; color:" + State.DEFECT.getColorCss() + ";\">" + getNumTestCasesHtml(State.DEFECT, rowSuiteData) + "</td>";
		html+=TD_INI + " text-align:center; color:" + State.WARN.getColorCss() + ";\">" + getNumTestCasesHtml(State.WARN, rowSuiteData) + "</td>";
		html+=TD_INI + " text-align:center; color:" + State.INFO.getColorCss() + ";\">" + getNumTestCasesHtml(State.INFO, rowSuiteData) + "</td>";
		
		html+=
		    TD_INI + " text-align:center; color:" + State.SKIP.getColorCss() + ";\">" + rowSuiteData.getNumTestCases(State.SKIP) + "</td>" +
		    TD_INI + " text-align:right;\">" + rowSuiteData.getTimeMinutes() + "</td>" +
		    TD_INI + "\">" + suiteNew.getStateExecution() + "</td>";
		
		html+=TD_INI + "\"><a href='" + rowSuiteData.getUrlReport(TypeSuite.NEW, hostTestMaker) + "'>report</a></td>";
		if (isOldData) {
			String reportURL = rowSuiteData.getUrlReport(TypeSuite.OLD, hostTestMaker);
			if (reportURL!=null && reportURL.compareTo("")!=0) {
				html+=TD_INI + "\"><a href='" + reportURL + "'>report</a></td>";
			} else {
				html+=TD_INI + "\"></td>";
			}
		}
		
		html+="</tr>";
		rowSuiteData.setHtml(html);  
		return rowSuiteData;
	}

	private String makeHtmlTotals() {
		String html=
			"<tfoot style=\"font-weight: bold;\">" +
			"<tr style=\"border:none;\">" +
			"<td colspan=\"7\"></td>";

		String TD_INI = "<td style=\"border:1px solid #000000;padding-left: 10px; padding-right: 10px; text-align:center; color:";
		html+=TD_INI + "darkGreen" + ";\">" + totals.getTestCasesAvailability() + "</td>";
		
		html+=TD_INI + State.OK.getColorCss() + ";\">" + getNumTestCasesTotalsHtml(State.OK) + "</td>";
		html+=TD_INI + State.KO.getColorCss() + ";\">" + getNumTestCasesTotalsHtml(State.KO) + "</td>";
		html+=TD_INI + State.DEFECT.getColorCss() + ";\">" + getNumTestCasesTotalsHtml(State.DEFECT) + "</td>";
		html+=TD_INI + State.WARN.getColorCss() + ";\">" + getNumTestCasesTotalsHtml(State.WARN) + "</td>";
		html+=TD_INI + State.INFO.getColorCss() + ";\">" + getNumTestCasesTotalsHtml(State.INFO) + "</td>";
		html+=TD_INI + State.SKIP.getColorCss() + ";\">" + getNumTestCasesTotalsHtml(State.SKIP) + "</td>";
		
		html+=TD_INI + totals.getSuiteMinutes() + "</td>" +
			"<td colspan=\"2\"></td>" +
			"</tr>";
		
		if (isNewData) {
			html+=
				"<tr style=\"border:none;\">" +
				"<td colspan=\"7\"></td>";
			
			html+=TD_INI + "darkGreen" + ";\">" + totals.getPercentageTestCasesAvailable() + "%</td>";
			
			html+=TD_INI + State.OK.getColorCss() + ";\">" + getPercentagesTotalsHtml(State.OK) + "</td>";
			html+=TD_INI + State.KO.getColorCss() + ";\">" + getPercentagesTotalsHtml(State.KO) + "</td>";
			html+=TD_INI + State.DEFECT.getColorCss() + ";\">" + getPercentagesTotalsHtml(State.DEFECT) + "</td>";
			html+=TD_INI + State.WARN.getColorCss() + ";\">" + getPercentagesTotalsHtml(State.WARN) + "</td>";
			html+=TD_INI + State.INFO.getColorCss() + ";\">" + getPercentagesTotalsHtml(State.INFO) + "</td>";
			html+=TD_INI + State.SKIP.getColorCss() + ";\">" + getPercentagesTotalsHtml(State.SKIP) + "</td>";
			html+=
				"<td colspan=\"3\"></td>" +
				"</tr>";
		}
		html+="</tfoot>";	
		return html;
	}
	
	private String getNumTestCasesHtml(State state, RowSuiteData rowSuiteData) {
		String numNew = rowSuiteData.getNumTestCases(state);
		String deltaBlock = "";
		if (rowSuiteData.isSuiteOld()) {
			String numOld = rowSuiteData.getNumTestCases(state, TypeSuite.OLD);
			deltaBlock = getDeltaTestsHtml(numNew, numOld);
		}
		return numNew + deltaBlock;
	}
	
	private String getNumTestCasesTotalsHtml(State state) {
		String numNew = totals.getNumTestCases(state);
		String deltaBlock = "";
		if (isOldData) {
			String numOld = totals.getNumTestCases(state, TypeSuite.OLD);
			deltaBlock = getDeltaTestsHtml(numNew, numOld); 
		}
		return numNew + deltaBlock;
	}
	
	private String getPercentagesTotalsHtml(State state) {
		String percentageNewStr = totals.getPercentageTestCases(state) + "%";
		String deltaBlock = "";
		if (isOldData) {
			Float percentageNew = totals.getPercentageTestCasesFloat(state, TypeSuite.NEW);
			Float percentageOld = totals.getPercentageTestCasesFloat(state, TypeSuite.OLD);
			Float difference = percentageNew - percentageOld;
			String differenceStr = String.format("%+.2f%n", difference) + "%";
			deltaBlock = getDeltaHtml(differenceStr);
		}
		return percentageNewStr + deltaBlock;
	}
	
	private String getDeltaTestsHtml(String numNew, String numOld) {
		String oldBlock = "";
		if (!(numNew.compareTo("")==0 && numOld.compareTo("")==0)) {
			if (numNew.compareTo("")==0) {
				numNew = "0";
			}
			if (numOld.compareTo("")==0) {
				numOld = "0";
			}
			Integer difference = Integer.valueOf(numNew) - Integer.valueOf(numOld);
			String differenceStr = String.format("%+d%n", difference);
			oldBlock = getDeltaHtml(differenceStr);
		}
		return oldBlock;
	}
	
	private String getDeltaHtml(String differenceStr) {
		return 
			"<div style=\"display:inline; font:9pt Arial;color:grey;\">(" +
			differenceStr + ")</div>";
	}
	
    private String makeHtmlSaludoRobot() {
    	return 
    			"<br>" +		
    			"<p style=\"font:12pt Arial;\">" +
    			"Un saludo,<br>" +
    			"El Robot de Test" +
    			"</p>";
    }

	static Map<State,Integer> getInitZeroValues() {
		Map<State,Integer> mapReturn = new HashMap<>();
		for (State state : State.values()) {
			mapReturn.put(state, 0);
		}
		return mapReturn;
	}
	
	private static class RowSuiteData {
		private final SuiteTestCasesData suiteNew;
		private final SuiteTestCasesData suiteOld;
		private String html;
 
		public RowSuiteData(
				SuiteTestCasesData suiteNew,
				SuiteTestCasesData suiteOld) {
			this.suiteNew = suiteNew;
			this.suiteOld = suiteOld;
		}
		
		public String getHtml() {
			return html;
		}
		public void setHtml(String html) {
			this.html = html;
		}
		public boolean isSuiteOld() {
			return suiteOld!=null;
		}
		
		public String getTimeMinutes() {
			DecimalFormat df1 = new DecimalFormat("0.00");
			return df1.format(Double.valueOf(suiteNew.getSuite().getDurationMillis()).doubleValue() / 1000 / 60);	
		}
		
		public int getAvailabilityTests(TypeSuite typeSuite) {
			Map<State,Integer> testCasesState = getTestCasesByState(typeSuite);
			return 
				testCasesState.get(State.OK) + 
				testCasesState.get(State.INFO) + 
				testCasesState.get(State.WARN) + 
				testCasesState.get(State.DEFECT);
		}
		
		public String getUrlReport(TypeSuite typeSuite, String hostTestMaker) {
			SuiteTestCasesData suiteData = getSuite(typeSuite);
			if (suiteData==null) {
				return null;
			}
			
			SuiteBean suite = suiteData.getSuite();
			String urlReport = suite.getUrlReportHtml().replace("\\", "/");
			if (urlReport.indexOf("http")!=0) {
				urlReport = hostTestMaker + "/" + urlReport;
			}
			return urlReport;
		}
		
		private SuiteTestCasesData getSuite(TypeSuite typeSuite) {
			SuiteTestCasesData suite = suiteNew;
			if (typeSuite==TypeSuite.OLD) {
				suite = suiteOld;
			}
			return suite;
		}
		
		public Map<State,Integer> getTestCasesByState(TypeSuite typeSuite) {
			Map<State,Integer> testCasesByState = getTestCasesByState(suiteNew.getTestCases());
			if (typeSuite==TypeSuite.OLD) {
				if (isSuiteOld()) {
					testCasesByState = getTestCasesByState(suiteOld.getTestCases());
				} else {
					testCasesByState = getInitZeroValues();
				}
			}
			return testCasesByState;
		}
	
		private Map<State,Integer> getTestCasesByState(List<TestCaseBean> listTestCases) {
			Map<State,Integer> mapReturn = getInitZeroValues();
			if (listTestCases==null) {
				return mapReturn;
			}
			for (TestCaseBean testCase : listTestCases) {
				mapReturn.put(
					testCase.getResult(), 
					mapReturn.get(testCase.getResult())+1);
			}
			return mapReturn;
		}
		
		public String getNumTestCases(State state) {
			return getNumTestCases(state, TypeSuite.NEW);
		}
		
		public String getNumTestCases(State state, TypeSuite typeSuite) {
			Map<State,Integer> testCasesState = getTestCasesByState(typeSuite);
			Integer numTestCases = testCasesState.get(state);
			if (numTestCases!=0) {
				return String.valueOf(numTestCases);
			}
			return "";
		}
	}
	
	private class Totals {
		
		private double suiteMinutes = 0;
		private int testCasesAvailabilityNew = 0;
		private int testCasesAvailabilityOld = 0;
		private Map<State,Integer> testCasesByStateNew = getInitZeroValues();
		private Map<State,Integer> testCasesByStateOld = getInitZeroValues();
		
		public void add(RowSuiteData rowSuiteData) {
			try {
				suiteMinutes+=Double.valueOf(rowSuiteData.getTimeMinutes());
			} catch (NumberFormatException e) {
				Log4jTM.getLogger().warn(String.format("Value %s not stored in totals because isn't float ", rowSuiteData.getTimeMinutes()));
			}
			testCasesAvailabilityNew+=Integer.valueOf(rowSuiteData.getAvailabilityTests(TypeSuite.NEW));
			addTestCases(rowSuiteData, TypeSuite.NEW);
			if (isOldData) {
				testCasesAvailabilityOld+=Integer.valueOf(rowSuiteData.getAvailabilityTests(TypeSuite.OLD));
				addTestCases(rowSuiteData, TypeSuite.OLD);
			}
		}
		
		private void addTestCases(RowSuiteData rowSuiteData, TypeSuite typeSuite) {
			Map<State,Integer> testCasesByState = testCasesByStateNew;
			if (typeSuite==TypeSuite.OLD) {
				testCasesByState = testCasesByStateOld;
			}
		    for (State state : State.values()) {
			    Integer testCasesInRow = rowSuiteData.getTestCasesByState(typeSuite).get(state);
				Integer testCasesTotal = testCasesByState.get(state);
				testCasesByState.put(state, testCasesTotal + testCasesInRow);
			}
		}
		
		public Integer getTotalTests(TypeSuite typeSuite) {
			Map<State,Integer> testCasesByState = testCasesByStateNew;
			if (typeSuite==TypeSuite.OLD) {
				testCasesByState = testCasesByStateOld;
			}
			Integer testCasesTotal = 0;
		    for (State state : State.values()) {
				testCasesTotal+=testCasesByState.get(state);
			}
		    return testCasesTotal;
		}
		
		public String getPercentageTestCases(State state) {
			return getPercentageTestCases(state, TypeSuite.NEW);
		}
		
		public String getPercentageTestCases(State state, TypeSuite typeSuite) {
			Float percentage = getPercentageTestCasesFloat(state, typeSuite);
			DecimalFormat df1 = new DecimalFormat("0.00");
			return df1.format(percentage);
		}
		
		public Float getPercentageTestCasesFloat(State state, TypeSuite typeSuite) {
			Map<State,Integer> testCasesByState = getTestCasesByState(typeSuite);
			Float percentage = ((testCasesByState.get(state) / getTotalTests(typeSuite).floatValue()) * 100);
			return percentage;
		}
		
		public String getPercentageTestCasesAvailable() {
			return getPercentageTestCasesAvailable(TypeSuite.NEW);
		}
		
		public String getPercentageTestCasesAvailable(TypeSuite typeSuite) {
			Integer totalTests = getTotalTests(typeSuite);
			Integer totalAvailab = getNumTestCasesAvailability(typeSuite);
			DecimalFormat df1 = new DecimalFormat("0.00");
			return df1.format(((totalAvailab / totalTests.floatValue()) * 100));
		}

		public String getSuiteMinutes() {
			DecimalFormat df1 = new DecimalFormat("0.00");
			return df1.format(suiteMinutes);
		}

		public String getTestCasesAvailability() {
			return getTestCasesAvailability(TypeSuite.NEW);
		}
		
		public String getTestCasesAvailability(TypeSuite typeSuite) {
			Integer numTestsAvailab = getNumTestCasesAvailability(typeSuite);
			DecimalFormat df2 = new DecimalFormat("0");
		    return df2.format(numTestsAvailab);
		}
		
		private Integer getNumTestCasesAvailability(TypeSuite typeSuite) {
			if (typeSuite==TypeSuite.NEW) {
			    return testCasesAvailabilityNew;
			}
			return testCasesAvailabilityOld;
		}

		public Map<State, Integer> getTestCasesByState(TypeSuite typeSuite) {
			if (typeSuite==TypeSuite.NEW) {
				return testCasesByStateNew;
			}
			return testCasesByStateOld;
		}
		
		public String getNumTestCases(State state) {
			return getNumTestCases(state, TypeSuite.NEW);
		}
		
		public String getNumTestCases(State state, TypeSuite typeSuite) {
			Map<State,Integer> testCasesState = getTestCasesByState(typeSuite);
			Integer numTestCases = testCasesState.get(state);
			if (numTestCases!=0) {
				return String.valueOf(numTestCases);
			}
			return "";
		}

	}
}
