package com.github.jorge2m.testmaker.testreports.html;

import java.util.ArrayList;
import java.util.List;

import com.github.jorge2m.testmaker.domain.suitetree.SuiteBean;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseBean;
import com.github.jorge2m.testmaker.domain.suitetree.TestRunBean;
import com.github.jorge2m.testmaker.service.TestMaker;

public class SuiteTestCasesData {

	private final SuiteBean suite;
	private final List<TestCaseBean> testCases;
	
	public SuiteTestCasesData(SuiteBean suite) throws Exception {
		this.suite = suite;
		this.testCases = getListTestCasesFromSuite(suite);
	}

	public SuiteBean getSuite() {
		return suite;
	}

	public List<TestCaseBean> getTestCases() {
		return testCases;
	}
	
	private List<TestCaseBean> getListTestCasesFromSuite(SuiteBean suite) throws Exception {
		List<TestCaseBean> listTestCases = new ArrayList<>();
		if (suite.getListTestRun().size() > 0) {
			//From memory
			for (TestRunBean testRun : suite.getListTestRun()) {
				listTestCases.addAll(testRun.getListTestCase());
			}
		} else {
			//From repository
			listTestCases = TestMaker.getRepository().getListTestCases(suite.getIdExecSuite());
		}
		return listTestCases;
	}

}
