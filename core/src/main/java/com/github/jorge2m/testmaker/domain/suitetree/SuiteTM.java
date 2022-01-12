package com.github.jorge2m.testmaker.domain.suitetree;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.github.jorge2m.testmaker.conf.ConstantesTM;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.conf.defaultmail.DefaultMailEndSuite;
import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.domain.RepositoryI.StoreUntil;
import com.github.jorge2m.testmaker.domain.SenderMailEndSuiteI;
import com.github.jorge2m.testmaker.domain.StateExecution;
import com.github.jorge2m.testmaker.domain.SuitesExecuted;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.service.webdriver.pool.PoolWebDrivers;
import com.github.jorge2m.testmaker.testreports.html.GenerateReports;

public class SuiteTM extends XmlSuite {
	
	private static final long serialVersionUID = 1L;
	private final InputParamsTM inputParams;
	private final String idSuiteExecution;
	//private Logger logger;
	private long threadId;
	private StateExecution stateExecution = StateExecution.NotStarted;
	private State result = State.Ok;
	private String infoExecution;
	private long timeInicio = 0;
	private long timeFin = 0;
	private final PoolWebDrivers poolWebDrivers;
	private SenderMailEndSuiteI senderMail;
	private List<Object> factoryTests = new ArrayList<>();
	
	public SuiteTM(String idSuiteExecution, InputParamsTM inputParams) {
		this.idSuiteExecution = idSuiteExecution;
		this.inputParams = inputParams;
//		Log4jConfig log4jFactory = new Log4jConfig();
//		this.logger = log4jFactory.createSuiteLogger(idSuiteExecution, getPathLogFile());
		this.poolWebDrivers = new PoolWebDrivers(this);
		this.senderMail = new DefaultMailEndSuite();
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
	
	public void setSenderMail(SenderMailEndSuiteI senderMail) {
		this.senderMail = senderMail;
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
		for (TestRunTM testRun : getListTestRuns()) {
			numTestCases+=testRun.getNumTestCases();
		}
		return numTestCases;
	}
	
	public PoolWebDrivers getPoolWebDrivers() {
		return poolWebDrivers;
	}
	
	public void start() {
		this.threadId = Thread.currentThread().getId();
		stateExecution = StateExecution.Started;
		timeInicio = (new Date()).getTime(); 
		SuitesExecuted.add(this);
		if (inputParams.getStoreBd().storeSuite()) {
			TestMaker.getRepository().store(getSuiteBean(), StoreUntil.suite);
		}
	}
	
	public void end() {
		stateExecution = StateExecution.Finished;
		result = getResultFromTestsRun();
		timeFin = (new Date()).getTime(); 
		poolWebDrivers.removeAllStrWd();
		if (inputParams.isSendMailInEndSuite()) {
			senderMail.sendMail(this);
		}
		if (inputParams.getStoreBd().storeSuite()) {
			TestMaker.getRepository().store(getSuiteBean(), inputParams.getStoreBd());
		}
		SuitesExecuted.remove(this);
		Log4jTM.removeSuiteLogger(idSuiteExecution);
	}
	
	private State getResultFromTestsRun() {
		State stateReturn = State.Ok;
		for (TestRunTM testRun : getListTestRuns()) {
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
			ConstantesTM.directoryOutputTests);
	}
	
	public String getPathReportHtml() {
		return (getPathDirectory() + File.separator + ConstantesTM.nameReportHTMLTSuite);
	}
	public String getPathLogFile() {
		return (getPathDirectory() + File.separator + ConstantesTM.nameLogFileSuite);
	}
	
	public String getDnsReportHtml() {
		String pathFileReport = getPathReportHtml();
		return (GenerateReports.getDnsOfFileReport(pathFileReport, inputParams.getWebAppDNS(), inputParams.getTypeAccess()));
	}
	
	public static SuiteTM getSuiteCreatedInPresentThread() {
		Long threadId = Thread.currentThread().getId();
		List<SuiteTM> listSuites = SuitesExecuted.getSuitesExecuted();
		for (SuiteTM suite : listSuites) {
			if (threadId==suite.getThreadId()) {
				return suite;
			}
		}
		//TestMaker.grabLog(Level.WARN, "Not found Suite associated", null);
		//return listSuites.get(0);
		return null;
	}
	
	public SuiteBean getSuiteBean() {
		SuiteBean suiteBean = new SuiteBean();
		InputParamsTM inputParams = getInputParams();
		
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
		if (obj==this) {
			return true;
		}
		return false;
//		if (!(obj instanceof SuiteTM)) {
//			return false;
//		}
//		SuiteTM suite = (SuiteTM)obj;
//		return (
//			getIdExecution().compareTo(suite.getIdExecution())==0 &&
//			getThreadId()==suite.getThreadId());
	}
}
