package org.aubay.testmaker.domain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.aubay.testmaker.boundary.listeners.InvokeListener;
import org.aubay.testmaker.boundary.listeners.MyTransformer;
import org.aubay.testmaker.conf.defaultmail.DefaultMailEndSuite;
import org.aubay.testmaker.domain.suitetree.SuiteTM;
import org.aubay.testmaker.domain.testfilter.FilterTestsSuiteXML;
import org.aubay.testmaker.domain.testfilter.TestMethod;
import org.aubay.testmaker.service.webdriver.pageobject.SeleniumUtils;
import org.aubay.testmaker.testreports.html.Reporter;
import org.testng.xml.XmlTest;
import org.testng.xml.XmlSuite.ParallelMode;

public abstract class SuiteMaker {

	private final String idSuiteExecution;
	private final InputParamsTM inputData;
    private final FilterTestsSuiteXML filterSuiteXML;

    private Map<String,String> parameters;
    private SenderMailEndSuiteI senderMail = new DefaultMailEndSuite();
    
    private List<TestRunMaker> listTestRuns = new ArrayList<>();
    private ParallelMode parallelMode = ParallelMode.METHODS;
    private int threadCount = 3;
	private SuiteTM suite;
	
	protected SuiteMaker(InputParamsTM inputData) {
		this.idSuiteExecution = makeIdSuiteExecution();
		this.inputData = inputData;
		this.filterSuiteXML = FilterTestsSuiteXML.getNew(inputData.getDataFilter());
	}
	
    private static synchronized String makeIdSuiteExecution() {
        Calendar c1 = Calendar.getInstance();
        String timestamp = new SimpleDateFormat("yyMMdd_HHmmssSS").format(c1.getTime());
        SeleniumUtils.waitMillis(1);
        return (timestamp);
    }
	
    public List<TestMethod> getListTests() {
        XmlTest testRun = getTestRun();
        return (
        	filterSuiteXML.getInitialTestCaseCandidatesToExecute(testRun)
        );
    }
    
    public SuiteTM getSuite() {
    	generateXmlSuiteIfNotAvailable();
    	suite.setSenderMail(senderMail);
    	return suite;
    }
    
    public XmlTest getTestRun() {
    	generateXmlSuiteIfNotAvailable();
        return (suite.getTests().get(0));
    }

	public SenderMailEndSuiteI getSenderMail() {
		return senderMail;
	}
	public void setSenderMail(SenderMailEndSuiteI senderMail) {
		this.senderMail = senderMail;
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
    	SuiteTM suite = new SuiteTM(idSuiteExecution, inputData);
    	String suiteName = inputData.getSuiteName();
        suite.setFileName(suiteName + ".xml");
        suite.setName(suiteName);
        suite.setListenersClass(createStandardListeners());
        suite.setParameters(parameters);
        suite.setParallel(parallelMode);
        suite.setThreadCount(threadCount);
        createTestRuns(suite);
        return suite;
    }
    
    private void createTestRuns(SuiteTM suite) {
    	for (TestRunMaker testRun : listTestRuns) {
    		testRun.createTestRun(suite, filterSuiteXML, inputData);
    	}
    }
}
