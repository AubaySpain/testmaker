package com.github.jorge2m.testmaker.domain.suitetree;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.TestRunner;
import org.testng.xml.XmlGroups;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.StateExecution;
import com.github.jorge2m.testmaker.service.webdriver.maker.DriverMaker;
import com.github.jorge2m.testmaker.testreports.stepstore.StepEvidenceStorer;

public class TestRunTM extends XmlTest {

	private static final long serialVersionUID = -4002416107477209626L;
	private StateExecution stateExecution = StateExecution.STARTED;
	private State state = State.OK;
	public XmlGroups x_xmlGroupsVisible;
	private ITestContext testNgContext;
	private StepEvidenceStorer storerErrorEvidence = null;
	private List<TestCaseTM> listTestCases = new ArrayList<>();
	//private BrowserStackDesktop browserStackDesktop = null;
	//private BrowserStackMobil browserStackMobil = null;
	private DriverMaker driverMakerUser = null;


	public TestRunTM(XmlSuite suite, int index) {
		super(suite, index);
	}

	public TestRunTM(XmlSuite suite) {
		super(suite);
	}

	public void end() {
		state = getStateFromTestCases();
		stateExecution = StateExecution.STOPPED;
	}
	
	private State getStateFromTestCases() {
		var stateReturn = State.OK;
		for (var testCase : getListTestCases()) {
			var stateTestCase = testCase.getStateResult();
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

	public StepEvidenceStorer getStorerErrorEvidence() {
		return storerErrorEvidence;
	}

	public void setStorerErrorStep(StepEvidenceStorer storerErrorEvidence) {
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
	
	
	public DriverMaker getDriverMakerUser() {
		return driverMakerUser;
	}
	public void setDriverMakerUser(DriverMaker driverMakerUser) {
		this.driverMakerUser = driverMakerUser;
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
    
//	public void setBrowserStackDesktop(BrowserStackDesktop browserStackDesktop) {
//		this.browserStackDesktop = browserStackDesktop;
//	}
//	
//	public void setBrowserStackMobil(BrowserStackMobil browserStackMobil) {
//		this.browserStackMobil = browserStackMobil;
//	}
//	
//	public BrowserStackDesktop getBrowserStackDesktop() {
//		return this.browserStackDesktop;
//	}
//	
//	public BrowserStackMobil getBrowserStackMobil() {
//		return this.browserStackMobil;
//	}
	
	public TestRunBean getTestRunBean() {
		TestRunBean testRunBean = new TestRunBean();
		SuiteTM suite = getSuiteParent();
		
		testRunBean.setIdExecSuite(suite.getIdExecution());
		testRunBean.setSuiteName(suite.getName());
		testRunBean.setName(getName());
		testRunBean.setResult(getResult());
//		if (getBrowserStackMobil()!=null) {
//			testRunBean.setDevice(getBrowserStackMobil().getDevice());
//		} else {
//			testRunBean.setDevice("");
//		}
		
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
		testRunBean.setDriver(suite.getInputParams().getDriver());
		
		List<TestCaseBean> listTestCase = new ArrayList<>();
		for (TestCaseTM testCase : getListTestCases()) {
			listTestCase.add(testCase.getTestCaseBean());
		}
		testRunBean.setListTestCase(listTestCase);
		
		return testRunBean;
	}
}
