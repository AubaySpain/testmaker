package com.github.jorge2m.testmaker.domain;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.FormParam;
import javax.ws.rs.PathParam;

import org.apache.commons.cli.CommandLine;

import com.github.jorge2m.testmaker.boundary.access.OptionTMaker;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.ConstantesTM;
import com.github.jorge2m.testmaker.domain.RepositoryI.StoreUntil;
import com.github.jorge2m.testmaker.domain.testfilter.DataFilterTCases;
import com.github.jorge2m.testmaker.domain.util.TestNameUtils;
import com.github.jorge2m.testmaker.service.webdriver.maker.FactoryWebdriverMaker.EmbeddedDriver;

public abstract class InputParamsTM {

	abstract public List<OptionTMaker> getSpecificParameters();
	abstract public void setSpecificDataFromCommandLine(CommandLine cmdLine);
	abstract public Map<String,String> getSpecificParamsValues();
	
	public static final String SuiteNameParam = "suite";
	public static final String DriverNameParam = "driver";
	public static final String ChannelNameParam = "channel";
	public static final String AppNameParam = "application";
	public static final String VersionNameParam = "version";
	public static final String URLNameParam = "url";
	public static final String TCaseNameParam = "tcases";
	public static final String GroupsNameParam = "groups";
	public static final String ThreadsParam = "threads";
	public static final String ServerDNSNameParam = "serverDNS";
	public static final String AsyncExecParam = "asyncexec";
	public static final String RemoteParam = "remote";
	public static final String RecicleWDParam = "reciclewd";
	public static final String NetAnalysisParam = "net";
	public static final String StoreBdParam = "storebd";
	public static final String MailsParam = "mails";
	public static final String TypeAccessParam = "typeAccess";
	public static final String DriverVersionParam = "driverVersion";
	public static final String TestObjectParam = "testobject";
	
	//BrowserStack
	public static final String BStackUserParam = "bstack_user"; //Mobil & Desktop
	public static final String BStackPasswordParam = "bstack_password"; //Mobil & Desktop
	public static final String BStackOsParam = "bstack_os"; //Mobil & Desktop
	public static final String BStackOsVersionParam = "bstack_os_version"; //Mobil & Desktop
	public static final String BStackDeviceParam = "bstack_device"; //Mobil
	public static final String BStackRealMobileParam = "bstack_realMobile"; //Mobil 
	public static final String BStackBrowserParam = "bstack_browser"; //Mobil & Desktop
	public static final String BStackBrowserVersionParam = "bstack_browser_version"; //Desktop
	public static final String BStackResolutionParam = "bstack_resolution"; //Desktop
	
	public static final String patternTestCaseItem = "([^\\{\\}]+)(?:\\{([0-9]+)(?:-([0-9]+)){0,1}\\}){0,1}";
	
	public enum ManagementWebdriver {recycle, discard}
	private Class<? extends Enum<?>> suiteEnum;
	private Class<? extends Enum<?>> appEnum;
	
	public enum TypeAccess {Rest, CmdLine, Bat}

	@FormParam(SuiteNameParam)
	String suite;

	@FormParam(GroupsNameParam)
	String groupsCommaSeparated;

	@FormParam(DriverNameParam)
	String driver;

	@FormParam(ChannelNameParam)
	String channel;

	@FormParam(AppNameParam)
	String application;

	@FormParam(VersionNameParam)
	String version;

	@FormParam(URLNameParam)
	String url;

	@FormParam(TCaseNameParam)
	String tcasesCommaSeparated;
	
	@FormParam(ThreadsParam)
	String threads;

	@FormParam(ServerDNSNameParam)
	String serverDNS;

	@FormParam(RecicleWDParam)
	String reciclewd;
	
	@FormParam(AsyncExecParam)
	String asyncexec;
	
	@FormParam(RemoteParam)
	String remote;

	@FormParam(NetAnalysisParam)
	String net;

	@FormParam(StoreBdParam)
	String storebd;

	@FormParam(MailsParam)
	String mailsCommaSeparated;
	
	@FormParam(TypeAccessParam)
	String typeAccess = TypeAccess.CmdLine.name();
	
	@FormParam(DriverVersionParam)
	String driverVersion;
	
	@FormParam(TestObjectParam)
	String testObject;
	
	//Browser Stack
	@FormParam(BStackUserParam)
	String bStackUser;
	
	@FormParam(BStackPasswordParam)
	String bStackPassword;
	
	@FormParam(BStackOsParam)
	String bStackOs;
	
	@FormParam(BStackOsVersionParam)
	String bStackOsVersion;
	
	@FormParam(BStackDeviceParam)
	String bStackDevice;
	
	@FormParam(BStackRealMobileParam)
	String bStackRealMobile;
	
	@FormParam(BStackBrowserParam)
	String bStackBrowser;
	
	@FormParam(BStackBrowserVersionParam)
	String bStackBrowserVersion;
	
	@FormParam(BStackResolutionParam)
	String bStackResolution;

	public InputParamsTM() {}

	public InputParamsTM(Class<? extends Enum<?>> suiteEnum, Class<? extends Enum<?>> appEnum) {
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
		Map<String, String> paramsValuesTM = getTmParamsValues();
		paramsValuesTM.putAll(getSpecificParamsValues());
		return paramsValuesTM;
	}
	
	private List<OptionTMaker> getTmParameters() {
		List<OptionTMaker> optionsTM = new ArrayList<>();
		optionsTM.add(OptionTMaker.builder(InputParamsTM.SuiteNameParam)
			.required(true)
			.hasArg()
			.possibleValues(suiteEnum)
			.desc("Test Suite to execute. Possible values: " + Arrays.asList(suiteEnum.getEnumConstants()))
			.build());

		optionsTM.add(OptionTMaker.builder(InputParamsTM.DriverNameParam)
			.required(true)
			.hasArg()
			//.possibleValues(EmbebdedDriver.class)
			.desc("WebDriver to launch the Suite of Tests. Possible values: " + Arrays.asList(EmbeddedDriver.values()))
			.build());

		optionsTM.add(OptionTMaker.builder(InputParamsTM.ChannelNameParam)
			.required(true)
			.hasArg()
			.possibleValues(Channel.class)
			.desc("Channel on which to run the webdriver. Possible values: " + Arrays.toString(Channel.values()))
			.build());

		optionsTM.add(OptionTMaker.builder(InputParamsTM.AppNameParam)
			.required(true)
			.hasArg()
			.possibleValues(appEnum)
			.desc("Application Web to test. Possible values: " + Arrays.toString(getNames(appEnum.getEnumConstants())))
			.build());

		optionsTM.add(OptionTMaker.builder(InputParamsTM.URLNameParam)
			.required(false)
			.hasArg()
			.pattern(ConstantesTM.URL_Pattern)
			.desc("Initial URL of the application Web to test")
			.build());

		optionsTM.add(OptionTMaker.builder(InputParamsTM.TCaseNameParam)
			.required(false)
			.hasArgs()
			.valueSeparator(',')
			//Examples: FIC001, FIC001{1}, FIC001{50-10}
			.pattern(patternTestCaseItem)
			.desc("List of testcases comma separated (p.e. OTR001,BOR001,FIC001{6-2})")
			.build());
		
		optionsTM.add(OptionTMaker.builder(InputParamsTM.GroupsNameParam)
			.required(false)
			.hasArg()
			.valueSeparator(',')
			.desc("Groups of tests to include")
			.build());
		
		optionsTM.add(OptionTMaker.builder(InputParamsTM.ThreadsParam)
			.required(false)
			.hasArgs()
			.pattern("[0-9]+")
			.desc("Number or threads for paralelize TestCases")
			.build());

		optionsTM.add(OptionTMaker.builder(InputParamsTM.VersionNameParam)
			.required(false)
			.hasArg()
			.desc("Version of the TestSuite")
			.build());

		optionsTM.add(OptionTMaker.builder(InputParamsTM.ServerDNSNameParam)
			.required(false)
			.hasArgs()
			.desc("Server DNS where are ubicated the HTML reports (p.e. http://robottest.mangodev.net)")
			.build());

		optionsTM.add(OptionTMaker.builder(InputParamsTM.RecicleWDParam)
			.required(false)
			.hasArgs()
			.possibleValues(Arrays.asList("true", "false"))
			.desc("Gestion mode of webdriver. Possible values: true->reuse across testcases, false->don't reuse)")
			.build());
		
		optionsTM.add(OptionTMaker.builder(InputParamsTM.AsyncExecParam)
			.required(false)
			.hasArgs()
			.possibleValues(Arrays.asList("true", "false"))
			.desc("Execution Asynchronous (true, false)")
			.build());
		
		optionsTM.add(OptionTMaker.builder(InputParamsTM.RemoteParam)
			.required(false)
			.hasArgs()
			.possibleValues(Arrays.asList("true", "false"))
			.desc("Remote Execution (true, false)")
			.build());

		optionsTM.add(OptionTMaker.builder(InputParamsTM.NetAnalysisParam)
			.required(false)
			.hasArgs()
			.possibleValues(Arrays.asList("true", "false"))
			.desc("Net Analysis (true, false)")
			.build());

		optionsTM.add(OptionTMaker.builder(InputParamsTM.StoreBdParam)
			.required(false)
			.hasArgs()
			.possibleValues(StoreUntil.class)
			.desc(
				"Store results in bd until item element. Possible values: " + 
				Arrays.toString(getNames(StoreUntil.class.getEnumConstants())))
			.build());

		optionsTM.add(OptionTMaker.builder(InputParamsTM.MailsParam)
			.required(false)
			.hasArgs()
			.valueSeparator(',')
			.pattern("^(.+)@(.+)$")
			.desc("List of mail adresses comma separated")
			.build());

		optionsTM.add(OptionTMaker.builder(InputParamsTM.TypeAccessParam)
			.required(false)
			.hasArgs()
			.possibleValues(TypeAccess.class)
			.desc("Type of access. Posible values: " + Arrays.asList(TypeAccess.values()))
			.build());
		
		optionsTM.add(OptionTMaker.builder(InputParamsTM.DriverVersionParam)
			.required(false)
			.hasArgs()
			.pattern("\\d+(\\.\\d+)*")
			.desc("Version of Driver (ChromeDriver, Geckodriver...) to use")
			.build());
		
		//BrowserStack
		optionsTM.add(OptionTMaker.builder(InputParamsTM.BStackUserParam)
			.required(false)
			.hasArgs()
			.desc("User of the BrowserStack platform")
			.build());
		
		optionsTM.add(OptionTMaker.builder(InputParamsTM.BStackPasswordParam)
			.required(false)
			.hasArgs()
			.desc("Password of the BrowserStack platform")
			.build());
		
		optionsTM.add(OptionTMaker.builder(InputParamsTM.BStackOsParam)
			.required(false)
			.hasArgs()
			.possibleValues(Arrays.asList("android"/*mobil*/, "iPhone"/*mobil*/, "Windows"/*desktop*/, "OS X"/*desktop*/))
			.desc("os of the BrowserStack Test Environment")
			.build());
		
		optionsTM.add(OptionTMaker.builder(InputParamsTM.BStackOsVersionParam)
			.required(false)
			.hasArgs()
			.desc("os Version of the BrowserStack Test Environment")
			.build());
		
		optionsTM.add(OptionTMaker.builder(InputParamsTM.BStackDeviceParam)
			.required(false)
			.hasArgs()
			.desc("Devide of the BrowserStack Test Environment")
			.build());
		
		optionsTM.add(OptionTMaker.builder(InputParamsTM.BStackRealMobileParam)
			.required(false)
			.hasArgs()
			.possibleValues(Arrays.asList("true", "false"))
			.desc("Indicator if a real movil-device is available in the BrowserStack Test Environment")
			.build());
		
		optionsTM.add(OptionTMaker.builder(InputParamsTM.BStackBrowserParam)
			.required(false)
			.hasArgs()
			.desc("Browser of the BrowserStack Test Environment")
			.build());
		
		optionsTM.add(OptionTMaker.builder(InputParamsTM.BStackBrowserVersionParam)
			.required(false)
			.hasArgs()
			.desc("Browser version of the BrowserStack Test Environment (only desktop)")
			.build());
		
		optionsTM.add(OptionTMaker.builder(InputParamsTM.BStackResolutionParam)
			.required(false)
			.hasArgs()
			.pattern("\\d+x\\d+")
			.desc("Screen Resolution of the BrowserStack Test Environment, for example 1024x768 (only desktop)")
			.build());

		return optionsTM;
	}

	private void setTmDataFromCommandLine(CommandLine cmdLine) {
		channel = cmdLine.getOptionValue(ChannelNameParam);
		suite = cmdLine.getOptionValue(SuiteNameParam);
		application = cmdLine.getOptionValue(AppNameParam);
		version = cmdLine.getOptionValue(VersionNameParam);
		url = cmdLine.getOptionValue(URLNameParam);
		driver = cmdLine.getOptionValue(DriverNameParam);
		serverDNS = cmdLine.getOptionValue(ServerDNSNameParam);
		String[] groups = cmdLine.getOptionValues(GroupsNameParam);
		if (groups!=null) {
			groupsCommaSeparated = String.join(",", groups);
		}
		String[] tcases = cmdLine.getOptionValues(TCaseNameParam);
		if (tcases!=null) {
			tcasesCommaSeparated = String.join(",", tcases);
		}
		String[] mails = cmdLine.getOptionValues(MailsParam);
		if (mails!=null) {
			mailsCommaSeparated = String.join(",", mails);
		}
		threads = cmdLine.getOptionValue(ThreadsParam);
		reciclewd = cmdLine.getOptionValue(RecicleWDParam);
		asyncexec = cmdLine.getOptionValue(AsyncExecParam);
		remote = cmdLine.getOptionValue(RemoteParam);
		net = cmdLine.getOptionValue(NetAnalysisParam);
		storebd = cmdLine.getOptionValue(StoreBdParam);
		typeAccess = cmdLine.getOptionValue(TypeAccessParam);
		driverVersion = cmdLine.getOptionValue(DriverVersionParam);
		
		//BrowserStack
		bStackUser = cmdLine.getOptionValue(BStackUserParam);
		bStackPassword = cmdLine.getOptionValue(BStackPasswordParam);
		bStackOs = cmdLine.getOptionValue(BStackOsParam);
		bStackOsVersion = cmdLine.getOptionValue(BStackOsVersionParam);
		bStackDevice = cmdLine.getOptionValue(BStackDeviceParam);
		bStackRealMobile = cmdLine.getOptionValue(BStackRealMobileParam);
		bStackBrowser = cmdLine.getOptionValue(BStackBrowserParam);
		bStackBrowserVersion = cmdLine.getOptionValue(BStackBrowserVersionParam);
		bStackResolution = cmdLine.getOptionValue(BStackResolutionParam);
	}

	private enum ParamTM {
		Suite(SuiteNameParam),
		Groups(GroupsNameParam),
		Driver(DriverNameParam),
		Channel(ChannelNameParam),
		Application(AppNameParam),
		Version(VersionNameParam),
		Url(URLNameParam),
		Tcases(TCaseNameParam),
		Threads(ThreadsParam),
		ServerDNS(ServerDNSNameParam),
		RecicleWD(RecicleWDParam),
		AsyncExec(AsyncExecParam),
		Remote(RemoteParam),
		NetAnalysis(NetAnalysisParam),
		StoreBd(StoreBdParam),
		Mails(MailsParam),
		TypeAccess(TypeAccessParam),
		DriverVersion(DriverVersionParam),
		TestObject(TestObjectParam),
		BStackUser(BStackUserParam),
		BStackPassword(BStackPasswordParam),
		BStackOs(BStackOsParam),
		BStackOsVersion(BStackOsVersionParam),
		BStackDevice(BStackDeviceParam),
		BStackRealMobile(BStackRealMobileParam),
		BStackBrowser(BStackBrowserParam),
		BStackBrowserVersion(BStackBrowserVersionParam),
		BStackResolution(BStackResolutionParam);
		
		public String nameParam;
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
		case Suite:
			return this.suite;
		case Groups:
			return this.groupsCommaSeparated;
		case Driver:
			return this.driver;
		case Channel:
			return this.channel;
		case Application:
			return this.application;
		case Version:
			return this.version;
		case Url:
			return this.url;
		case Tcases:
			return this.tcasesCommaSeparated;
		case Threads:
			return this.threads;
		case ServerDNS:
			return this.serverDNS;
		case RecicleWD:
			return this.reciclewd;
		case AsyncExec:
			return this.asyncexec;
		case Remote:
			return this.remote;
		case NetAnalysis:
			return this.net;
		case StoreBd:
			return this.storebd;
		case Mails:
			return this.mailsCommaSeparated;
		case TypeAccess:
			return this.typeAccess;
		case DriverVersion:
			return this.driverVersion;
		case BStackUser:
			return bStackUser;
		case BStackPassword:
			return bStackPassword;
		case BStackOs:
			return bStackOs;
		case BStackOsVersion:
			return bStackOsVersion;
		case BStackDevice:
			return bStackDevice;
		case BStackRealMobile:
			return bStackRealMobile;
		case BStackBrowser:
			return bStackBrowser;
		case BStackBrowserVersion:
			return bStackBrowserVersion;
		case BStackResolution:
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
	public String getDnsUrlAcceso() throws URISyntaxException {
		URI uri = new URI(getUrlBase());
		return (uri.getScheme() + "://" + uri.getHost());
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
	public List<String> getListTestCasesName() {
		List<String> listTestCasesName = new ArrayList<>();
		List<TestCaseParams> listTestCasesData = getListTestCasesData();
		for (TestCaseParams testCaseData : listTestCasesData) {
			listTestCasesName.add(testCaseData.getName());
		}
		return listTestCasesName;
	}
	
	public List<TestCaseParams> getListTestCasesData() {
		List<TestCaseParams> listTestCaseData = new ArrayList<>();
		for (String testCaseItem : getListTestCaseItems()) {
			TestCaseParams testCaseData = new TestCaseParams();
			Pattern pattern = Pattern.compile(patternTestCaseItem);
			Matcher matcher = pattern.matcher(testCaseItem);
			if (matcher.find()) {
				testCaseData.setName(matcher.group(1));
				if (matcher.group(2)!=null) {
					testCaseData.setInvocationCount(Integer.valueOf(matcher.group(2)));
					if (matcher.group(3)!=null) {
						testCaseData.setThreadPoolSize(Integer.valueOf(matcher.group(3)));
					}
				}
			} else {
				testCaseData.setName(testCaseItem);
			}
			listTestCaseData.add(testCaseData);
		}
		return listTestCaseData; 
	}
	public TestCaseParams getTestCaseParams(String nameTestCase) {
		for (TestCaseParams testCaseData : getListTestCasesData()) {
			if (TestNameUtils.isMethodNameTestCase(nameTestCase, testCaseData.getName())) {
				return testCaseData;
			}
		}
		return null;
	}
	
	public String getThreads() {
		return threads;
	}
	public Integer getThreadsNum() {
		if (threads!=null) {
			return Integer.valueOf(threads);
		}
		return null;
	}
	public void setThreads(String threads) {
		this.threads = threads;
	}
	public List<String> getMails() {
		if (mailsCommaSeparated!=null) {
			String[] mails = mailsCommaSeparated.split(",");
			return Arrays.asList(mails);
		}
		return Arrays.asList();
	}
	public void setMails(List<String> mailsInput) {
		String[] mails = mailsInput.toArray(new String[mailsInput.size()]);
		mailsCommaSeparated = String.join(",", mails);
	}
	public boolean isSendMailInEndSuite() {
		return (getMails()!=null && getMails().size()>0);
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
			return ManagementWebdriver.recycle;
		}
		return ManagementWebdriver.discard;
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
	
	public String getDriverVersion() {
		return driverVersion;
	}
	public void setDriverVersion(String driverVersion) {
		this.driverVersion = driverVersion;
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
		DataFilterTCases dFilter = new DataFilterTCases(getChannel(), getApp());
		dFilter.setGroupsFilter(getGroupsFilter());
		dFilter.setTestCasesFilter(getListTestCasesName());
		return dFilter;
	}

	private static String[] getNames(Enum<?>[] enumConstants) {
		return Arrays.stream(enumConstants).map(Enum::name).toArray(String[]::new);
	}
}
