package com.github.jorge2m.example_test.access;

import java.util.Arrays;

import com.github.jorge2m.example_test.test.suite.SmokeTestSuite;
import com.github.jorge2m.testmaker.domain.CreatorSuiteRun;
import com.github.jorge2m.testmaker.domain.InputParamsBasic;
import com.github.jorge2m.testmaker.domain.SuiteMaker;

public class CreatorSuiteRunTest extends CreatorSuiteRun {
	
	public enum Apps { calculator }
	public enum Suites { SmokeTest }
	
	//Access Api-Rest (ServerRest.java)
	public CreatorSuiteRunTest() throws Exception {
		super();
	}
	//Access Command Line (CmdRunTests)
	public CreatorSuiteRunTest(InputParamsBasic inputParams) throws Exception {
		super(inputParams);
	}
	
	@Override
	public SuiteMaker getSuiteMaker() throws Exception {
		switch ((Suites)inputParams.getSuite()) {
		case SmokeTest:
			return (new SmokeTestSuite(inputParams));
		default:
			System.out.println("Suite Name not valid. Posible values: " + Arrays.asList(Suites.values()));
			return null;
		}
	}
}
