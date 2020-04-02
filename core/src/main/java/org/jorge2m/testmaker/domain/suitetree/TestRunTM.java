package org.jorge2m.testmaker.domain.suitetree;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jorge2m.testmaker.conf.State;
import org.jorge2m.testmaker.domain.StateExecution;
import org.jorge2m.testmaker.service.webdriver.maker.brwstack.BrowserStackDesktop;
import org.jorge2m.testmaker.service.webdriver.maker.brwstack.BrowserStackMobil;
import org.jorge2m.testmaker.testreports.stepstore.EvidenceStorer;
import org.testng.ITestContext;
import org.testng.TestRunner;
import org.testng.xml.XmlGroups;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

public class TestRunTM extends XmlTest {

	private static final long serialVersionUID = -4002416107477209626L;
	private StateExecution stateExecution = StateExecution.Started;
	private State state = State.Ok;
	public XmlGroups x_xmlGroupsVisible;
	private ITestContext testNgContext;
	private EvidenceStorer storerErrorEvidence = null;
	private List<TestCaseTM> listTestCases = new ArrayList<>();
	private BrowserStackDesktop browserStackDesktop = null;
	private BrowserStackMobil browserStackMobil = null;
	
	public TestRunTM(XmlSuite suite, int index) {
		super(suite, index);
	}

	public TestRunTM(XmlSuite suite) {
		super(suite);
	}

	public void end() {
		state = getStateFromTestCases();
		stateExecution = StateExecution.Stopped;
	}
	
	private State getStateFromTestCases() {
		State stateReturn = State.Ok;
		for (TestCaseTM testCase : getListTestCases()) {
			State stateTestCase = testCase.getStateResult();
			if (stateTestCase.isMoreCriticThan(stateReturn)) {
				stateReturn = stateTestCase;
			}
		}
		return stateReturn;
	}
	
	public StateExecution getStateExecution() {
		return stateExecution;
	}
	
	public void setStateExecution(StateExecution stateExecution) {
		this.stateExecution = stateExecution;
	}
	
	public State getResult() {
		return state;
	}
	
	public SuiteTM getSuiteParent() {
		return (SuiteTM)getSuite();
	}

	public ITestContext getTestNgContext() {
		return testNgContext;
	}

	public void setTestNgContext(ITestContext testNgContext) {
		this.testNgContext = testNgContext;
		String suiteDirectory = ((SuiteTM)getSuite()).getPathDirectory();
		setTestRunOutputDirectory(suiteDirectory);
	}

	public EvidenceStorer getStorerErrorEvidence() {
		return storerErrorEvidence;
	}

	public void setStorerErrorStep(EvidenceStorer storerErrorEvidence) {
		this.storerErrorEvidence = storerErrorEvidence;
	}

	private void setTestRunOutputDirectory(String outputDirectory) {
		TestRunner runner = (TestRunner)testNgContext;
		runner.setOutputDirectory(outputDirectory);  
	}

	public XmlGroups getGroups() {
		return this.x_xmlGroupsVisible;
	}

	@Override
	public void setGroups(XmlGroups xmlGroups) {
		super.setGroups(xmlGroups);
		this.x_xmlGroupsVisible = xmlGroups;
	}
	
	public List<TestCaseTM> getListTestCases() {
		return listTestCases;
	}
	
	public int getNumTestCases() {
		return getListTestCases().size();
	}

	
	public void addTestCase(TestCaseTM testCase) {
		listTestCases.add(testCase);
	}
    
	public void setBrowserStackDesktop(BrowserStackDesktop browserStackDesktop) {
		this.browserStackDesktop = browserStackDesktop;
	}
	
	public void setBrowserStackMobil(BrowserStackMobil browserStackMobil) {
		this.browserStackMobil = browserStackMobil;
	}
	
	public BrowserStackDesktop getBrowserStackDesktop() {
		return this.browserStackDesktop;
	}
	
	public BrowserStackMobil getBrowserStackMobil() {
		return this.browserStackMobil;
	}
	
	public TestRunBean getTestRunBean() {
		TestRunBean testRunBean = new TestRunBean();
		SuiteTM suite = getSuiteParent();
		
		testRunBean.setIdExecSuite(suite.getIdExecution());
		testRunBean.setSuiteName(suite.getName());
		testRunBean.setName(getName());
		testRunBean.setResult(getResult());
		if (getBrowserStackMobil()!=null) {
			testRunBean.setDevice(getBrowserStackMobil().getDevice());
		} else {
			testRunBean.setDevice("");
		}
		
		Date inicio = new Date();
		Date fin = new Date();
		ITestContext ctxTestRun = getTestNgContext();
		if (ctxTestRun!=null) {
			inicio = getTestNgContext().getStartDate();
			Date endDate = getTestNgContext().getEndDate();
			if (endDate!=null) {
				fin = endDate;
			}
		}
		testRunBean.setInicioDate(inicio);
		testRunBean.setFinDate(fin);
		if (fin!=null && inicio!=null) {
			testRunBean.setDurationMillis(fin.getTime() - inicio.getTime());
		}
		testRunBean.setNumberTestCases(getNumTestCases());
		testRunBean.setWebDriverType(suite.getInputParams().getWebDriverType());
		
		List<TestCaseBean> listTestCase = new ArrayList<>();
		for (TestCaseTM testCase : getListTestCases()) {
			listTestCase.add(testCase.getTestCaseBean());
		}
		testRunBean.setListTestCase(listTestCase);
		
		return testRunBean;
	}
}
