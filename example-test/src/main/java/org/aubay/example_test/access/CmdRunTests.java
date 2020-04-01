package org.aubay.example_test.access;

import org.aubay.example_test.access.datatmaker.Apps;
import org.aubay.example_test.access.datatmaker.Suites;
import org.aubay.testmaker.boundary.access.CmdLineMaker;
import org.aubay.testmaker.domain.CreatorSuiteRun;
import org.aubay.testmaker.domain.InputParamsBasic;

public class CmdRunTests {

	public static void main(String[] args) throws Exception { 
		InputParamsBasic inputParams = new InputParamsBasic(Suites.class, Apps.class);
		CmdLineMaker cmdLineAccess = CmdLineMaker.from(args, inputParams);
		if (cmdLineAccess.checkOptionsValue().isOk()) {
			CreatorSuiteRun creatorSuiteRun = CreatorSuiteRunTestGoogle.getNew(inputParams);
			creatorSuiteRun.execTestSuite(false);
		}
	}
}
