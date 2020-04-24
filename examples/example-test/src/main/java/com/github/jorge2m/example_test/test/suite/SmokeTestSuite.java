package com.github.jorge2m.example_test.test.suite;

import java.util.Arrays;
import java.util.HashMap;

import org.testng.xml.XmlSuite.ParallelMode;

import com.github.jorge2m.example_test.test.factory.SearchFactory;
import com.github.jorge2m.example_test.test.testcase.script.BuscarPatternPageObject;
import com.github.jorge2m.example_test.test.testcase.script.BuscarWithoutRefactor;
import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.domain.SuiteMaker;
import com.github.jorge2m.testmaker.domain.TestRunMaker;

public class SmokeTestSuite extends SuiteMaker {

	public SmokeTestSuite(InputParamsTM iParams) {
		super(iParams);
		setParameters(new HashMap<>());
		TestRunMaker testRun = TestRunMaker.from(
				iParams.getSuiteName(), 
				Arrays.asList(
					BuscarWithoutRefactor.class, 
					BuscarPatternPageObject.class, 
					SearchFactory.class));
		addTestRun(testRun);
		setParallelMode(ParallelMode.METHODS);
		setThreadCount(3);
	}
}
