package org.aubay.example_test.test.suite;

import java.util.Arrays;
import java.util.HashMap;

import org.aubay.example_test.test.testcase.script.Buscar;
import org.aubay.example_test.test.testcase.script.BuscarWithoutRefactor;
import org.testng.xml.XmlSuite.ParallelMode;

import org.aubay.testmaker.domain.InputParamsTM;
import org.aubay.testmaker.domain.SuiteMaker;
import org.aubay.testmaker.domain.TestRunMaker;

public class SmokeTestSuite extends SuiteMaker {

	public SmokeTestSuite(InputParamsTM iParams) {
		super(iParams);
		setParameters(new HashMap<>());
		TestRunMaker testRun = TestRunMaker.from(
				iParams.getSuiteName(), 
				Arrays.asList(BuscarWithoutRefactor.class/*Buscar.class*/));
		addTestRun(testRun);
		setParallelMode(ParallelMode.METHODS);
		setThreadCount(3);
	}
}
