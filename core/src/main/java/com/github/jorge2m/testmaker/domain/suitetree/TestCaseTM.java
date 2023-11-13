package com.github.jorge2m.testmaker.domain.suitetree;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.InitObject;

import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.domain.StateExecution;
import com.github.jorge2m.testmaker.domain.SuitesExecuted;
import com.github.jorge2m.testmaker.domain.util.TestNameUtils;

public class TestCaseTM  {

	private int invocationCount;
	private List<StepTM> listSteps = new ArrayList<>();
	private StateExecution stateRun = StateExecution.Started;
	private State state = State.Ok;
	private final SuiteTM suiteParent;
	private final TestRunTM testRunParent;
	private final ITestResult result;
	private final long threadId;
	private final InitTestObjects initTestObjects;
	private String specificInputData = "";
	private boolean exceptionInExecution = true;

	public TestCaseTM(ITestResult result) {
		this.testRunParent = (TestRunTM)result.getTestContext().getCurrentXmlTest();
		this.suiteParent = (SuiteTM)testRunParent.getSuite();
		this.result = result;
		this.threadId = Thread.currentThread().getId();
		this.invocationCount = getInvocationCountForTest();
		this.initTestObjects = new InitTestObjects(this);
	}
	
	public void makeInitObjects(InitObject initObject) {
		initTestObjects.make(initObject);
	}
	
	private void updateInvocationCount() {
		this.invocationCount = getInvocationCountForTest();
	}
	
	private int getInvocationCountForTest() {
		List<Integer> listCounts = new ArrayList<>();
		var listTestCases = testRunParent.getListTestCases();
		for (var testCaseTM : listTestCases) {
			if (testCaseTM.getNameWithInputData().compareTo(getNameWithInputData())==0 &&
				testCaseTM!=this) {
				listCounts.add(testCaseTM.getInvocationCount());
			}
		}
		
		//Es el 1o
		if (listCounts.isEmpty()) {
			return 1;
		}
		//Buscamos un hueco entre los valores de counts existentes
		Collections.sort(listCounts);
		for (int i=0; i<listCounts.size(); i++) {
			if (listCounts.get(i) > i+1) {
				return listCounts.get(i) - 1;
			}
		}
		//Si no hay hueco retornamos el valor máximo + 1
		return (listCounts.get(listCounts.size()-1) + 1);
	}
	
	public int getInvocationCount() {
		return invocationCount;
	}
	
	public String getName() {
		return result.getName();
	}
	public String getCode() {
		return TestNameUtils.getCodeFromTestCase(getName());
	}
	
	public String getNameWithInputData() {
		if ("".compareTo(getSpecificInputData())!=0) {
			return getName() + " (" + getSpecificInputData() + ")";
		}
		return getName();
	}
	
	public String getNameUnique() {
		String nameTest = getNameWithInputData();
		if (getInvocationCount()>1) {
			nameTest+=" (" + getInvocationCount() + ")";
		}
		return nameTest;
	}
	
	public void end(State state) {
		stopTest();
		this.state = state;
	}
	
	public void end() {
		stopTest();
		this.state = getStateFromSteps();
	}
	
	private void stopTest() {
		setStateRun(StateExecution.Finished);
		initTestObjects.stopTestSignal();
	}
	
	public int getIndexInTestRun() {
		var listTestCases = testRunParent.getListTestCases();
		for (int i=0; i<listTestCases.size(); i++) {
			if (this==listTestCases.get(i)) {
				return i;
			}
		}
		return -1;
	}

	public State getStateResult() {
		return this.state;
	}
	
	public String getTestPathDirectory() {
		var suiteP = getSuiteParent();
		String suitePath = SuiteTM.getPathDirectory(
				suiteP.getName(), 
				suiteP.getIdExecution());
		
		return 
			suitePath + File.separator + 
			getTestRunParent().getName() + File.separator +
			getNameUnique();
	}
	
	public static Optional<TestCaseTM> getTestCase(ITestResult result) {
		return SuitesExecuted.getSuitesExecuted().stream()
			.map(SuiteTM::getListTestRuns).flatMap(List::stream)
			.map(TestRunTM::getListTestCases).flatMap(List::stream)
			.filter(t -> t!=null && t.getResult().equals(result))
			.filter(t -> t.getStateResult()!=State.Retry)
			.collect(Collectors.toList()).stream()
			.findFirst();
	}
	
	public static Optional<TestCaseTM> getTestCaseInExecution() {
		Long threadId = Thread.currentThread().getId();
		return SuitesExecuted.getSuitesExecuted().stream()
			.map(SuiteTM::getListTestRuns).flatMap(List::stream)
			.map(TestRunTM::getListTestCases).flatMap(List::stream)
			.filter(t -> 
				t.getThreadId().compareTo(threadId)==0 &&
				!t.getStateRun().isFinished())
			.collect(Collectors.toList()).stream()
			.findFirst();
	}
	
	public static Optional<WebDriver> getDriverTestCase() {
		var testCaseTM = TestCaseTM.getTestCaseInExecution();
		if (testCaseTM.isPresent()) {
			return Optional.of(testCaseTM.get().getDriver());
		}
		return Optional.empty();
	}
	
	public ITestResult getResult() {
		return this.result;
	}
	
	public void addStep(StepTM step) {
		listSteps.add(step);
	}
	public List<StepTM> getListStep() {
		return this.listSteps;
	}
	public void setListStep(List<StepTM> listSteps) {
		this.listSteps = listSteps;
	}

	public State getStateFromSteps() {
		var stateReturn = State.Ok;
		for (var step : getListStep()) {
			if (step.getResultSteps().isMoreCriticThan(stateReturn)) {
				stateReturn = step.getResultSteps();
			}
		}
		return stateReturn;
	}
	
	public StepTM getCurrentStepInExecution() {
		StepTM stepReturn = null;
		for (var step : listSteps) {
			if (!step.getState().isFinished()) {
				stepReturn = step;
			}
		}
		return stepReturn;
	}
	
	public StepTM getLastStep() {
		if (!listSteps.isEmpty()) {
			return (listSteps.get(listSteps.size() - 1));
		}
		return null;
	}
	
	public boolean isLastStep(StepTM step) {
		return (step==getLastStep());
	}
	
	public StateExecution getStateRun() {
		return this.stateRun;
	}
	
	public void setStateRun(StateExecution stateRun) {
		this.stateRun = stateRun;
	}
	
	public WebDriver getDriver() {
		return initTestObjects.getWebDriver();
	}
	
	public Long getThreadId() {
		return this.threadId;
	}
	
	public SuiteTM getSuiteParent() {
		return suiteParent;
	}
	
	public InputParamsTM getInputParamsSuite() {
		return getSuiteParent().getInputParams();
	}
	
	public TestRunTM getTestRunParent() {
		return testRunParent;
	}
	
	public ITestContext getTestRunContext() {
		return testRunParent.getTestNgContext();
	}
	
	public String getSpecificInputData() {
		return this.specificInputData;
	}
	
	public void addSufixToName(String sufix) {
		this.specificInputData = sufix;
		updateInvocationCount();
	}
	public static void addNameSufix(String sufix) throws NoSuchElementException {
		TestCaseTM.getTestCaseInExecution()
			.orElseThrow(NoSuchElementException::new)
			.addSufixToName(sufix);
	}
	
	public TestCaseBean getTestCaseBean() {
		var testCaseBean = new TestCaseBean();
		var suite = getSuiteParent();
		
		testCaseBean.setIdExecSuite(suite.getIdExecution());
		testCaseBean.setSuiteName(suite.getName());
		testCaseBean.setTestRunName(getTestRunParent().getName());
		testCaseBean.setName(getNameUnique());
		testCaseBean.setSpecificInputData(getSpecificInputData());
		testCaseBean.setNameUnique(getNameUnique());
		testCaseBean.setDescription(getResult().getMethod().getDescription());
		testCaseBean.setIndexInTestRun(getIndexInTestRun());
		testCaseBean.setResult(getStateResult());
		testCaseBean.setStatusTng(getResult().getStatus());
		
		Date inicio = new Date(getResult().getStartMillis());
		Date fin = new Date(getResult().getEndMillis());
		testCaseBean.setInicioDate(inicio);
		testCaseBean.setFinDate(fin); 
		testCaseBean.setDurationMillis((float)fin.getTime() - (float)inicio.getTime());
		
		testCaseBean.setNumberSteps(getListStep().size());
		testCaseBean.setClassSignature(getResult().getInstanceName());
		testCaseBean.setThrowable(toStringB64(getResult().getThrowable()));
		
		List<StepTM> listStepBean = new ArrayList<>();
		for (var step : getListStep()) {
			listStepBean.add(step);
		}
		testCaseBean.setListStep(listStepBean);
		
		return testCaseBean;
	}

	/** Write the object to a Base64 string. */
	private static String toStringB64(Serializable o) {
		try {
			var baos = new ByteArrayOutputStream();
			var oos = new ObjectOutputStream( baos );
			oos.writeObject( o );
			oos.close();
			return Base64.getEncoder().encodeToString(baos.toByteArray()); 
		}
		catch (Exception e) {
			return null;
		}
	}

	public boolean isExceptionInExecution() {
		return exceptionInExecution;
	}

	public void setExceptionInExecution(boolean exceptionInExecution) {
		this.exceptionInExecution = exceptionInExecution;
	}
	
}
