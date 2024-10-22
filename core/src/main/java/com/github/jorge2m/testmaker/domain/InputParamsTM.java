package com.github.jorge2m.testmaker.domain;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.ws.rs.FormParam;

import org.apache.commons.cli.CommandLine;

import com.github.jorge2m.testmaker.boundary.access.OptionTMaker;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.ConstantesTM;
import com.github.jorge2m.testmaker.domain.RepositoryI.StoreUntil;
import com.github.jorge2m.testmaker.domain.testfilter.DataFilterTCases;
import com.github.jorge2m.testmaker.service.webdriver.maker.FactoryWebdriverMaker.EmbeddedDriver;

public abstract class InputParamsTM implements Serializable {

	private static final long serialVersionUID = 1L;

	public abstract List<OptionTMaker> getSpecificParameters();
	public abstract void setSpecificDataFromCommandLine(CommandLine cmdLine);
	public abstract Map<String,String> getSpecificParamsValues();
	
	public static final String SUITE_NAME_PARAM = "suite";
	public static final String DRIVER_NAME_PARAM = "driver";
	public static final String CHANNEL_NAME_PARAM = "channel";
	public static final String APP_NAME_PARAM = "application";
	public static final String VERSION_NAME_PARAM = "version";
	public static final String URL_NAME_PARAM = "url";
	public static final String EXEC_INIT_URL_NAME_PARAM = "execiniturl";
	public static final String TCASE_NAME_PARAM = "tcases";
	public static final String TCASE_EXCLUDED_NAME_PARAM = "tcasesexcluded";
	public static final String GROUPS_NAME_PARAM = "groups";
	public static final String RETRY_PARAM = "retry";
	public static final String RETRIED_PARAM = "retried";
	public static final String THREADS_PARAM = "threads";
	public static final String THREADS_RAMP_PARAM = "threadsramp";
	public static final String SERVER_DNS_NAME_PARAM = "serverDNS";
	public static final String ASYNC_EXEC_PARAM = "asyncexec";
	public static final String REMOTE_PARAM = "remote";
	public static final String RECICLE_WD_PARAM = "reciclewd";
	public static final String NET_ANALYSIS_PARAM = "net";
	public static final String RECORD_PARAM = "record";
	
	public static final String NOTIFICATION_PARAM = "notification";
	public static final String ALARM_PARAM = "alarm";
	public static final String ALARMS_TO_CHECK_PARAM = "alarmstocheck";	
	public static final String MAX_ALARMS_PARAM = "maxalarms";
	public static final String PERIOD_ALARMS_PARAM = "periodalarms";
	public static final String TEAMS_CHANNEL_PARAM = "teamschannel";
	
	public static final String STORE_BD_PARAM = "storebd";
	public static final String MAILS_PARAM = "mails";
	public static final String MAIL_USER_PARAM = "mailuser";
	public static final String MAIL_PASSWORD_PARAM = "mailpassword";
	public static final String TYPE_ACCESS_PARAM = "typeAccess";
	public static final String TEST_OBJECT_PARAM = "testobject";
	public static final String ID_EXEC_SUITE_PARAM = "idexecsuite";
	public static final String ID_EXEC_SUITE_PARENT_PARAM = "idexecsuiteparent";
	public static final String TESTCASE_NAME_PARENT_PARAM = "testcasenameparent";
	
	public static final String DYNATRACESD_PARAM = "dynatracesd";
	
	//BrowserStack
	public static final String BSTACK_USER_PARAM = "bstack_user"; //Mobil & Desktop
	public static final String BSTACK_PASSWORD_PARAM = "bstack_password"; //Mobil & Desktop
	public static final String BSTACK_OS_PARAM = "bstack_os"; //Mobil & Desktop
	public static final String BSTACK_OS_VERSION_PARAM = "bstack_os_version"; //Mobil & Desktop
	public static final String BSTACK_DEVICE_PARAM = "bstack_device"; //Mobil
	public static final String BSTACK_REAL_MOBILE_PARAM = "bstack_realMobile"; //Mobil 
	public static final String BSTACK_BROWSER_PARAM = "bstack_browser"; //Mobil & Desktop
	public static final String BSTACK_BROWSER_VERSION_PARAM = "bstack_browser_version"; //Desktop
	public static final String BSTACK_RESOLUTION_PARAM = "bstack_resolution"; //Desktop
	
	public static final String PATTERN_TESTCASE_ITEM = "([^\\{\\}]+)(?:\\{(\\d+)(?:-(\\d+))?\\})?";
	public static final String PATTERN_RETRY_PARAM = "(\\d)|(?:\\{(\\d+)(?:-(\\d+))?\\})?";
	
	public enum ManagementWebdriver { RECYCLE, DISCARD }
	private Class<? extends Enum<?>> suiteEnum;
	private Class<? extends Enum<?>> appEnum;
	
	public enum TypeAccess {Rest, CmdLine, Bat}

	@FormParam(SUITE_NAME_PARAM)
	String suite;

	@FormParam(GROUPS_NAME_PARAM)
	String groupsCommaSeparated;

	@FormParam(DRIVER_NAME_PARAM)
	String driver;

	@FormParam(CHANNEL_NAME_PARAM)
	String channel;

	@FormParam(APP_NAME_PARAM)
	String application;

	@FormParam(VERSION_NAME_PARAM)
	String version;

	@FormParam(URL_NAME_PARAM)
	String url;
	
	@FormParam(EXEC_INIT_URL_NAME_PARAM)
	String execiniturl;

	@FormParam(TCASE_NAME_PARAM)
	String tcasesCommaSeparated;
	
	@FormParam(TCASE_EXCLUDED_NAME_PARAM)
	String tcasesexcludedCommaSeparated;
	
	@FormParam(RETRY_PARAM)
	String retry;	
	
	@FormParam(RETRIED_PARAM)
	String retried;
	
	@FormParam(THREADS_PARAM)
	String threads;
	
	@FormParam(THREADS_RAMP_PARAM)
	String threadsramp;

	@FormParam(SERVER_DNS_NAME_PARAM)
	String serverDNS;

	@FormParam(RECICLE_WD_PARAM)
	String reciclewd;
	
	@FormParam(ASYNC_EXEC_PARAM)
	String asyncexec;
	
	@FormParam(REMOTE_PARAM)
	String remote;

	@FormParam(NET_ANALYSIS_PARAM)
	String net;
	
	@FormParam(RECORD_PARAM)
	String record;	
	
	@FormParam(NOTIFICATION_PARAM)
	String notification;	
	
	@FormParam(ALARM_PARAM)
	String alarm;
	
	@FormParam(ALARMS_TO_CHECK_PARAM)
	String alarmsToCheck;
	
	@FormParam(MAX_ALARMS_PARAM)
	String maxalarms;
	
	@FormParam(PERIOD_ALARMS_PARAM)
	String periodalarms;	
	
	@FormParam(TEAMS_CHANNEL_PARAM)
	String teamschannel;
	
	@FormParam(STORE_BD_PARAM)
	String storebd;

	@FormParam(TYPE_ACCESS_PARAM)
	String typeAccess = TypeAccess.CmdLine.name();
	
	@FormParam(ID_EXEC_SUITE_PARAM)
	String idExecSuite;
	
	@FormParam(ID_EXEC_SUITE_PARENT_PARAM)
	String idExecSuiteParent;
	
	@FormParam(TESTCASE_NAME_PARENT_PARAM)
	String testcasenameparent;

	@FormParam(TEST_OBJECT_PARAM)
	String testObject;
	
	@FormParam(DYNATRACESD_PARAM)
	String dynatracesd;
	
	//Browser Stack
	@FormParam(BSTACK_USER_PARAM)
	String bStackUser;
	
	@FormParam(BSTACK_PASSWORD_PARAM)
	String bStackPassword;
	
	@FormParam(BSTACK_OS_PARAM)
	String bStackOs;
	
	@FormParam(BSTACK_OS_VERSION_PARAM)
	String bStackOsVersion;
	
	@FormParam(BSTACK_DEVICE_PARAM)
	String bStackDevice;
	
	@FormParam(BSTACK_REAL_MOBILE_PARAM)
	String bStackRealMobile;
	
	@FormParam(BSTACK_BROWSER_PARAM)
	String bStackBrowser;
	
	@FormParam(BSTACK_BROWSER_VERSION_PARAM)
	String bStackBrowserVersion;
	
	@FormParam(BSTACK_RESOLUTION_PARAM)
	String bStackResolution;

	protected InputParamsTM() {}

	protected InputParamsTM(Class<? extends Enum<?>> suiteEnum, Class<? extends Enum<?>> appEnum) {
		this.suiteEnum = suiteEnum;
		this.appEnum = appEnum;
	}
	
	public List<OptionTMaker> getListAllOptions() {
		List<OptionTMaker> listAllOptions = new ArrayList<>();
		listAllOptions.addAll(getTmParameters());
		List<OptionTMaker> listSpecificParams = getSpecificParameters();
		if (listSpecificParams!=null) {
			listAllOptions.addAll(listSpecificParams);
		}
		return listAllOptions; 
	}
	
	public void setAllDataFromCommandLine(CommandLine cmdLine) {
		setTmDataFromCommandLine(cmdLine);
		setSpecificDataFromCommandLine(cmdLine);
	}
	
	public Map<String, String> getAllParamsValues() {
		var paramsValuesTM = getTmParamsValues();
		paramsValuesTM.putAll(getSpecificParamsValues());
		return paramsValuesTM;
	}
	
	private List<OptionTMaker> getTmParameters() {
		List<OptionTMaker> optionsTM = new ArrayList<>();
		optionsTM.add(OptionTMaker.builder(SUITE_NAME_PARAM)
			.required(true)
			.hasArg()
			.possibleValues(suiteEnum)
			.desc("Test Suite to execute. Possible values: " + Arrays.asList(suiteEnum.getEnumConstants()))
			.build());

		optionsTM.add(OptionTMaker.builder(DRIVER_NAME_PARAM)
			.required(true)
			.hasArg()
			//.possibleValues(EmbebdedDriver.class)
			.desc("WebDriver to launch the Suite of Tests. Possible values: " + Arrays.asList(EmbeddedDriver.values()))
			.build());

		optionsTM.add(OptionTMaker.builder(CHANNEL_NAME_PARAM)
			.required(true)
			.hasArg()
			.possibleValues(Channel.class)
			.desc("Channel on which to run the webdriver. Possible values: " + Arrays.toString(Channel.values()))
			.build());

		optionsTM.add(OptionTMaker.builder(APP_NAME_PARAM)
			.required(true)
			.hasArg()
			.possibleValues(appEnum)
			.desc("Application Web to test. Possible values: " + Arrays.toString(getNames(appEnum.getEnumConstants())))
			.build());

		optionsTM.add(OptionTMaker.builder(URL_NAME_PARAM)
			.required(false)
			.hasArg()
			.pattern(ConstantesTM.URL_PATTERN)
			.desc("Initial URL of the application Web to test")
			.build());
		
		optionsTM.add(OptionTMaker.builder(URL_NAME_PARAM)
			.required(false)
			.hasArg()
			.pattern(ConstantesTM.URL_PATTERN)
			.desc("Initial URL of the application Web to test")
			.build());		
		
		optionsTM.add(OptionTMaker.builder(InputParamsTM.TCASE_NAME_PARAM)
			.required(false)
			.hasArgs()
			.valueSeparator(',')
			.pattern(PATTERN_TESTCASE_ITEM)
			.desc("List of testcases comma separated (p.e. OTR001,BOR001,FIC001{6-2})")
			.build());

		optionsTM.add(OptionTMaker.builder(InputParamsTM.TCASE_EXCLUDED_NAME_PARAM)
			.required(false)
			.hasArgs()
			.valueSeparator(',')
			.desc("List of testcases excluded comma separated (p.e. OTR001,BOR001,FIC001)")
			.build());		

		optionsTM.add(OptionTMaker.builder(EXEC_INIT_URL_NAME_PARAM)
			.required(false)
			.hasArgs()
			.possibleValues(Arrays.asList("true", "false"))
			.desc("Execution of initial URL")
			.build());
		
		optionsTM.add(OptionTMaker.builder(GROUPS_NAME_PARAM)
			.required(false)
			.hasArg()
			.valueSeparator(',')
			.desc("Groups of tests to include")
			.build());
		
		optionsTM.add(OptionTMaker.builder(RETRY_PARAM)
			.required(false)
			.hasArgs()
			.pattern(PATTERN_RETRY_PARAM)
			.desc("Number or retrys of a TestCase with problems")
			.build());		
		
		optionsTM.add(OptionTMaker.builder(RETRIED_PARAM)
			.required(false)
			.hasArgs()
			.possibleValues(Arrays.asList("true", "false"))
			.desc("Indicates if the testcase is retried")
			.build());		
		
		optionsTM.add(OptionTMaker.builder(THREADS_PARAM)
			.required(false)
			.hasArgs()
			.pattern("[0-9]+")
			.desc("Number or threads for paralelize TestCases")
			.build());
		
		optionsTM.add(OptionTMaker.builder(THREADS_RAMP_PARAM)
			.required(false)
			.hasArgs()
			.pattern("[0-9]+")
			.desc("Ramp in seconds for reach maximum thread paralelization")
			.build());		

		optionsTM.add(OptionTMaker.builder(VERSION_NAME_PARAM)
			.required(false)
			.hasArg()
			.desc("Version of the TestSuite")
			.build());

		optionsTM.add(OptionTMaker.builder(SERVER_DNS_NAME_PARAM)
			.required(false)
			.hasArgs()
			.desc("Server DNS where are ubicated the HTML reports (p.e. http://robottest.mangodev.net)")
			.build());

		optionsTM.add(OptionTMaker.builder(RECICLE_WD_PARAM)
			.required(false)
			.hasArgs()
			.possibleValues(Arrays.asList("true", "false"))
			.desc("Gestion mode of webdriver. Possible values: true->reuse across testcases, false->don't reuse)")
			.build());
		
		optionsTM.add(OptionTMaker.builder(ASYNC_EXEC_PARAM)
			.required(false)
			.hasArgs()
			.possibleValues(Arrays.asList("true", "false"))
			.desc("Execution Asynchronous (true, false)")
			.build());
		
		optionsTM.add(OptionTMaker.builder(REMOTE_PARAM)
			.required(false)
			.hasArgs()
			.possibleValues(Arrays.asList("true", "false"))
			.desc("Remote Execution (true, false)")
			.build());

		optionsTM.add(OptionTMaker.builder(NET_ANALYSIS_PARAM)
			.required(false)
			.hasArgs()
			.possibleValues(Arrays.asList("true", "false"))
			.desc("Net Analysis (true, false)")
			.build());

		optionsTM.add(OptionTMaker.builder(RECORD_PARAM)
			.required(false)
			.hasArgs()
			.possibleValues(TypeRecord.getAllParamValues())
			.desc("Record Video (" + TypeRecord.getAllParamValues() + ")")
			.build());		

		optionsTM.add(OptionTMaker.builder(NOTIFICATION_PARAM)
			.required(false)
			.hasArgs()
			.possibleValues(Arrays.asList("true", "false"))
			.desc("Teams Notification Suite with problems (true, false, whenretry, whendefect)")
			.build());		
		
		optionsTM.add(OptionTMaker.builder(ALARM_PARAM)
			.required(false)
			.hasArgs()
			.possibleValues(Arrays.asList("true", "false"))
			.desc("Teams Notification check Alarm (true, false)")
			.build());		

		optionsTM.add(OptionTMaker.builder(ALARMS_TO_CHECK_PARAM)
			.required(false)
			.valueSeparator(',')
			.hasArgs()
			.desc("List of code alarms to check")
			.build());		
		
		optionsTM.add(OptionTMaker.builder(MAX_ALARMS_PARAM)
			.required(false)
			.hasArgs()
			.pattern("[0-9]+")
			.desc("Max alarms in a period. By default 60 minutes (configurable via maxalarms param)")
			.build());		
		
		optionsTM.add(OptionTMaker.builder(PERIOD_ALARMS_PARAM)
			.required(false)
			.hasArgs()
			.pattern("[0-9]+")
			.desc("Configure the period in minutes param maxalarms refers to")
			.build());		
		
		optionsTM.add(OptionTMaker.builder(TEAMS_CHANNEL_PARAM)
			.required(false)
			.hasArgs()
			.desc("URL of the Teams Webhook")
			.build());		
		
		optionsTM.add(OptionTMaker.builder(STORE_BD_PARAM)
			.required(false)
			.hasArgs()
			.possibleValues(StoreUntil.class)
			.desc(
				"Store results in bd until item element. Possible values: " + 
				Arrays.toString(getNames(StoreUntil.class.getEnumConstants())))
			.build());

		optionsTM.add(OptionTMaker.builder(MAILS_PARAM)
			.required(false)
			.hasArgs()
			.valueSeparator(',')
			.pattern("^(.+)@(.+)$")
			.desc("List of mail adresses comma separated")
			.build());

		optionsTM.add(OptionTMaker.builder(TYPE_ACCESS_PARAM)
			.required(false)
			.hasArgs()
			.possibleValues(TypeAccess.class)
			.desc("Type of access. Posible values: " + Arrays.asList(TypeAccess.values()))
			.build());
		
		optionsTM.add(OptionTMaker.builder(ID_EXEC_SUITE_PARAM)
			.required(false)
			.hasArgs()
			.desc("Id of SuiteTest Execution. For the case where you want to specify your own instead of the automatically generated one")
			.build());		
		
		optionsTM.add(OptionTMaker.builder(ID_EXEC_SUITE_PARENT_PARAM)
			.required(false)
			.hasArgs()
			.desc("Id of SuiteTest Execution. For the case where you want to specify your own instead of the automatically generated one")
			.build());		
		
		optionsTM.add(OptionTMaker.builder(TESTCASE_NAME_PARENT_PARAM)
			.required(false)
			.hasArgs()
			.desc("Testcase name parent for the remote execution")
			.build());		
		
		optionsTM.add(OptionTMaker.builder(DYNATRACESD_PARAM)
			.required(false)
			.hasArgs()
			.desc("Dynatrace subdomain")
			.build());		
		
		//BrowserStack
		optionsTM.add(OptionTMaker.builder(BSTACK_USER_PARAM)
			.required(false)
			.hasArgs()
			.desc("User of the BrowserStack platform")
			.build());
		
		optionsTM.add(OptionTMaker.builder(BSTACK_PASSWORD_PARAM)
			.required(false)
			.hasArgs()
			.desc("Password of the BrowserStack platform")
			.build());
		
		optionsTM.add(OptionTMaker.builder(BSTACK_OS_PARAM)
			.required(false)
			.hasArgs()
			.possibleValues(Arrays.asList("android", "iPhone", "Windows", "OS X"))
			.desc("os of the BrowserStack Test Environment")
			.build());
		
		optionsTM.add(OptionTMaker.builder(BSTACK_OS_VERSION_PARAM)
			.required(false)
			.hasArgs()
			.desc("os Version of the BrowserStack Test Environment")
			.build());
		
		optionsTM.add(OptionTMaker.builder(BSTACK_DEVICE_PARAM)
			.required(false)
			.hasArgs()
			.desc("Devide of the BrowserStack Test Environment")
			.build());
		
		optionsTM.add(OptionTMaker.builder(BSTACK_REAL_MOBILE_PARAM)
			.required(false)
			.hasArgs()
			.possibleValues(Arrays.asList("true", "false"))
			.desc("Indicator if a real movil-device is available in the BrowserStack Test Environment")
			.build());
		
		optionsTM.add(OptionTMaker.builder(BSTACK_BROWSER_PARAM)
			.required(false)
			.hasArgs()
			.desc("Browser of the BrowserStack Test Environment")
			.build());
		
		optionsTM.add(OptionTMaker.builder(BSTACK_BROWSER_VERSION_PARAM)
			.required(false)
			.hasArgs()
			.desc("Browser version of the BrowserStack Test Environment (only desktop)")
			.build());
		
		optionsTM.add(OptionTMaker.builder(BSTACK_RESOLUTION_PARAM)
			.required(false)
			.hasArgs()
			.pattern("\\d+x\\d+")
			.desc("Screen Resolution of the BrowserStack Test Environment, for example 1024x768 (only desktop)")
			.build());

		return optionsTM;
	}

	private void setTmDataFromCommandLine(CommandLine cmdLine) {
		channel = cmdLine.getOptionValue(CHANNEL_NAME_PARAM);
		suite = cmdLine.getOptionValue(SUITE_NAME_PARAM);
		application = cmdLine.getOptionValue(APP_NAME_PARAM);
		version = cmdLine.getOptionValue(VERSION_NAME_PARAM);
		url = cmdLine.getOptionValue(URL_NAME_PARAM);
		execiniturl = cmdLine.getOptionValue(EXEC_INIT_URL_NAME_PARAM);
		driver = cmdLine.getOptionValue(DRIVER_NAME_PARAM);
		serverDNS = cmdLine.getOptionValue(SERVER_DNS_NAME_PARAM);
		
		String[] groups = cmdLine.getOptionValues(GROUPS_NAME_PARAM);
		if (groups!=null) {
			groupsCommaSeparated = String.join(",", groups);
		}
		String[] tcases = cmdLine.getOptionValues(TCASE_NAME_PARAM);
		if (tcases!=null) {
			tcasesCommaSeparated = String.join(",", tcases);
		}
		String[] tcasesexcluded = cmdLine.getOptionValues(TCASE_EXCLUDED_NAME_PARAM);
		if (tcasesexcluded!=null) {
			tcasesexcludedCommaSeparated = String.join(",", tcasesexcluded);
		}

		retry = cmdLine.getOptionValue(RETRY_PARAM);
		retried = cmdLine.getOptionValue(RETRIED_PARAM);
		threads = cmdLine.getOptionValue(THREADS_PARAM);
		threadsramp = cmdLine.getOptionValue(THREADS_RAMP_PARAM);
		reciclewd = cmdLine.getOptionValue(RECICLE_WD_PARAM);
		asyncexec = cmdLine.getOptionValue(ASYNC_EXEC_PARAM);
		remote = cmdLine.getOptionValue(REMOTE_PARAM);
		net = cmdLine.getOptionValue(NET_ANALYSIS_PARAM);
		record = cmdLine.getOptionValue(RECORD_PARAM);
		
		notification = cmdLine.getOptionValue(NOTIFICATION_PARAM);
		alarm = cmdLine.getOptionValue(ALARM_PARAM);
		alarmsToCheck = cmdLine.getOptionValue(ALARMS_TO_CHECK_PARAM);
		maxalarms = cmdLine.getOptionValue(MAX_ALARMS_PARAM);
		periodalarms = cmdLine.getOptionValue(PERIOD_ALARMS_PARAM);
		teamschannel = cmdLine.getOptionValue(TEAMS_CHANNEL_PARAM);
		
		storebd = cmdLine.getOptionValue(STORE_BD_PARAM);
		typeAccess = cmdLine.getOptionValue(TYPE_ACCESS_PARAM);
		idExecSuite = cmdLine.getOptionValue(ID_EXEC_SUITE_PARAM);
		idExecSuiteParent = cmdLine.getOptionValue(ID_EXEC_SUITE_PARENT_PARAM);
		testcasenameparent = cmdLine.getOptionValue(TESTCASE_NAME_PARENT_PARAM);
		
		dynatracesd = cmdLine.getOptionValue(DYNATRACESD_PARAM);
		
		//BrowserStack
		bStackUser = cmdLine.getOptionValue(BSTACK_USER_PARAM);
		bStackPassword = cmdLine.getOptionValue(BSTACK_PASSWORD_PARAM);
		bStackOs = cmdLine.getOptionValue(BSTACK_OS_PARAM);
		bStackOsVersion = cmdLine.getOptionValue(BSTACK_OS_VERSION_PARAM);
		bStackDevice = cmdLine.getOptionValue(BSTACK_DEVICE_PARAM);
		bStackRealMobile = cmdLine.getOptionValue(BSTACK_REAL_MOBILE_PARAM);
		bStackBrowser = cmdLine.getOptionValue(BSTACK_BROWSER_PARAM);
		bStackBrowserVersion = cmdLine.getOptionValue(BSTACK_BROWSER_VERSION_PARAM);
		bStackResolution = cmdLine.getOptionValue(BSTACK_RESOLUTION_PARAM);
	}

	private enum ParamTM {
		SUITE(SUITE_NAME_PARAM),
		GROUPS(GROUPS_NAME_PARAM),
		DRIVER(DRIVER_NAME_PARAM),
		CHANNEL(CHANNEL_NAME_PARAM),
		APPLICATION(APP_NAME_PARAM),
		VERSION(VERSION_NAME_PARAM),
		URL(URL_NAME_PARAM),
		EXEC_INIT_URL(EXEC_INIT_URL_NAME_PARAM),
		TCASES(TCASE_NAME_PARAM),
		TCASES_EXCLUDED(TCASE_EXCLUDED_NAME_PARAM),
		RETRY(RETRY_PARAM),
		RETRIED(RETRIED_PARAM),
		THREADS(THREADS_PARAM),
		THREADS_RAMP(THREADS_RAMP_PARAM),
		SERVER_DNS(SERVER_DNS_NAME_PARAM),
		RECICLE_WD(RECICLE_WD_PARAM),
		ASYNC_EXEC(ASYNC_EXEC_PARAM),
		REMOTE(REMOTE_PARAM),
		NET_ANALYSIS(NET_ANALYSIS_PARAM),
		RECORD(RECORD_PARAM),
		NOTIFICATION(NOTIFICATION_PARAM),
		ALARM(ALARM_PARAM),
		ALARMS_TO_CHECK(ALARMS_TO_CHECK_PARAM),
		MAX_ALARMS(MAX_ALARMS_PARAM),
		PERIOD_ALARMS(PERIOD_ALARMS_PARAM),		
		TEAMS_CHANNEL(TEAMS_CHANNEL_PARAM),
		STORE_BD(STORE_BD_PARAM),
		MAILS(MAILS_PARAM),
		MAIL_USER(MAIL_USER_PARAM),
		MAIL_PASSWORD(MAIL_PASSWORD_PARAM),
		TYPE_ACCESS(TYPE_ACCESS_PARAM),
		ID_EXEC_SUITE(ID_EXEC_SUITE_PARAM),
		ID_EXEC_SUITE_PARENT(ID_EXEC_SUITE_PARENT_PARAM),
		TESTCASE_NAME_PARENT(TESTCASE_NAME_PARENT_PARAM),
		TEST_OBJECT(TEST_OBJECT_PARAM),
		DYNATRACESD(DYNATRACESD_PARAM),
		BSTACK_USER(BSTACK_USER_PARAM),
		BSTACK_PASSWORD(BSTACK_PASSWORD_PARAM),
		BSTACK_OS(BSTACK_OS_PARAM),
		BSTACK_OS_VERSION(BSTACK_OS_VERSION_PARAM),
		BSTACK_DEVICE(BSTACK_DEVICE_PARAM),
		BSTACK_REAL_MOBILE(BSTACK_REAL_MOBILE_PARAM),
		BSTACK_BROWSER(BSTACK_BROWSER_PARAM),
		BSTACK_BROWSER_VERSION(BSTACK_BROWSER_VERSION_PARAM),
		BSTACK_RESOLUTION(BSTACK_RESOLUTION_PARAM);
		
		String nameParam;
		private ParamTM(String nameParam) {
			this.nameParam = nameParam;
		}
	}
	
	private Map<String,String> getTmParamsValues() {
		Map<String,String> pairsParamValue = new HashMap<>(); 
		for (ParamTM paramTM : ParamTM.values()) {
			String valueParam = getValueParam(paramTM);
			if (valueParam!=null && "".compareTo(valueParam)!=0) {
				pairsParamValue.put(paramTM.nameParam, valueParam);
			}
		}
		return pairsParamValue;
	}
	
	private String getValueParam(ParamTM paramId) {
		switch (paramId) {
		case SUITE:
			return this.suite;
		case GROUPS:
			return this.groupsCommaSeparated;
		case DRIVER:
			return this.driver;
		case CHANNEL:
			return this.channel;
		case APPLICATION:
			return this.application;
		case VERSION:
			return this.version;
		case URL:
			return this.url;
		case EXEC_INIT_URL:
			return this.execiniturl;
		case TCASES:
			return this.tcasesCommaSeparated;
		case TCASES_EXCLUDED:
			return this.tcasesexcludedCommaSeparated;			
		case RETRY:
			return this.retry;
		case RETRIED:
			return this.retried;
		case THREADS:
			return this.threads;
		case THREADS_RAMP:
			return this.threadsramp;
		case SERVER_DNS:
			return this.serverDNS;
		case RECICLE_WD:
			return this.reciclewd;
		case ASYNC_EXEC:
			return this.asyncexec;
		case REMOTE:
			return this.remote;
		case NET_ANALYSIS:
			return this.net;
		case RECORD:
			return this.record;			
		case NOTIFICATION:
			return this.notification;
		case ALARM:
			return this.alarm;
		case ALARMS_TO_CHECK:
			return this.alarmsToCheck;
		case MAX_ALARMS:
			return this.maxalarms;
		case PERIOD_ALARMS:
			return this.periodalarms;
		case TEAMS_CHANNEL:
			return this.teamschannel;
		case STORE_BD:
			return this.storebd;
		case TYPE_ACCESS:
			return this.typeAccess;
		case ID_EXEC_SUITE:
			return this.idExecSuite;
		case ID_EXEC_SUITE_PARENT:
			return this.idExecSuiteParent;
		case TESTCASE_NAME_PARENT:
			return this.testcasenameparent;
		case DYNATRACESD:
			return this.dynatracesd;			
		case BSTACK_USER:
			return bStackUser;
		case BSTACK_PASSWORD:
			return bStackPassword;
		case BSTACK_OS:
			return bStackOs;
		case BSTACK_OS_VERSION:
			return bStackOsVersion;
		case BSTACK_DEVICE:
			return bStackDevice;
		case BSTACK_REAL_MOBILE:
			return bStackRealMobile;
		case BSTACK_BROWSER:
			return bStackBrowser;
		case BSTACK_BROWSER_VERSION:
			return bStackBrowserVersion;
		case BSTACK_RESOLUTION:
			return bStackResolution;
		default:
			return "";
		}
	}

	public Class<? extends Enum<?>> getSuiteEnum() {
		return suiteEnum;
	}
	public void setSuiteEnum(Class<? extends Enum<?>> suiteEnum) {
		this.suiteEnum = suiteEnum;
	}
	public Class<? extends Enum<?>> getAppEnum() {
		return appEnum;
	}
	public void setAppEnum(Class<? extends Enum<?>> appEnum) {
		this.appEnum = appEnum;
	}
	public Channel getChannel() {
		return Channel.valueOf(channel);
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public void setChannel(Channel channel) {
    	this.channel = channel.name();
    }
    public Enum<?> getSuite() {
    	return (valueOf(suiteEnum.getEnumConstants(), suite));
    }
	public String getSuiteName() {
		return suite;
	}
	public void setSuite(String suite) {
		this.suite = suite;
	}
	public void setSuite(Enum<?> suite) {
		this.suite = suite.name();
	}
	public Enum<?> getApp() {
		return (valueOf(appEnum.getEnumConstants(), application));
	}
	public void setApp(String app) {
		this.application = app;
	}
	public void setApp(Enum<?> app) {
		this.application = app.name();
	}
	public String getVersion() {
		if (version==null) {
			version = "V1";
		}
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getUrlBase() {
		return url;
	}
	public void setUrlBase(String urlBase) {
		this.url = urlBase;
	}
	public String getExecInitUrl() {
		return this.execiniturl;
	}
	public void setExecInitUrl(String execiniturl) {
		this.execiniturl = execiniturl;
	}
	public boolean isExecInitUrl() {
		if (execiniturl==null) {
			return true;
		}
		return ("true".compareTo(execiniturl)==0);
	}
	public String getDnsUrlAcceso() throws URISyntaxException {
		URI uri = new URI(getUrlBase());
		return (uri.getScheme() + "://" + uri.getHost());
	}
	
	public EmbeddedDriver getDriverType() {
		return EmbeddedDriver.valueOf(driver);
	}
	
	public String getDriver() {
		return this.driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getWebAppDNS() {
		return serverDNS;
	}
	public void setWebAppDNS(String serverDNS) {
		this.serverDNS = serverDNS;
	}
	public List<String> getGroupsFilter() {
		if (groupsCommaSeparated!=null) {
			String[] groups = groupsCommaSeparated.split(",");
			return Arrays.asList(groups);
		}
		return Arrays.asList();
	}
	public List<String> getListTestCaseItems() {
		if (tcasesCommaSeparated!=null) {
			String[] tcases = tcasesCommaSeparated.split(",");
			return Arrays.asList(tcases);
		}
		return Arrays.asList();
	}
	public void setListTestCaseItems(List<String> listTestCases) {
		String[] tcases = listTestCases.toArray(new String[listTestCases.size()]);
		tcasesCommaSeparated = String.join(",", tcases);
	}
	
	public List<String> getListTestCasesIncludedName() {
        return getListTestCasesIncludedData().stream()
                .map(TestCaseParams::getName)
                .collect(Collectors.toList());
	}
	
    public List<TestCaseParams> getListTestCasesIncludedData() {
        return getListTestCaseItems().stream()
                .map(this::parseTestCaseItem)
                .collect(Collectors.toList());
    }
    
    private TestCaseParams parseTestCaseItem(String testCaseItem) {
        Pattern pattern = Pattern.compile(PATTERN_TESTCASE_ITEM);
        Matcher matcher = pattern.matcher(testCaseItem);
        TestCaseParams testCaseData = new TestCaseParams();
        if (matcher.find()) {
            testCaseData.setName(matcher.group(1));
            if (matcher.group(2) != null) {
                testCaseData.setInvocationCount(Integer.valueOf(matcher.group(2)));
                if (matcher.group(3) != null) {
                    testCaseData.setThreadPoolSize(Integer.valueOf(matcher.group(3)));
                }
            }
        } else {
            testCaseData.setName(testCaseItem);
        }
        return testCaseData;
    }    
	
	public TestCaseParams getTestCaseParams(String codeTest) {
		for (var testCaseData : getListTestCasesIncludedData()) {
			if (testCaseData.getName().compareTo(codeTest)==0) {
				return testCaseData;
			}
		}
		return null;
	}

	public List<String> getListTestCaseExcludedItems() {
		if (tcasesexcludedCommaSeparated!=null) {
			String[] tcases = tcasesexcludedCommaSeparated.split(",");
			return Arrays.asList(tcases);
		}
		return Arrays.asList();
	}
	public void setListTestCaseExcludedItems(List<String> listTestCasesExcluded) {
		String[] tcases = listTestCasesExcluded.toArray(new String[listTestCasesExcluded.size()]);
		tcasesexcludedCommaSeparated = String.join(",", tcases);
	}

	public String getRetry() {
		return retry;
	}
	public Optional<RetryParams> getRetryParams() {
		if (retry==null) {
			return Optional.empty();
		}
		
		var retryParams = new RetryParams();
		var pattern = Pattern.compile(PATTERN_RETRY_PARAM);
		var matcher = pattern.matcher(retry);
		if (!matcher.find()) {
			return Optional.empty();
		}
		if (matcher.group(1)!=null) {
			retryParams.setRetriesTestCase(Integer.valueOf(matcher.group(1))); 
		}
		if (matcher.group(2)!=null) {
			retryParams.setRetriesTestCase(Integer.valueOf(matcher.group(2)));
		}
		if (matcher.group(3)!=null) {
			retryParams.setRetriesMaxSuite(Integer.valueOf(matcher.group(3)));
		}
		
		return Optional.of(retryParams);
	}

	public boolean isRetried() {
		if (retried!=null) {
	    	return ("true".compareTo(retried)==0);
		}
		return false;
	}
	public void setRetried(String retried) {
		this.retried = retried;
	}
	
	public String getThreads() {
		return threads;
	}
	public Optional<Integer> getThreadsNum() {
		if (threads!=null) {
			return Optional.of(Integer.valueOf(threads));
		}
		return Optional.empty();
	}
	public void setThreads(String threads) {
		this.threads = threads;
	}
	public int getThreadsRampNum() {
		if (threadsramp!=null) {
			return Integer.valueOf(threadsramp);
		}
		return 0;
	}
	
	public String getThreadsRamp() {
		return threadsramp;
	}
	
	public boolean getRecicleWD() {
		if (reciclewd!=null) {
	    	return ("true".compareTo(reciclewd)==0);
		}
		return false;
	}
	public void setRecicleWD(String reciclewd) {
		this.reciclewd = reciclewd;
	}
	public ManagementWebdriver getTypeManageWebdriver() {
		if (getRecicleWD()) {
			return ManagementWebdriver.RECYCLE;
		}
		return ManagementWebdriver.DISCARD;
	}
	public void setAsyncExec(String asyncexec) {
		this.asyncexec = asyncexec;
	}
	public boolean isAsyncExec() {
		if (asyncexec!=null) {
			return ("true".compareTo(asyncexec)==0);
		}
		return true;
	} 
	public void setTestExecutingInRemote(String remote) {
		this.remote = remote;
	}
	public boolean isTestExecutingInRemote() {
		if (remote!=null) {
			return ("true".compareTo(remote)==0);
		}
		return false;
	} 
	public void setNetAnalysis(String net) {
		this.net = net;
	}
	public boolean isNetAnalysis() {
		if (net!=null) {
			return ("true".compareTo(net)==0);
		}
		return false;
	}
	
	public void setRecord(String record) {
		this.record = record;
	}
	public String getRecord() {
		return record;
	}	
	
	public void setNotification(String notification) {
		this.notification = notification;
	}
	public boolean isNotification() {
		if (notification!=null) {
			return ("true".compareTo(notification)==0);
		}
		return false;
	}	
	
	public void setAlarm(String alarm) {
		this.alarm = alarm;
	}
	public boolean isAlarm() {
		if (alarm!=null) {
			return ("true".compareTo(alarm)==0);
		}
		return false;
	}	
	public String getAlarmsToCheckCommaSeparated() {
		return this.alarmsToCheck;
	}
	public List<String> getAlarmsToCheck() {
		if (alarmsToCheck!=null && "".compareTo(alarmsToCheck)!=0) {
			String[] listAlarms = alarmsToCheck.split("\\s*,\\s*");
			return Arrays.asList(listAlarms);
		}
		return Arrays.asList();
	}
	public void setAlarmsToCheck(String alarmsToCheck) {
		this.alarmsToCheck = alarmsToCheck;
	}
	public String getMaxAlarms() {
		return this.maxalarms;
	}
	public void setMaxAlarms(String maxalarms) {
		this.maxalarms = maxalarms;
	}
	public String getPeriodAlarms() {
		return this.periodalarms;
	}
	public void setPeriodAlarms(String periodalarms) {
		this.periodalarms = periodalarms;
	}	
	public void setTeamsChannel(String teamschannel) {
		this.teamschannel = teamschannel;
	}
	public String getTeamsChannel() {
		return teamschannel;
	}	
	
	public void setStoreBd(StoreUntil storeUntil) {
		this.storebd = storeUntil.name();
	}
	public StoreUntil getStoreBd() {
		if (storebd==null || "".compareTo(storebd)==0) {
			if (getTypeAccess()==TypeAccess.Rest) {
				return StoreUntil.testcase;
			}
			return StoreUntil.nostore;
		}
		return StoreUntil.valueOf(storebd);
	}
	public String getMoreInfo() {
		return "";
	}
	
	private static Enum<?> valueOf(Enum<?>[] enumConstants, String value) throws IllegalArgumentException {
		for (Enum<?> enumCandidate : enumConstants) {
			if (enumCandidate.name().equals(value)) {
				return enumCandidate;
			}
		}
		throw new IllegalArgumentException();
	}

	public TypeAccess getTypeAccess() {
		if (typeAccess==null || "".compareTo(typeAccess)==0) {
			return TypeAccess.CmdLine;
		}
		return TypeAccess.valueOf(typeAccess);
	}
	public void setTypeAccess(TypeAccess typeAccess) {
		this.typeAccess = typeAccess.name();
	}
	
	public String getIdExecSuite() {
		return this.idExecSuite;
	}
	public void setIdExecSuite(String idExecSuite) {
		this.idExecSuite = idExecSuite;
	}
	
	public String getIdExecSuiteParent() {
		return this.idExecSuiteParent;
	}
	public void setIdExecSuiteParent(String idExecSuiteParent) {
		this.idExecSuiteParent = idExecSuiteParent;
	}	
	
	public String getTestCaseNameParent() {
		return this.testcasenameparent;
	}
	public void setTestCaseNameParent(String testcasenameparent) {
		this.testcasenameparent = testcasenameparent;
	}	
	
	public String getDynatracesd() {
		return this.dynatracesd;
	}
	public void setDynatracesd(String dynatracesd) {
		this.dynatracesd = dynatracesd;
	}	
	
	public String getTestObject() {
		return testObject;
	}
	public void setTestObject(String testObject) {
		this.testObject = testObject;
	}
	
	//BrowserStack
	public String getBStackUser() {
		return bStackUser;
	}
	public void setBStackUser(String bStackUser) {
		this.bStackUser = bStackUser;
	}
	public String getBStackPassword() {
		return bStackPassword;
	}
	public void setBStackPassword(String bStackPassword) {
		this.bStackPassword = bStackPassword;
	}
	public String getBStackOs() {
		return bStackOs;
	}
	public void setBStackOs(String bStackOs) {
		this.bStackOs = bStackOs;
	}
	public String getBStackOsVersion() {
		return bStackOsVersion;
	}
	public void setBStackOsVersion(String bStackOsVersion) {
		this.bStackOsVersion = bStackOsVersion;
	}
	public String getBStackDevice() {
		return bStackDevice;
	}
	public void setBStackDevice(String bStackDevice) {
		this.bStackDevice = bStackDevice;
	}
	public String getBStackRealMobile() {
		return bStackRealMobile;
	}
	public void setBStackRealMobile(String bStackRealMobile) {
		this.bStackRealMobile = bStackRealMobile;
	}
	public String getBStackBrowser() {
		return bStackBrowser;
	}
	public void setBStackBrowser(String bStackBrowser) {
		this.bStackBrowser = bStackBrowser;
	}
	public String getBStackBrowserVersion() {
		return bStackBrowserVersion;
	}
	public void setBStackBrowserVersion(String bStackBrowserVersion) {
		this.bStackBrowserVersion = bStackBrowserVersion;
	}
	public String getBStackResolution() {
		return bStackResolution;
	}
	public void setBStackResolution(String bStackResolution) {
		this.bStackResolution = bStackResolution;
	}
	
	public DataFilterTCases getDataFilter() {
		var dFilter = new DataFilterTCases(getChannel(), getApp());
		dFilter.setGroupsFilter(getGroupsFilter());
		dFilter.setTestCasesIncludedFilter(getListTestCasesIncludedName());
		dFilter.setTestCasesExcludedFilter(getListTestCaseExcludedItems());
		return dFilter;
	}

	private static String[] getNames(Enum<?>[] enumConstants) {
		return Arrays.stream(enumConstants).map(Enum::name).toArray(String[]::new);
	}
}
