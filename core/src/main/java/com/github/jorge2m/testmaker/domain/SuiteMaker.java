package com.github.jorge2m.testmaker.domain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.xml.XmlTest;
import org.testng.xml.XmlSuite.ParallelMode;

import com.github.jorge2m.testmaker.boundary.listeners.InvokeListener;
import com.github.jorge2m.testmaker.boundary.listeners.MyTransformer;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteTM;
import com.github.jorge2m.testmaker.domain.testfilter.FilterTestsSuiteXML;
import com.github.jorge2m.testmaker.domain.testfilter.TestMethod;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.testreports.html.Reporter;

public abstract class SuiteMaker {

	private static final int DEFAULT_THREAD_COUNT = 3;
	
	private final String idSuiteExecution;
	private final InputParamsTM inputData;
	private final FilterTestsSuiteXML filterSuiteXML;
	private final boolean isApiRestExecution;

	private Map<String,String> parameters = new HashMap<>();

	private List<TestRunMaker> listTestRuns = new ArrayList<>();
	private ParallelMode parallelMode = ParallelMode.METHODS;
	private int threadCount = DEFAULT_THREAD_COUNT;
	private SuiteTM suite;
	
	protected SuiteMaker(InputParamsTM inputData) {
		this(inputData, false);
	}
	
	protected SuiteMaker(InputParamsTM inputData, boolean isApiRestExecution) {
		this.idSuiteExecution = getIdSuiteExecution(inputData);
		this.inputData = inputData;
		this.filterSuiteXML = FilterTestsSuiteXML.getNew(inputData.getDataFilter());
		this.isApiRestExecution = isApiRestExecution;
	}
	
	private String getIdSuiteExecution(InputParamsTM inputData) {
		if (inputData.getIdExecSuite()!=null && "".compareTo(inputData.getIdExecSuite())!=0) {
			return inputData.getIdExecSuite();
		}
		return makeIdSuiteExecution();
	}
	
	private static synchronized String makeIdSuiteExecution() {
		Calendar c1 = Calendar.getInstance(); 
		String timestamp = new SimpleDateFormat("yyMMdd_HHmmssSS").format(c1.getTime());
		PageObjTM.waitMillis(1);
		return (timestamp);
	}
	
	public List<TestMethod> getListTests() {
		XmlTest testRun = getTestRun();
		return filterSuiteXML.getInitialTestCaseCandidatesToExecute(testRun);
	}

	public SuiteTM getSuite() {
		generateXmlSuiteIfNotAvailable();
		return suite;
	}

	public XmlTest getTestRun() {
		generateXmlSuiteIfNotAvailable();
		return (suite.getTests().get(0));
	}

	protected void setParameters(Map<String,String> parameters) {
		this.parameters = parameters;
	}

	protected void addParameters(Map<String,String> parameters) {
		if (this.parameters==null) {
			setParameters(parameters);
		} else {
			this.parameters.putAll(parameters);
		}
	}

	protected void addTestRun(TestRunMaker testRun) {
		listTestRuns.add(testRun);
	}

	protected void addTestRuns(List<TestRunMaker> testRuns) {
		listTestRuns.addAll(testRuns);
	}

	protected void setParallelMode(ParallelMode parallelMode) {
		this.parallelMode = parallelMode;
	}

	protected void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}

	private List<Class<?>> createStandardListeners() {
		List<Class<?>> listeners = new ArrayList<>();
		listeners.add(MyTransformer.class);
		listeners.add(InvokeListener.class);
		if (!inputData.isTestExecutingInRemote()) {
			listeners.add(Reporter.class);
		}
		return listeners;
	}

	private void generateXmlSuiteIfNotAvailable() {
		if (suite==null) {
			suite = createSuite();
		}
	}

	private SuiteTM createSuite() {
		SuiteTM suite = new SuiteTM(idSuiteExecution, inputData, isApiRestExecution);
		String suiteName = inputData.getSuiteName();
		suite.setFileName(suiteName + ".xml");
		suite.setName(suiteName);
		suite.setListenersClass(createStandardListeners());
		suite.setParameters(parameters);
		suite.setParallel(parallelMode);
		if (!inputData.getThreadsNum().isPresent()) {
			suite.setThreadCount(threadCount);
		} else {
			suite.setThreadCount(inputData.getThreadsNum().get());
		}
		createTestRuns(suite);
		return suite;
	}

	private void createTestRuns(SuiteTM suite) {
		for (TestRunMaker testRun : listTestRuns) {
			testRun.createTestRun(suite, filterSuiteXML, inputData);
		}
	}
}
