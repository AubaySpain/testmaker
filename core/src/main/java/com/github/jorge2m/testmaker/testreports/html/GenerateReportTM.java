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
import java.util.Optional;
import java.util.regex.Pattern;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.ConstantesTM;
import com.github.jorge2m.testmaker.domain.InputParamsBasic;
import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.domain.InputParamsTM.TypeAccess;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteBean;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseBean;
import com.github.jorge2m.testmaker.domain.suitetree.TestRunBean;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.service.webdriver.maker.FactoryWebdriverMaker.EmbeddedDriver;
import com.github.jorge2m.testmaker.service.webdriver.maker.brwstack.BrowserStackDataDesktop;
import com.github.jorge2m.testmaker.service.webdriver.maker.brwstack.BrowserStackDataMobil;
import com.github.jorge2m.testmaker.testreports.browserstack.BrowserStackRestClient;
import com.github.jorge2m.testmaker.testreports.stepstore.compareimages.ComparatorImages;
import com.github.jorge2m.testmaker.testreports.testcasestore.TestCaseEvidence;

import static com.github.jorge2m.testmaker.testreports.stepstore.StepEvidence.*;

public class GenerateReportTM {
	
	private final SuiteBean suite1;
	private final SuiteBean suite2;
	private final InputParamsTM inputParams;
	private final List<Integer> treeTable;
	private final String outputDirectory;
	private final DynatraceLinks dynatraceLinks;
	
	private String reportHtml = "";
	private String outputLibrary = "../..";
	private String pathStatics = outputLibrary + "/static";

	public GenerateReportTM(SuiteTM suiteTM, String outputDirectory) throws Exception {
		this.suite1 = suiteTM.getSuiteBean();
		this.suite2 = null;
		this.inputParams = suiteTM.getInputParams();
		this.outputDirectory = outputDirectory;
		this.treeTable = getMapTree(suite1);
		this.dynatraceLinks = new DynatraceLinks(suite1);
	}
	
	public GenerateReportTM(SuiteBean suite) throws Exception {
		this.suite1 = suite;
		this.suite2 = null;
		this.inputParams = new InputParamsBasic(suite.getParameters());
		this.treeTable = getMapTree(suite);
		this.outputDirectory = suite.getOutputDirectory();
		this.dynatraceLinks = new DynatraceLinks(suite);
	}
	
	public GenerateReportTM(SuiteBean suite1, SuiteBean suite2) throws Exception {
		this.suite1 = suite1;
		this.suite2 = suite2;
		this.inputParams = new InputParamsBasic(suite1.getParameters());
		this.treeTable = getMapTree(suite1);
		this.outputDirectory = suite1.getOutputDirectory();
		this.dynatraceLinks = new DynatraceLinks(suite1);
	}	
	
	public void generate() throws Exception {
		deployStaticsIfNotExist();
		generateReportHTML();
		if (isCompare()) {
        	TestMaker.getRepository().insertComparedSuites(suite1, suite2);
		}
	}

	private void deployStaticsIfNotExist() throws Exception {
		var resExtractor = ResourcesExtractor.getNew();
		String pathDirectoryInFromResources =  ConstantesTM.NAME_DIRECTORY_STATICS;
		resExtractor.copyDirectoryResources(
			pathDirectoryInFromResources, 
			outputDirectory + "/../../" + pathDirectoryInFromResources);
	}

	private void generateReportHTML() throws Exception {
		pintaCabeceraHTML();
		pintaHeadersTableMain();	
		pintaTestRunsOfSuite();
		pintaCierreHTML();
		createFileReportHTML();
	}

    void pintaHeadersTableMain() {
    	int colsPan=13;
    	if (isCompare()) {
    		colsPan+=4;
    	}
    	
    	reportHtml+=
        	"<table id=\"tableMain\" class=\"tablemain\">" + 
            "<thead>\n" + 
            "  <tr id=\"header1\">\n" + 
            "    <th colspan=\"" + colsPan + "\" class=\"head\">" + 
            "<div id=\"titleReport\">" +
            getTitleReport() + 
            "        <span id=\"descrVersion\">" + suite1.getVersion() + "</span>" +
            "        <span id=\"browser\">" + suite1.getDriver() + "</span>";
    	
            if (suite1.getUrlBase()!=null) {
            	reportHtml+=
            	"        <span id=\"url\"><a id=\"urlLink\" href=\"" + suite1.getUrlBase() + "\">" + suite1.getUrlBase() + "</a></span>";
            }
            
            reportHtml+= 
            "      </div>" + 
                   getDivDynatrace() + 
                   getDivBrowserStack() +
            "    </th>\n" + 
            "  </tr>\n" +
            "  <tr id=\"header2\">" + 
            "    <th style=\"display:none;\"></th>\n" + 
            "    <th rowspan=\"2\">Methods Sort: <a href=\"#\" class=\"link-sort-table asc\">A-Z</a> <a href=\"#\" class=\"link-sort-table desc\">Z-A</a> <a href=\"#\" onclick=\"location.reload()\" class=\"link-sort-table reset\">Reset</a></th>" + 
            "    <th rowspan=\"2\">Sons</th>" + 
            "    <th rowspan=\"2\">Result</th>" + 
            "    <th rowspan=\"2\">Time</th>" + 
            "    <th rowspan=\"2\">Evidences</th>";
            
            if (isCompare()) {
            	reportHtml+=
            		"    <th rowspan=\"2\" class=\"compare\">Result2</th>" +
            		"    <th rowspan=\"2\" class=\"compare\">Time2</th>" +            	
            		"    <th rowspan=\"2\" class=\"compare\">Evidences2</th>" +
            		"    <th rowspan=\"2\" class=\"compare\">Compare</th>";
            }
            
            reportHtml+=
            "    <th class=\"size20\" rowspan=\"2\">Description / Action / Validation</th>" + 
            "    <th class=\"size15\" rowspan=\"2\">Result expected</th>" +
            "    <th rowspan=\"2\">Init</th>" + 
            "    <th rowspan=\"2\">End</th>" +                                
            "    <th rowspan=\"2\">Class / Method</th>" + 
            "  </tr>\n" + 
            "  <tr></tr>\n" +
        	"   </thead>\n";
    }
    
    private String getTitleReport() {
    	String title =  
    		suite1.getName() + " - " + 
    		suite1.getApp() + ", " + 
    		suite1.getChannel();
    	
    	if (isCompare()) {
    		title+=
       			" (Id: "  + 
       			"<a id=\"suitenew\" href=\"" + suite1.getUrlReportHtml() + "\">" + suite1.getIdExecSuite() + "</a> vs " +
       			"<a id=\"suiteorig\" href=\"" + suite2.getUrlReportHtml() + "\">" + suite2.getIdExecSuite() + "</a>)";
    	} else {
    		title+=" (Id: " + suite1.getIdExecSuite() + ")";
    	}
    	return title;
    }

	private String getDivDynatrace() {
		if (!dynatraceLinks.isActive()) {
			return "";
		}
		
		return  
			"<div style=\"float:right;\">" +
			"	<a id=\"linkDynatrace\" href=\"" + dynatraceLinks.getMultidimensionalAnalisisForSuiteURL() + "\" target=\"_blank\">" +		
			"		<div style=\"float:left;\">" +
			"			<img width=\"53\" src=\"../../static/images/dynatrace-logo.png\" title=\"Dynatrace Traffic\">" +
			"		</div>" +
			"	</a>" +
			"</div>";
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
			if (inputParams.getChannel()==Channel.desktop) {
				var bsData = new BrowserStackDataDesktop(inputParams);
				dataDiv = 
					"<div style=\"font-size:11;padding-right:9px;\">" + bsData.getOs() + " " + bsData.getOsVersion() + "</div>" + 
					"<div style=\"font-size:11;padding-right:9px;\">" + bsData.getBrowser() + " " + bsData.getBrowserVersion() + "</div>";
				if (bsData.getResolution()!=null && "".compareTo(bsData.getResolution())!=0) {
					dataDiv+=
						"<div style=\"font-size:11;padding-right:4px;\">" + bsData.getResolution() + "</div>";
				}
			} else {
				var bsData = new BrowserStackDataMobil(inputParams);
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
		if (inputParams.getDriver().compareTo(EmbeddedDriver.browserstack.name())==0) {
			String user = inputParams.getBStackUser();
			String password = inputParams.getBStackPassword();
			var client = new BrowserStackRestClient(user, password);
			return client.getUrlBuild(suite1.getIdExecSuite());
		}
		return "";
	}
    
    void pintaCabeceraHTML() {
        reportHtml+="<html>\n";
        reportHtml+="<head>\n";
        reportHtml+="<meta charset=\"utf-8\">\n";
        reportHtml+="       <title>JQTreeTable</title>\n";
        reportHtml+="       <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js\"></script>";
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
		for (var testRun1 : suite1.getListTestRun()) {
			var format = DateFormat.getDateTimeInstance();
			String deviceInfo = "";
			if (testRun1.getDevice()!=null && "".compareTo(testRun1.getDevice())!=0) {
				deviceInfo = " [" + testRun1.getDevice() + "]";
			}
			reportHtml+= 
				"<tr class=\"testrun\">" +
				"  <td style=\"display:none;\"></td>\n" + 
				"  <td class=\"nowrap\">" + testRun1.getName() + deviceInfo + "</td>" + 
				"  <td>" + testRun1.getNumberTestCases() + "</td>" + 
				"  <td><div class=\"result" + testRun1.getResult() + "\">" + testRun1.getResult() + "</div></td>" + 
				"  <td>" + toSeconds(testRun1.getDurationMillis()) + "</td>" + 
				"  <td></td>"; 
			
			var testRun2Opt = pintaTestRunComparation(testRun1);
				
			reportHtml+=
				"  <td></td>" +					
				"  <td></td>" +
				"  <td>" + format.format(testRun1.getInicioDate()) + "</td>" + 
				"  <td>" + format.format(testRun1.getFinDate()) + "</td>" +
				"  <td></td>" + 
				"</tr>\n";

			pintaTestCasesOfTestRun(testRun1, testRun2Opt);
		}
	}

	private Optional<TestRunBean> pintaTestRunComparation(TestRunBean testRun1) {
		Optional<TestRunBean> testRun2Opt = Optional.empty();
		if (!isCompare()) {
			return testRun2Opt;
		}
		
		testRun2Opt = getSameTestRun(suite2, testRun1);
		if (testRun2Opt.isPresent()) {
			var testRun2 = testRun2Opt.get();
			reportHtml+=
				"  <td class=\"compare\"><div class=\"result" + testRun2.getResult() + "\">" + testRun1.getResult() + "</div></td>" + 
				"  <td class=\"compare\">" + toSeconds(testRun2.getDurationMillis()) + "</td>";
		} else {
			reportHtml+=
				"  <td class=\"compare\"></td>" +
				"  <td class=\"compare\"></td>";					
		}
		
		reportHtml+=
			"  <td class=\"compare\"></td>" +
			"  <td class=\"compare\"></td>";

		return testRun2Opt;
	}
	
	private Optional<TestRunBean> getSameTestRun(SuiteBean suite2, TestRunBean testRun1) {
		for (var testRun2 : suite2.getListTestRun()) {
			if (testRun2.getName().compareTo(testRun1.getName())==0) {
				return Optional.of(testRun2);
			}
		}
		return Optional.empty();
	}
	
	void pintaTestCasesOfTestRun(TestRunBean testRun1, Optional<TestRunBean> testRun2Opt) {
		String TagTimeout = "@TIMEOUTSTEP";
		var listTestCases1 = testRun1.getListTestCase();
		
		for (int i=0; i<listTestCases1.size(); i++) {
			var testCase1 = listTestCases1.get(i);
			var format = new SimpleDateFormat("HH:mm:ss");
			
			reportHtml+= 
				"<tr class=\"method\"" + " met=\"" + testCase1.getIndexInTestRun() + "\">" +
				"  <td style=\"display:none;\"></td>\n" + 
				"  <td class=\"nowrap\">" + testCase1.getNameUnique() + "</td>" + 
				"  <td>" + testCase1.getNumberSteps() + "</td>" + 
				"  <td><div class=\"result" + testCase1.getResult() + "\">" + testCase1.getResult() + "</div></td>" + 
				"  <td>" + toSeconds(testCase1.getDurationMillis()) + "</td>" +
				"  <td>" + getLinksEvidencesTestCase(testCase1) + "</td>";
			
			var testCase2Opt = pintaTestCaseComparation(testRun2Opt, testCase1);
			
			reportHtml+=
				"  <td colspan=2>" + testCase1.getDescription() + "</td>" + 
				"  <td>" + TagTimeout + format.format(testCase1.getInicioDate()) + "</td>" + 
				"  <td>" + TagTimeout + format.format(testCase1.getFinDate()) + "</td>" +
				"  <td>" + testCase1.getClassSignature() + "</td>" + 
				"</tr>\n";

			boolean timeoutStep = pintaStepsOfTestCase(testCase1, testCase2Opt);
			String font = "<font>";
			if (timeoutStep) {
				font = "<font class=\"timeout\">";
			}
			reportHtml = reportHtml.replace(TagTimeout, font);
		}
	}

	private Optional<TestCaseBean> pintaTestCaseComparation(Optional<TestRunBean> testRun2Opt, TestCaseBean testCase1) {
		Optional<TestCaseBean> testCase2Opt = Optional.empty();
		if (!isCompare()) {
			return testCase2Opt;
		}
		
		if (testRun2Opt.isPresent()) {
			testCase2Opt = getSameTestCase(testRun2Opt.get(), testCase1);
			if (testCase2Opt.isPresent()) {
				var testCase2 = testCase2Opt.get();
				reportHtml+=
					"  <td class=\"compare\"><div class=\"result" + testCase2.getResult() + "\">" + testCase2.getResult() + "</div></td>" + 
					"  <td class=\"compare\">" + toSeconds(testCase2.getDurationMillis()) + "</td>";

				reportHtml+=
					"<td class=\"compare\">" +
					getLinksEvidencesTestCase(testCase2Opt.get()) +
					"</td>";
			}
		}
		
		if (testCase2Opt.isEmpty()) {
			reportHtml+=
				"  <td class=\"compare\"></td>" + 
				"  <td class=\"compare\"></td>" +
				"  <td class=\"compare\"></td>";			
		}
		
		reportHtml+="<td class=\"compare\"></td>";
		
		return testCase2Opt;
	}
	
	private Optional<TestCaseBean> getSameTestCase(TestRunBean testRun2, TestCaseBean testCase1) {
		for (var testCase2 : testRun2.getListTestCase()) {
			if (testCase2.getNameUnique().compareTo(testCase1.getNameUnique())==0) {
				return Optional.of(testCase2);
			}
		}
		return Optional.empty();
	}
	
	private String getLinksEvidencesTestCase(TestCaseBean testCase) {
		String linksTest = getHtmlDynatraceLink(testCase);
		for (var testCaseEvidence : TestCaseEvidence.values()) {
			linksTest+=getHtmlLink(testCase, testCaseEvidence);
		}
		
		if ("".compareTo(linksTest)==0) {
			return "<br><br>";
		}
		
		return linksTest; 
	}
	
	private String getHtmlDynatraceLink(TestCaseBean testCase) {
		if (!dynatraceLinks.isActive()) {
			return "";
		}

		return getHtmlLink(
			dynatraceLinks.getDistributedTracesForTestCaseURL(testCase),
			"dynatrace-icon.svg",
			"Dynatrace distributed traces");
	}
	
	private String getHtmlLink(TestCaseBean testCase, TestCaseEvidence testCaseEvidence) {
		if (!testCaseEvidence.fileExists(testCase)) {
			return "";
		}
		return getHtmlLink(
				getPathEvidencia(testCase, testCaseEvidence),
				testCaseEvidence.getNameIcon(),
				testCaseEvidence.getTagInfo());			
	}
	
	private String getHtmlLink(String path, String nameIcon, String tagInfo) {
		return
			"<a href=\"" + path + "\" target=\"_blank\">" + 
			"<img width=\"25\" style=\"padding:3\" src=\"" + pathStatics + "/images/" + nameIcon + "\" title=\"" + tagInfo + "\"/>" +
			"</a>";		
	}

	private boolean pintaStepsOfTestCase(TestCaseBean testCase1, Optional<TestCaseBean> testCase2Opt) {
		boolean timeout = false;
		int stepNumber = 0;
		for (var step1 : testCase1.getListStep()) {
			stepNumber+=1;
			
			long diffInMillies = step1.getHoraFin().getTime() - step1.getHoraInicio().getTime();
			String tdClassDate = "<td>";
			if (diffInMillies > 30000) {
				tdClassDate = "<td><font class=\"timeout\">";
				timeout = true;
			}
			var format = new SimpleDateFormat("HH:mm:ss");
			String diffInSecondsStr = toSeconds(diffInMillies);
			String fechaFinStr = format.format(step1.getHoraFin());
			if (diffInMillies < 0) {
				diffInSecondsStr = "?";
				fechaFinStr = "?";
			}

			reportHtml+=
				"<tr class=\"step collapsed\"" + " met=\"" + testCase1.getIndexInTestRun() + "\">" +
				"     <td style=\"display:none;\"></td>\n" +
				"     <td class=\"nowrap\">Step " + stepNumber + "</td>" + 
				"     <td>" + step1.getNumChecksTM() + "</td>" + 
				"     <td><div class=\"result" + step1.getResultSteps() + "\">" + step1.getResultSteps() + "</div></td>" + 
				"     <td>" + diffInSecondsStr + "</td>" + 
				"     <td class=\"nowrap\">" + getLinksStepEvidences(testCase1, step1) + "</td>";
			
			var step2Opt = pintaStepComparation(testCase1, testCase2Opt, step1);
				
			reportHtml+=
				"     <td>" + step1.getDescripcion() + "</td>" + 
				"     <td>" + step1.getResExpected() + "</td>" +
				tdClassDate + format.format(step1.getHoraInicio()) + "</td>" + 
				tdClassDate + fechaFinStr + "</td>" +
				"     <td>" + step1.getNameClass() + " / " + step1.getNameMethod() + "</td>" +
				"</tr>\n";

			pintaValidacionesStep(testCase1, step1, step2Opt);
		}

		return timeout;
	}

	private Optional<StepTM> pintaStepComparation(
			TestCaseBean testCase1, Optional<TestCaseBean> testCase2Opt, StepTM step1) {

		Optional<StepTM> step2Opt = Optional.empty();
		if (!isCompare()) {
			return step2Opt;
		}

		if (testCase2Opt.isPresent()) {
			step2Opt = getSameStep(testCase2Opt.get(), step1);
			if (step2Opt.isPresent()) {
				var step2 = step2Opt.get();
				long diffInMillies = step2.getHoraFin().getTime() - step2.getHoraInicio().getTime();
				String diffInSecondsStr = toSeconds(diffInMillies);
				
				reportHtml+=
					"     <td class=\"compare\"><div class=\"result" + step2.getResultSteps() + "\">" + step2.getResultSteps() + "</div></td>" + 
					"     <td class=\"compare\">" + diffInSecondsStr + "</td>";

				reportHtml+="     <td class=\"nowrap compare\">";
				reportHtml+=getLinksStepEvidences(testCase2Opt.get(), step2Opt.get());
				reportHtml+="     </td>";
			}
		}
		
		if (step2Opt.isEmpty()) {
			reportHtml+=
				"<td class=\"compare\"></td>" + 
				"<td class=\"compare\"></td>" +
				"<td class=\"compare\"></td>";
		}
		
		reportHtml+="<td class=\"compare\">";
		
		if (step2Opt.isPresent()) {
			var comparatorImages = ComparatorImages.make(testCase1, step1, testCase2Opt.get(), step2Opt.get());
			if (comparatorImages.compareAndSave()) {
				reportHtml+=createComparisonLink(comparatorImages, testCase1, step1);
			}
		}
		reportHtml+="</td>";
			
		return step2Opt;
	}
	
	private String createComparisonLink(ComparatorImages comparatorImages, TestCaseBean testCase1, StepTM step1) {
		String pathImageCompared = comparatorImages.getPathImageCompared(testCase1, step1);
	    return 
	    	"<a href=\"" + pathImageCompared + "\" target=\"_blank\">" +
	        "<img width=\"22\" src=\"" + pathStatics + "/images/" + ComparatorImages.getNameIcon() + "\">" +
	        "</a>";
	}	
	
	private Optional<StepTM> getSameStep(TestCaseBean testCase2, StepTM step1) {
		for (var step2 : testCase2.getListStep()) {
			if (step2.getNumber().equals(step1.getNumber()) &&
				step2.getDescripcion().equals(step1.getDescripcion())) {
				return Optional.of(step2);
			}
		}
		for (var step2 : testCase2.getListStep()) {
			if (step2.getDescripcion().equals(step1.getDescripcion())) {
				return Optional.of(step2);
			}
		}
		for (var step2 : testCase2.getListStep()) {
			if (step2.getNumber().equals(step1.getNumber())) {
				return Optional.of(step2);
			}
		}
		return Optional.empty();
	}

	private String getLinksStepEvidences(TestCaseBean testCase, StepTM step) {
		String linkHardcopy = "";
		if (IMAGEN.fileExists(testCase, step)) {
			linkHardcopy = 
				"<a href=\"" + IMAGEN.getPathEvidencia(testCase, step) + "\" target=\"_blank\">" + 
				"<img width=\"22\" src=\"" + pathStatics + "/images/" + IMAGEN.getNameIcon() + "\" title=\"" + IMAGEN.getTagInfo() + "\"/>" +
				"</a>";
		}
		
		String linkHtml = "";
		if (HTML.fileExists(testCase, step)) {
			linkHtml = 
				"<a href=\"" + HTML.getPathEvidencia(testCase, step) + "\" target=\"_blank\">" + 
				"<img width=\"22\" src=\"" + pathStatics + "/images/" + HTML.getNameIcon() + "\" title=\"" + HTML.getTagInfo() + "\"/>" +
				"</a>";
		}
	
		String linkError = "";
		if (ERROR_PAGE.fileExists(testCase, step)) {
			linkError = 
				"<a href=\"" + ERROR_PAGE.getPathEvidencia(testCase, step) + "\" target=\"_blank\">" + 
				"<img width=\"22\" src=\"" + pathStatics + "/images/" + ERROR_PAGE.getNameIcon() + "\" title=\"" + ERROR_PAGE.getTagInfo() + "\"/>" +
				"</a>";
		}

		String harpFileStep = HARP.getPathFile(testCase, step);
		File indexFile = new File(harpFileStep);
		String linkHarp = "";
		if (HARP.fileExists(testCase, step)) {
			String pathHARP = getDnsOfFileReport(
				indexFile.getAbsolutePath(), 
				inputParams.getWebAppDNS(), 
				inputParams.getTypeAccess()).replace('\\', '/');
			
			linkHarp = 
				" \\ <a href=\"" + ConstantesTM.URL_SOFTWAREISHARD + pathHARP + "\" target=\"_blank\">" +
				"<img width=\"22\" src=\"" + pathStatics + "/images/" + HARP.getNameIcon() + "\" title=\"" + HARP.getTagInfo() + "\"/>" +
				"</a>";
		}
	
		String harFileStep = HAR.getPathFile(testCase, step);
		String linkHar = "";
		indexFile = new File(harFileStep);
		if (indexFile.exists()) {
			linkHar = 
				" \\ <a href=\"" + HAR.getPathEvidencia(testCase, step) + "\" target=\"_blank\">" +
				"<img width=\"22\" src=\"" + pathStatics + "/images/" + HAR.getNameIcon() + "\" title=\"" + HAR.getTagInfo() + "\"/>" +
				"</a>";
		}
		
		return linkHardcopy + linkHtml + linkError + linkHarp + linkHar;
	}

	private String getPathEvidencia(TestCaseBean testcase, TestCaseEvidence evidence) {
		String idSuite = testcase.getIdExecSuite();
		String fileName = evidence.getNameFileEvidence();
		String testRunName = testcase.getTestRunName();
		String testCaseNameUnique = testcase.getNameUnique();
		return ("../" + idSuite + "/" + testRunName + "/" + testCaseNameUnique + "/" + fileName);
	}	

	private void pintaValidacionesStep(TestCaseBean testCase, StepTM step1, Optional<StepTM> step2Opt) {

		var listChecksResult = step1.getListChecksTM();
		for (var checksResult : listChecksResult) {
			String descriptValid = checksResult.getHtmlValidationsBrSeparated();
			reportHtml+= 
				"<tr class=\"validation collapsed\"" + " met=\"" + testCase.getIndexInTestRun() + "\">" +
				"    <td style=\"display:none;\"></td>\n" + 
				"    <td class=\"nowrap\">Validation " + checksResult.getPositionInStep() + "</td>" +
				"    <td></td>" + 
				"    <td><div class=\"result" + checksResult.getStateValidation() + "\">" + checksResult.getStateValidation() + "</div></td>" + 
				"    <td></td>" +
				"    <td></td>";			
			
			pintaValidationComparation(step2Opt, checksResult);
			
			reportHtml+=
				"    <td>" + descriptValid + "</td>" + 
				"    <td></td>" + 
				"    <td></td>" + 
				"    <td></td>" +                        
				"    <td>" + checksResult.getNameClass() + " / " + checksResult.getNameMethod() + "</td>" + 
				"</tr>\n";
		}
	}

	private void pintaValidationComparation(Optional<StepTM> step2Opt, ChecksTM check1) {
		
		Optional<ChecksTM> check2Opt = Optional.empty();
		if (!isCompare()) {
			return;
		}
		
		if (step2Opt.isPresent()) {
			var step2 = step2Opt.get();
			check2Opt = getSameValidation(step2, check1);
			if (check2Opt.isPresent()) {
				var check2 = check2Opt.get();
				reportHtml+=
					"    <td class=\"compare\"><div class=\"result" + check2.getStateValidation() + "\">" + check2.getStateValidation() + "</div></td>";
			}
		}
		
		if (check2Opt.isEmpty()) {
			reportHtml+="    <td class=\"compare\"></td>";
		}
		
		reportHtml+=
			"    <td class=\"compare\"></td>" +
			"    <td class=\"compare\"></td>" +
			"    <td class=\"compare\"></td>";
	}
	
	private Optional<ChecksTM> getSameValidation(StepTM step2, ChecksTM check1) {
		for (var check2 : step2.getListChecksTM()) {
			if (check2.getPositionInStep()==check1.getPositionInStep() &&
				check2.getTextValidationsBrSeparated().equals(check1.getTextValidationsBrSeparated())) {
				return Optional.of(check2);
			}
		}
		
		for (var check2 : step2.getListChecksTM()) {
			if (check2.getTextValidationsBrSeparated().equals(check1.getTextValidationsBrSeparated())) {
				return Optional.of(check2);
			}
		}

		for (var check2 : step2.getListChecksTM()) {
			if (check2.getPositionInStep()==check1.getPositionInStep()) {
				return Optional.of(check2);
			}
		}
		
		return Optional.empty();
	}

	public void pintaCierreHTML() {
		reportHtml+=
			"</tbody>" + 
			"</table><br />\n" +	
			"</body>\n" +
			"</html>\n";
	}

    public void createFileReportHTML() throws Exception {
        String fileReport = getPathReportHtml();
        try (Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileReport), "UTF8"))) {
            out.write(reportHtml.toString());
        } 
    }
    
    private String getPathReportHtml() {
    	if (!isCompare()) {
    		return suite1.getPathReportHtml();
    	}
    	return suite1.getPathComparativeReport(suite2.getIdExecSuite());
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
	
	private String toSeconds(float millis) {
	    float seconds = Math.round(millis / 100.0f) / 10.0f;
	    return seconds + "s";
	}
	
	private boolean isCompare() {
		return suite2!=null;
	}
	
}
