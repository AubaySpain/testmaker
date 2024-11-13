package com.github.jorge2m.testmaker.domain;

import java.util.ArrayList;
import java.util.List;

import com.github.jorge2m.testmaker.conf.UtilsPath;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteTM;
import com.github.jorge2m.testmaker.domain.testfilter.TestMethod;
import com.github.jorge2m.testmaker.domain.testfilter.TestMethodData;
import com.github.jorge2m.testmaker.service.TestMaker;

public abstract class CreatorSuiteRun {
	
	protected InputParamsTM inputParams;
	protected SuiteMaker suiteMaker;
	protected boolean isRestExecution;
	
	public abstract SuiteMaker getSuiteMaker() throws Exception;
	
	protected CreatorSuiteRun() throws Exception { }
	
	protected CreatorSuiteRun(InputParamsTM inputParams) throws Exception {
		this.inputParams = inputParams;
		this.suiteMaker = getSuiteMaker();
	}
	
	public void setInputParams(InputParamsTM inputParams) throws Exception {
		this.inputParams = inputParams;
		this.suiteMaker = getSuiteMaker();
	}
	
	public SuiteTM getSuite() throws Exception {
		return suiteMaker.getSuite();
	}
	
	public boolean isSuite(Enum<?> suite) {
		if (inputParams==null ||
			inputParams.getSuite()==null) {
			return false;
		}
		return suite.name().compareTo(inputParams.getSuite().name())==0;
	}
	
	public SuiteTM execTestSuite(boolean async) throws Exception {
		return execTestSuite(async, false);
	}
	
	public SuiteTM execTestSuite(boolean async, boolean isRestExecution) throws Exception {
		this.isRestExecution = isRestExecution;
		makeSuiteMakerIfNull();
		var suite = suiteMaker.getSuite();
		if (!isRestExecution) {
			UtilsPath.setUserDir();
			suite.setApiRestExecution(isRestExecution);
		}		
		
		TestMaker.run(suite, async);
		return suite;
	}

	public List<TestMethod> getListAllTestCases() throws Exception {
		makeSuiteMakerIfNull();
		return suiteMaker.getListTests();
	}

	public List<TestMethodData> getListAllTestCasesData() throws Exception {
		List<TestMethodData> listTestMethods = new ArrayList<>();
		for (var testMethod : getListAllTestCases()) {
			listTestMethods.add(testMethod.getData());
		}
		return listTestMethods;
	}
	
	private void makeSuiteMakerIfNull() throws Exception {
		if (suiteMaker==null) {
			suiteMaker = getSuiteMaker();
		}
	}
}
