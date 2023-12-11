package com.github.jorge2m.testmaker.testreports.html;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.testng.ISuite;
import org.testng.reporters.EmailableReporter;
import org.testng.xml.XmlSuite;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.ConstantesTM;
import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.domain.InputParamsTM.TypeAccess;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteBean;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseBean;
import com.github.jorge2m.testmaker.domain.suitetree.TestRunBean;
import com.github.jorge2m.testmaker.service.webdriver.maker.FactoryWebdriverMaker.EmbeddedDriver;
import com.github.jorge2m.testmaker.service.webdriver.maker.brwstack.BrowserStackDataDesktop;
import com.github.jorge2m.testmaker.service.webdriver.maker.brwstack.BrowserStackDataMobil;
import com.github.jorge2m.testmaker.testreports.browserstack.BrowserStackRestClient;
import com.github.jorge2m.testmaker.testreports.stepstore.StepEvidence;
import com.github.jorge2m.testmaker.testreports.testcasestore.TestCaseEvidence;

import static com.github.jorge2m.testmaker.testreports.stepstore.StepEvidence.*;

public class GenerateReports extends EmailableReporter {
	
	private SuiteBean suite;
	private SuiteTM suiteTM;
	private InputParamsTM inputParamsSuite;
	private List<Integer> treeTable;
	private String outputDirectory = "";
	private String reportHtml = "";
	private String outputLibrary = "../..";
	private String pathStatics = outputLibrary + "/static";

	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		super.generateReport(xmlSuites, suites, outputDirectory);
		this.suiteTM = ((SuiteTM)xmlSuites.get(0));
		this.suite = suiteTM.getSuiteBean();
		this.inputParamsSuite = suiteTM.getInputParams();
		this.treeTable = getMapTree(suite);
		this.outputDirectory = outputDirectory;
		try {
			deployStaticsIfNotExist();
			generateReportHTML();
		} 
		catch (Exception e) {
			suiteTM.getLogger().fatal("Problem generating ReportHTML", e);
		}
	}

	private void deployStaticsIfNotExist() throws Exception {
		var resExtractor = ResourcesExtractor.getNew();
		String pathDirectoryInFromResources =  ConstantesTM.NAME_DIRECTORY_STATICS;
		resExtractor.copyDirectoryResources(
			pathDirectoryInFromResources, 
			outputDirectory + "/../../" + pathDirectoryInFromResources);
	}

	private void generateReportHTML() {
		pintaCabeceraHTML();
		pintaHeadersTableMain();	
		pintaTestRunsOfSuite();
		pintaCierreHTML();
		createFileReportHTML();
	}

    void pintaHeadersTableMain() {
    	reportHtml+=
        	"<table id=\"tableMain\" class=\"tablemain\">" + 
            "<thead>\n" + 
            "  <tr id=\"header1\">\n" + 
            "    <th colspan=\"13\" class=\"head\">" + 
            "      <div id=\"titleReport\">" + suite.getName() + " - " + suite.getApp() + ", " + suite.getChannel() + " (Id: " + suite.getIdExecSuite() + ")" +
            "        <span id=\"descrVersion\">" + suite.getVersion() + "</span>" +
            "        <span id=\"browser\">" + suite.getDriver() + "</span>" + 
            "        <span id=\"url\"><a id=\"urlLink\" href=\"" + suite.getUrlBase() + "\">" + suite.getUrlBase() + "</a></span>" + 
            "      </div>" + 
                   getDivBrowserStack() +
            "    </th>\n" + 
            "  </tr>\n" +
            "  <tr id=\"header2\">" + 
            "    <th style=\"display:none;\"></th>\n" + 
            "    <th rowspan=\"2\">Methods Sort: <a href=\"#\" class=\"link-sort-table asc\">A-Z</a> <a href=\"#\" class=\"link-sort-table desc\">Z-A</a> <a href=\"#\" onclick=\"location.reload()\" class=\"link-sort-table reset\">Reset</a></th>" + 
            "    <th rowspan=\"2\">Sons</th>" + 
            "    <th rowspan=\"2\">Result</th>" + 
            "    <th rowspan=\"2\">Time</th>" + 
            "    <th rowspan=\"2\">Evidences</th>" + 
            "    <th class=\"size20\" rowspan=\"2\">Description / Action / Validation</th>" + 
            "    <th class=\"size15\" rowspan=\"2\">Result expected</th>" +
            "    <th rowspan=\"2\">Init</th>" + 
            "    <th rowspan=\"2\">End</th>" +                                
            "    <th rowspan=\"2\">Class / Method</th>" + 
            "  </tr>\n" + 
            "  <tr></tr>\n" +
        	"   </thead>\n";
    }

	private String getDivBrowserStack() {
		String urlBuildBrowserStack = getUrlBuildBrowserStack();
		if ("".compareTo(urlBuildBrowserStack)!=0) {
			String initialDiv = 
				"<div style=\"float:right;\">" +
				"	<a id=\"linkBrowserStack\" href=\"" + urlBuildBrowserStack + "\" target=\"_blank\">" +		
				"		<div style=\"float:left;\">" + 
				"			<div style=\"padding-right:4px;\">BrowserStack Report</div>";
			
			String finalDiv = 
				"		</div>" + 
				"		<div style=\"float:left;\">" +
				"			<img width=\"53\" src=\"../../static/images/browserstack-logo.svg\" title=\"BrowserStack Report\">" +
				"		</div>" +
				"	</a>" +
				"</div>";
			
			String dataDiv = "";
			if (inputParamsSuite.getChannel()==Channel.desktop) {
				var bsData = new BrowserStackDataDesktop(inputParamsSuite);
				dataDiv = 
					"<div style=\"font-size:11;padding-right:9px;\">" + bsData.getOs() + " " + bsData.getOsVersion() + "</div>" + 
					"<div style=\"font-size:11;padding-right:9px;\">" + bsData.getBrowser() + " " + bsData.getBrowserVersion() + "</div>";
				if (bsData.getResolution()!=null && "".compareTo(bsData.getResolution())!=0) {
					dataDiv+=
						"<div style=\"font-size:11;padding-right:4px;\">" + bsData.getResolution() + "</div>";
				}
			} else {
				var bsData = new BrowserStackDataMobil(inputParamsSuite);
				dataDiv = 
					"<div style=\"font-size:11;padding-right:9px;\">" + bsData.getOs() + " " + bsData.getOsVersion() + "</div>" +
					"<div style=\"font-size:11;padding-right:8px;\">" + bsData.getDevice() + "</div>" +
					"<div style=\"font-size:11;padding-right:8px;\">Mobil Real: " + bsData.getRealMobile() + "</div>" +
					"<div style=\"font-size:11;padding-right:9px;\">" + bsData.getBrowser() + "</div>";
			}
			
			return (
				initialDiv +
				dataDiv +
				finalDiv);
		}
		return "";
	}

	private String getUrlBuildBrowserStack() {
		if (inputParamsSuite.getDriver().compareTo(EmbeddedDriver.browserstack.name())==0) {
			String user = inputParamsSuite.getBStackUser();
			String password = inputParamsSuite.getBStackPassword();
			var client = new BrowserStackRestClient(user, password);
			return client.getUrlBuild(suite.getIdExecSuite());
		}
		return "";
	}
    
    void pintaCabeceraHTML() {
        reportHtml+="<html>\n";
        reportHtml+="<head>\n";
        reportHtml+="<meta charset=\"utf-8\">\n";
        reportHtml+="       <title>JQTreeTable</title>\n";
        reportHtml+="       <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js\" type=\"text/javascript\"></script>";
        reportHtml+="       <script type=\"text/javascript\" src=\"" + pathStatics + "/js/ReportLibrary.js\"></script>\n";

        reportHtml+="<script type=\"text/javascript\">\n";
        reportHtml+="$(function(){//I       AS" + " " + "+ nitialise the treetable\n";
        reportHtml+="  var map2=" + treeTable + ";\n";
        reportHtml+="  var options1 = {openImg: \"" + pathStatics + "/images/tv-collapsable.gif\", " + 
                                      "shutImg: \"" + pathStatics + "/images/tv-expandable.gif\", " +
                                      "leafImg: \"" + pathStatics + "/images/tv-item.gif\", " +
                                      "lastOpenImg: \"" + pathStatics + "/images/tv-collapsable-last.gif\", " + 
                                      "lastShutImg: \"" + pathStatics + "/images/tv-expandable-last.gif\", " + 
                                      "lastLeafImg: \"" + pathStatics + "/images/tv-item-last.gif\", " + 
                                      "vertLineImg: \"" + pathStatics + "/images/vertline.gif\", " + 
                                      "blankImg: \"" + pathStatics + "/images/blank.gif\", collapse: false, column: 1, striped: true, highlight: true, state:true};\n";
        reportHtml+="  $(\"#treet2\").jqTreeTable(map2, {" + 
                                       "openImg: \"" + pathStatics + "/images/fopen.gif\", " + 
                                       "shutImg: \"" + pathStatics + "/images/fshut.gif\", " + 
                                       "leafImg: \"" + pathStatics + "/images/new.gif\", " + 
                                       "lastOpenImg: \"" + pathStatics + "/images/fopen.gif\", " + 
                                       "lastShutImg: \"" + pathStatics + "/images/fshut.gif\", " + 
                                       "lastLeafImg: \"" + pathStatics + "/images/new.gif\", " + 
                                       "vertLineImg: \"" + pathStatics + "/images/blank.gif\", " + 
                                       "blankImg: \"" + pathStatics + "/images/blank.gif\", collapse: false, column: 1, striped: true, highlight: true, state:false});\n";
        reportHtml+="});\n";
        reportHtml+="var outputReports = '" + outputLibrary + "';";
        reportHtml+="</script>\n";
        reportHtml+="       <link href=\"" + pathStatics + "/css/Report.css\" rel=\"stylesheet\" type=\"text/css\">\n";
        reportHtml+="</head>\n";
        
        //Body
        reportHtml+="<body class=\"body\" onload=\"setBrowserType();setSizeTable();\">\n";
        reportHtml+="<div id=\"divShow\">\n";
        reportHtml+="   <a href=\"javascript:show_hide_all('tableMain',true, outputReports);\" id=\"linkShow\">Show All</a>\n";
        reportHtml+="</div>\n";
        reportHtml+="<div class=\"divTestNG\">";
        reportHtml+="  <a href=\"emailable-report.html\" target=\"_blank\" class=\"linkTestNG\">Emailable Report</a>";
        reportHtml+="</div>\n";
        reportHtml+="<div class=\"divTestNG\">";
        reportHtml+="  <a href=\"" + ConstantesTM.MAME_LOG_FILE_SUITE + "\" target=\"_blank\" class=\"linkTestNG\">" + ConstantesTM.MAME_LOG_FILE_SUITE + "</a>";
        reportHtml+="</div>";        
        reportHtml+="<br>\n";
        reportHtml+="<br>\n";
    }

	void pintaTestRunsOfSuite() {
		reportHtml+="<tbody id=\"treet2\">\n";
		for (var testRun : suite.getListTestRun()) {
			var format = DateFormat.getDateTimeInstance();
			String deviceInfo = "";
			if (testRun.getDevice()!=null && "".compareTo(testRun.getDevice())!=0) {
				deviceInfo = " [" + testRun.getDevice() + "]";
			}
			reportHtml+= 
				"<tr class=\"testrun\">" +
				"  <td style=\"display:none;\"></td>\n" + 
				"  <td class=\"nowrap\">" + testRun.getName() + deviceInfo + "</td>" + 
				"  <td>" + testRun.getNumberTestCases() + "</td>" + 
				"  <td><div class=\"result" + testRun.getResult() + "\">" + testRun.getResult() + "</div></td>" + 
				"  <td>" + testRun.getDurationMillis() + "</td>" + "               <td></td>" + 
				"  <td></td>" + 
				"  <td></td>" +
				"  <td>" + format.format(testRun.getInicioDate()) + "</td>" + 
				"  <td>" + format.format(testRun.getFinDate()) + "</td>" +
				"  <td></td>" + 
				"</tr>\n";

			pintaTestCasesOfTestRun(testRun);
		}
	}

	void pintaTestCasesOfTestRun(TestRunBean testRun) {
		var listTestCases = testRun.getListTestCase();
		String TagTimeout = "@TIMEOUTSTEP";
		for (int i=0; i<listTestCases.size(); i++) {
			var testCase = listTestCases.get(i);
			var format = new SimpleDateFormat("HH:mm:ss");
			
			reportHtml+= 
				"<tr class=\"method\"" + " met=\"" + testCase.getIndexInTestRun() + "\">" +
				"  <td style=\"display:none;\"></td>\n" + 
				"  <td class=\"nowrap\">" + testCase.getNameUnique() + "</td>" + 
				"  <td>" + testCase.getNumberSteps() + "</td>" + 
				"  <td><div class=\"result" + testCase.getResult() + "\">" + testCase.getResult() + "</div></td>" + 
				"  <td>" + testCase.getDurationMillis() + "</td>" +
				"  <td>" + getLinksEvidencesTestCase(testCase) + "</td>" +
				"  <td colspan=2>" + testCase.getDescription() + "</td>" + 
				"  <td>" + TagTimeout + format.format(testCase.getInicioDate()) + "</td>" + 
				"  <td>" + TagTimeout + format.format(testCase.getFinDate()) + "</td>" +
				"  <td>" + testCase.getClassSignature() + "</td>" + 
				"</tr>\n";

			boolean timeoutStep = pintaStepsOfTestCase(testCase);
			String font = "<font>";
			if (timeoutStep) {
				font = "<font class=\"timeout\">";
			}
			reportHtml = reportHtml.replace(TagTimeout, font);
		}
	}
	
	private String getLinksEvidencesTestCase(TestCaseBean testCase) {
		String linksTest = "";
		if (TestCaseEvidence.EXCEPTION.fileExists(testCase)) {
			linksTest+= 
				"<a href=\"" + getRelativePathEvidencia(testCase, TestCaseEvidence.EXCEPTION) + "\" target=\"_blank\">" + 
				"<img width=\"22\" src=\"" + pathStatics + "/images/" + TestCaseEvidence.EXCEPTION.getNameIcon() + "\" title=\"" + TestCaseEvidence.EXCEPTION.getTagInfo() + "\"/>" +
				"</a>";
		}
		
		if (TestCaseEvidence.LOGS.fileExists(testCase)) {
			linksTest+=
				"<a href=\"" + getRelativePathEvidencia(testCase, TestCaseEvidence.LOGS) + "\" target=\"_blank\">" + 
				"<img width=\"22\" src=\"" + pathStatics + "/images/" + TestCaseEvidence.LOGS.getNameIcon() + "\" title=\"" + TestCaseEvidence.LOGS.getTagInfo() + "\"/>" +
				"</a>";
		}
		
		if ("".compareTo(linksTest)==0) {
			return "<br><br>";
		}
		
		return linksTest; 
	}

	private boolean pintaStepsOfTestCase(TestCaseBean testCase) {
		boolean timeout = false;
		int stepNumber = 0;
		for (var step : testCase.getListStep()) {
			stepNumber+=1;

			String linkHardcopy = "";
			if (IMAGEN.fileExists(step)) {
				linkHardcopy = 
					"<a href=\"" + getRelativePathEvidencia(step, IMAGEN) + "\" target=\"_blank\">" + 
					"<img width=\"22\" src=\"" + pathStatics + "/images/" + IMAGEN.getNameIcon() + "\" title=\"" + IMAGEN.getTagInfo() + "\"/>" +
					"</a>";
			}
			
			String linkHtml = "";
			if (HTML.fileExists(step)) {
				linkHtml = 
					"<a href=\"" + getRelativePathEvidencia(step, HTML) + "\" target=\"_blank\">" + 
					"<img width=\"22\" src=\"" + pathStatics + "/images/" + HTML.getNameIcon() + "\" title=\"" + HTML.getTagInfo() + "\"/>" +
					"</a>";
			}

			String linkError = "";
			if (ERROR_PAGE.fileExists(step)) {
				linkError = 
					"<a href=\"" + getRelativePathEvidencia(step, ERROR_PAGE) + "\" target=\"_blank\">" + 
					"<img width=\"22\" src=\"" + pathStatics + "/images/" + ERROR_PAGE.getNameIcon() + "\" title=\"" + ERROR_PAGE.getTagInfo() + "\"/>" +
					"</a>";
			}

			String harpFileStep = HARP.getPathFile(step);
			File indexFile = new File(harpFileStep);
			String linkHarp = "";
			if (HARP.fileExists(step)) {
				String pathHARP = getDnsOfFileReport(
					indexFile.getAbsolutePath(), 
					inputParamsSuite.getWebAppDNS(), 
					inputParamsSuite.getTypeAccess()).replace('\\', '/');
				
				linkHarp = 
					" \\ <a href=\"" + ConstantesTM.URL_SOFTWAREISHARD + pathHARP + "\" target=\"_blank\">" +
					"<img width=\"22\" src=\"" + pathStatics + "/images/" + HARP.getNameIcon() + "\" title=\"" + HARP.getTagInfo() + "\"/>" +
					"</a>";
			}

			String harFileStep = HAR.getPathFile(step);
			String linkHar = "";
			indexFile = new File(harFileStep);
			if (indexFile.exists()) {
				linkHar = " \\ <a href=\"" + getRelativePathEvidencia(step, HAR) + "\" target=\"_blank\">NetJSON</a>";
				
				linkHar = 
					" \\ <a href=\"" + getRelativePathEvidencia(step, HAR) + "\" target=\"_blank\">" +
					"<img width=\"22\" src=\"" + pathStatics + "/images/" + HAR.getNameIcon() + "\" title=\"" + HAR.getTagInfo() + "\"/>" +
					"</a>";
			}

			long diffInMillies = step.getHoraFin().getTime() - step.getHoraInicio().getTime();
			String tdClassDate = "<td>";
			if (diffInMillies > 30000) {
				tdClassDate = "<td><font class=\"timeout\">";
				timeout = true;
			}
			var format = new SimpleDateFormat("HH:mm:ss");
			String diffInMilliesStr = String.valueOf(diffInMillies);
			String fechaFinStr = format.format(step.getHoraFin());
			if (diffInMillies < 0) {
				diffInMilliesStr = "?";
				fechaFinStr = "?";
			}

			reportHtml+=
				"<tr class=\"step collapsed\"" + " met=\"" + testCase.getIndexInTestRun() + "\">" +
				"     <td style=\"display:none;\"></td>\n" +
				"     <td class=\"nowrap\">Step " + stepNumber + "</td>" + 
				"     <td>" + step.getNumChecksTM() + "</td>" + 
				"     <td><div class=\"result" + step.getResultSteps() + "\">" + step.getResultSteps() + "</div></td>" + 
				"     <td>" + diffInMilliesStr + "</td>" + 
				"     <td class=\"nowrap\">" + linkHardcopy + linkHtml + linkError + linkHarp + linkHar + "</td>" + 
				"     <td>" + step.getDescripcion() + "</td>" + 
				"     <td>" + step.getResExpected() + "</td>" +
				tdClassDate + format.format(step.getHoraInicio()) + "</td>" + 
				tdClassDate + fechaFinStr + "</td>" +
				"     <td>" + step.getNameClass() + " / " + step.getNameMethod() + "</td>" +
				"</tr>\n";

			pintaValidacionesStep(step);
		}

		return timeout;
	}

	private String getRelativePathEvidencia(StepTM step, StepEvidence evidence) {
		String fileName = evidence.getNameFileEvidence(step);
		String testRunName = step.getTestRunParent().getName();
		String testCaseNameUnique = step.getTestCaseParent().getNameUnique();
		return ("./" + testRunName + "/" + testCaseNameUnique + "/" + fileName);
	}
	
	private String getRelativePathEvidencia(TestCaseBean testcase, TestCaseEvidence evidence) {
		String fileName = evidence.getNameFileEvidence();
		String testRunName = testcase.getTestRunName();
		String testCaseNameUnique = testcase.getNameUnique();
		return ("./" + testRunName + "/" + testCaseNameUnique + "/" + fileName);
	}	

	private void pintaValidacionesStep(StepTM step) {
		var listChecksResult = step.getListChecksTM();
		for (var checksResult : listChecksResult) {
			String descriptValid = checksResult.getHtmlValidationsBrSeparated();
			reportHtml+= 
				"<tr class=\"validation collapsed\"" + " met=\"" + step.getTestCaseParent().getIndexInTestRun() + "\">" +
				"    <td style=\"display:none;\"></td>\n" + 
				"    <td class=\"nowrap\">Validation " + checksResult.getPositionInStep() + "</td>" + 
				"    <td></td>" + 
				"    <td><div class=\"result" + checksResult.getStateValidation() + "\">" + checksResult.getStateValidation() + "</div></td>" + 
				"    <td></td>" + 
				"    <td></td>" + 
				"    <td>" + descriptValid + "</td>" + 
				"    <td></td>" + 
				"    <td></td>" + 
				"    <td></td>" +                        
				"    <td>" + checksResult.getNameClass() + " / " + checksResult.getNameMethod() + "</td>" + 
				"</tr>\n";
		}
	}

	public void pintaCierreHTML() {
		reportHtml+=
			"</tbody>" + 
			"</table><br />\n" +	
			"</body>\n" +
			"</html>\n";
	}

    public void createFileReportHTML() {
        String fileReport = suite.getPathReportHtml();;
        try (Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileReport), "UTF8"))) {
            out.write(reportHtml.toString());
        } 
        catch (Exception e) {
        	suiteTM.getLogger().fatal("Problem creating file ReportHTML", e);
        } 
    }

    static List<Integer> getMapTree(SuiteBean suite) {
    	List<Integer> listMapReturn = new ArrayList<>();
    	for (var testRun : suite.getListTestRun()) {
    		listMapReturn.add(0);
    		int posLastTestRun = listMapReturn.size();
    		for (var testCase : testRun.getListTestCase()) {
    			listMapReturn.add(posLastTestRun);
        		int posLastTest = listMapReturn.size();
    			for (var step : testCase.getListStep()) {
    				listMapReturn.add(posLastTest);
            		int posLastStep = listMapReturn.size();
            		for (int i=0; i<step.getListChecksTM().size(); i++) {
        				listMapReturn.add(posLastStep);
            		}
    			}
    		}
    	}
    	return listMapReturn;
    }

    /**
     * Obtiene la DNS de un fichero ubicado dentro del contexto de la aplicación de tests
     * @param serverDNS: del tipo "http://robottest.mangodev.net + :port si fuera preciso)  
     */
    public static String getDnsOfFileReport(String filePath, String applicationDNS, TypeAccess typeAccess) {
        String pathReport = filePath.substring(filePath.indexOf(ConstantesTM.DIRECTORY_OUTPUT_TESTS));
        if (applicationDNS!=null && "".compareTo(applicationDNS)!=0) {
            return (applicationDNS + "/" + pathReport);
        } else {
        	switch (typeAccess) {
        	case CmdLine:
        	case Bat:
        		var patternDrive = Pattern.compile("^[a-zA-Z]:");
        		return (patternDrive.matcher(filePath).replaceFirst("\\\\\\\\" + getNamePC()));
        	case Rest:
        	default:
        		return (pathReport.replace("\\", "/"));
        	}
        }
    }
	
	private static String getNamePC() {
		String hostname = "";
		try {
			var addr = InetAddress.getLocalHost();
			hostname = addr.getHostName();
		}
		catch (UnknownHostException ex) {
			hostname = "Unknown";
		}
		return hostname;
	}
	
}
