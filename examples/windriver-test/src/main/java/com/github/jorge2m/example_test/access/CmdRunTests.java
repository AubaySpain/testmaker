package com.github.jorge2m.example_test.access;

import com.github.jorge2m.example_test.access.CreatorSuiteRunTest.Apps;
import com.github.jorge2m.example_test.access.CreatorSuiteRunTest.Suites;
import com.github.jorge2m.testmaker.boundary.access.CmdLineMaker;
import com.github.jorge2m.testmaker.domain.CreatorSuiteRun;
import com.github.jorge2m.testmaker.domain.InputParamsBasic;

/**
 * Main class that implements access via Command Line
 * @author jorge.muñoz
 *
 */
public class CmdRunTests {

	public static void main(String[] args) throws Exception { 
		InputParamsBasic inputParams = new InputParamsBasic(Suites.class, Apps.class);
		CmdLineMaker cmdLineAccess = CmdLineMaker.from(args, inputParams);
		if (cmdLineAccess.checkOptionsValue().isOk()) {
			CreatorSuiteRun creatorSuiteRun = new CreatorSuiteRunTest(inputParams);
			creatorSuiteRun.execTestSuite(false);
		}
	}
}
