package com.github.jorge2m.testmaker.domain;

import java.util.ArrayList;
import java.util.List;

import com.github.jorge2m.testmaker.domain.suitetree.SuiteTM;
import com.github.jorge2m.testmaker.domain.testfilter.TestMethod;
import com.github.jorge2m.testmaker.domain.testfilter.TestMethodData;
import com.github.jorge2m.testmaker.service.TestMaker;

public abstract class CreatorSuiteRun {
	
	protected InputParamsTM inputParams;
	protected SuiteMaker suiteMaker;
	
	public abstract SuiteMaker getSuiteMaker() throws Exception;
	
	public CreatorSuiteRun() throws Exception { }
	
	public CreatorSuiteRun(InputParamsTM inputParams) throws Exception {
		this.inputParams = inputParams;
		this.suiteMaker = getSuiteMaker();
	}
	
	public void setInputParams(InputParamsTM inputParams) throws Exception {
		this.inputParams = inputParams;
		this.suiteMaker = getSuiteMaker();
	}
	
	public SuiteTM getSuite() {
		return suiteMaker.getSuite();
	}
	
	public SuiteTM execTestSuite(boolean async) throws Exception {
		SuiteTM suite = suiteMaker.getSuite();
		TestMaker.run(suite, async);
		return suite;
	}

	public List<TestMethod> getListAllTestCases() {
		return suiteMaker.getListTests();
	}

	public List<TestMethodData> getListAllTestCasesData() {
		List<TestMethodData> listTestMethods = new ArrayList<>();
		for (TestMethod testMethod : getListAllTestCases()) {
			listTestMethods.add(testMethod.getData());
		}
		return listTestMethods;
	}
}
