package com.github.jorge2m.testmaker.domain.suitetree;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.logging.log4j.Logger;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.conf.ConstantesTM;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.domain.RepositoryI.StoreUntil;
import com.github.jorge2m.testmaker.domain.StateExecution;
import com.github.jorge2m.testmaker.domain.SuitesExecuted;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.service.notifications.SuiteNotificationSender;
import com.github.jorge2m.testmaker.service.webdriver.pool.PoolWebDrivers;
import com.github.jorge2m.testmaker.testreports.html.GenerateReports;

public class SuiteTM extends XmlSuite {
	
	private static final long serialVersionUID = 1L;
	private final InputParamsTM inputParams;
	private final String idSuiteExecution;
	private long threadId;
	private StateExecution stateExecution = StateExecution.NOT_STARTED;
	private State result = State.OK;
	private int numTestCaseRetries = 0;
	private String infoExecution;
	private long timeInicio = 0;
	private long timeFin = 0;
	private final PoolWebDrivers poolWebDrivers;
	private List<Object> factoryTests = new ArrayList<>();
	
	public SuiteTM(String idSuiteExecution, InputParamsTM inputParams) {
		this.idSuiteExecution = idSuiteExecution;
		this.inputParams = inputParams;
		this.poolWebDrivers = new PoolWebDrivers(this);
	}
	
	public Logger getLogger() {
		return Log4jTM.getSuiteLogger(idSuiteExecution, getPathLogFile());
	}
	
	public String getIdExecution() {
		return idSuiteExecution;
	}

	public long getThreadId() {
		return threadId;
	}
	
	public InputParamsTM getInputParams() {
		return inputParams;
	}
	
	public StateExecution getStateExecution() {
		return stateExecution;
	}
	
	public void setStateExecution(StateExecution stateExecution) {
		this.stateExecution = stateExecution;
	}
	
	public State getResult() {
		return result;
	}
	
	public List<TestRunTM> getListTestRuns() {
		List<TestRunTM> listTestRuns = new ArrayList<>();
		for (XmlTest xmlTest : getTests()) {
			listTestRuns.add((TestRunTM)xmlTest);
		}
		return listTestRuns;
	}
	
	public int getNumberTestCases() {
		int numTestCases = 0;
		for (var testRun : getListTestRuns()) {
			numTestCases+=testRun.getNumTestCases();
		}
		return numTestCases;
	}
	
	public int getNumberTestCases(StateExecution state) {
		return getTestCases(state).size();
	}

	public List<TestCaseTM> getTestCases(StateExecution state) {
		return 
			getTestCases().stream()
				.filter(s -> s.getStateRun()==state)
				.collect(Collectors.toList());
	}
	public List<TestCaseTM> getTestCases() {
		return 
			getListTestRuns().stream()
				.map(s -> s.getListTestCases())
				.flatMap(List::stream)
				.collect(Collectors.toList());
	}
	
	public List<StepTM> getStepsFromNoRetriedTestCases(State state) {
		return getTestCases().stream()
				.filter(s -> s.getStateResult()!=State.RETRY)
				.map(t -> t.getListStep())
				.flatMap(List::stream)
				.filter(s -> s.getResultSteps()==state)
				.collect(Collectors.toList());		
	}
	
	public List<StepTM> getSteps(State state) {
		return getTestCases().stream()
			.map(t -> t.getListStep())
			.flatMap(List::stream)
			.filter(s -> s.getResultSteps()==state)
			.collect(Collectors.toList());
	}
	 
	public PoolWebDrivers getPoolWebDrivers() {
		return poolWebDrivers;
	}
	
	public void start() {
		this.threadId = Thread.currentThread().getId();
		stateExecution = StateExecution.STARTED;
		timeInicio = (new Date()).getTime(); 
		SuitesExecuted.add(this);
		if (inputParams.getStoreBd().storeSuite()) {
			TestMaker.getRepository().store(getSuiteBean(), StoreUntil.suite);
		}
	}
	
	public void end() {
		stateExecution = StateExecution.FINISHED;
		result = getResultFromTestsRun();
		timeFin = (new Date()).getTime(); 
		poolWebDrivers.removeAllStrWd();
		if (inputParams.getStoreBd().storeSuite()) {
			TestMaker.getRepository().store(getSuiteBean(), inputParams.getStoreBd());
		}
		SuitesExecuted.remove(this);
		Log4jTM.removeSuiteLogger(idSuiteExecution);
	}
	
	public void sendAlarmsIfNeeded() {
		result = getResultFromTestsRun();
		if (result.isMoreCriticThan(State.WARN)) {
			var suiteAlarm = SuiteNotificationSender.make(this);
			if (suiteAlarm.canSend()) {
				suiteAlarm.send();
			}
		}
	}
	
	private State getResultFromTestsRun() {
		var stateReturn = State.OK;
		for (var testRun : getListTestRuns()) {
			if (testRun.getResult().isMoreCriticThan(stateReturn)) {
				stateReturn = testRun.getResult();
			}
		}
		return stateReturn;
	}
	
	public Date getInicio() {
		return new Date(getTimeInicio());
	}
	public Date getFin() {
		return new Date(getTimeFin());
	}
	
	public long getTimeInicio() {
		return timeInicio;
	}
	public void setTimeInicio(long timeInicio) {
		this.timeInicio = timeInicio;
	}
	public long getTimeFin() {
		return timeFin;
	}
	public void setTimeFin(long timeFin) {
		this.timeFin = timeFin;
	}
	public long getDurationMillis() {
		return timeFin - timeInicio;
	}
	public long getTimeFromInit(TimeUnit timeUnit) {
		Long now = new Date().getTime();
		return (timeUnit.convert(now - getTimeInicio(), TimeUnit.MILLISECONDS));
	}
	
	public String getInfoExecution() {
		return infoExecution;
	}
	public void setInfoExecution(String infoExecution) {
		this.infoExecution = infoExecution;
	}
	
	public List<Object> getFactoryTests() {
		return factoryTests;
	}
	public void addFactoryTests(List<Object> newFactoryTests) {
		factoryTests.addAll(newFactoryTests);
	}
	public boolean isTestFromFactory(Object test) {
		for (Object testFactory : factoryTests) {
			if (testFactory==test) {
				return true;
			}
		}
		return false;
	}
	
	public void setListenersClass(List<Class<?>> listListeners) {
		List<String> listListenersStr = new ArrayList<>();
		for (Class<?> listener : listListeners) {
			listListenersStr.add(listener.getCanonicalName());
		}
		setListeners(listListenersStr);
	}
	
	public String getPathDirectory() {
		return getPathDirectory(getName(), getIdExecution());
	}
	public static String getPathDirectory(String nameSuite, String idExecutionSuite) {
		return (
			getPathDirectoryOutputTests() + File.separator + 
			nameSuite + File.separator + 
			idExecutionSuite);
	}
	public static String getPathDirectoryOutputTests() {
		String userDir = System.getProperty("user.dir");
		String lastCharUserDir = userDir.substring(userDir.length() - 1);
		if (File.separator.compareTo(lastCharUserDir)!=0) {
			userDir+=File.separator;
		}
		return (
			userDir +
			ConstantesTM.DIRECTORY_OUTPUT_TESTS);
	}
	
	public String getPathReportHtml() {
		return (getPathDirectory() + File.separator + ConstantesTM.NAME_REPORT_HTML_TSUITE);
	}
	public String getPathLogFile() {
		return (getPathDirectory() + File.separator + ConstantesTM.MAME_LOG_FILE_SUITE);
	}
	
	public String getDnsReportHtml() {
		String pathFileReport = getPathReportHtml();
		return (GenerateReports.getDnsOfFileReport(
				pathFileReport.replace("\\", "/"), 
				inputParams.getWebAppDNS(), 
				inputParams.getTypeAccess()));
	}
	
	public static SuiteTM getSuiteCreatedInPresentThread() {
		Long threadId = Thread.currentThread().getId();
		List<SuiteTM> listSuites = SuitesExecuted.getSuitesExecuted();
		for (SuiteTM suite : listSuites) {
			if (threadId==suite.getThreadId()) {
				return suite;
			}
		}
		return null;
	}
	
	public SuiteBean getSuiteBean() {
		var suiteBean = new SuiteBean();
		suiteBean.setIdExecSuite(getIdExecution());
		suiteBean.setName(getName());
		suiteBean.setVersion(inputParams.getVersion());
		suiteBean.setChannel(inputParams.getChannel());
		suiteBean.setApp(inputParams.getApp().toString());
		suiteBean.setDriver(inputParams.getDriver());
		suiteBean.setResult(getResult());
		suiteBean.setInicioDate(getInicio());
		suiteBean.setFinDate(getFin());
		suiteBean.setDurationMillis(getDurationMillis());
		suiteBean.setInfoExecution(getInfoExecution());
		suiteBean.setNumberTestCases(getNumberTestCases());
		suiteBean.setMoreInfo(inputParams.getMoreInfo());
		suiteBean.setUrlBase(inputParams.getUrlBase());
		suiteBean.setPathReportHtml(getPathReportHtml());
		suiteBean.setUrlReportHtml(getDnsReportHtml());
		suiteBean.setPathLogFile(getPathLogFile());
		suiteBean.setStateExecution(getStateExecution());
		
		List<TestRunBean> listTestRun = new ArrayList<>();
		for (TestRunTM testRun : getListTestRuns()) {
			listTestRun.add(testRun.getTestRunBean());
		}
		suiteBean.setListTestRun(listTestRun);
		
		return suiteBean;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj==this);
	}
	
	@Override
	public int hashCode() {
		return this.hashCode();
	}

	public int getNumTestCaseRetries() {
		return numTestCaseRetries;
	}

	public void incrementTestCaseRetries() {
		this.numTestCaseRetries+=1;
	}
	
}
