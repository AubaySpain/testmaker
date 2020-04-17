package org.github.jorge2m.test;

import com.github.jorge2m.testmaker.boundary.access.CmdLineMaker;
import com.github.jorge2m.testmaker.domain.CreatorSuiteRun;
import com.github.jorge2m.testmaker.domain.InputParamsBasic;

public class CmdLineAccess {

	//Defines the aplications that can be tested from that project
	public enum Apps { google; }
	
	//Defines the suites of test that can be executed from that project
	public enum Suites { SmokeTest; }
	
	public static void main(String[] args) throws Exception {
		
		//Creates the TestMaker class with the input parameters allowed
		InputParamsBasic inputParams = new InputParamsBasic(Suites.class, Apps.class);
		
		//Creates the via Command Line access based in the input parameters
		CmdLineMaker cmdLineAccess = CmdLineMaker.from(args, inputParams);
		
		//Checks the user input parameters
		if (cmdLineAccess.checkOptionsValue().isOk()) {
			
			//Defines the creator of TestSuites based in the user input parameters.
			CreatorSuiteRun creatorSuiteRun = new MyCreatorSuiteRun(inputParams);
			creatorSuiteRun.execTestSuite(false);
		}
	}
}
