package org.github.jorge2m.test;

import java.util.Arrays;
import org.github.jorge2m.test.CmdLineAccess.Suites;
import com.github.jorge2m.testmaker.domain.CreatorSuiteRun;
import com.github.jorge2m.testmaker.domain.InputParamsBasic;
import com.github.jorge2m.testmaker.domain.SuiteMaker;

public class MyCreatorSuiteRun extends CreatorSuiteRun {

	//from CmdLineAccess
	public MyCreatorSuiteRun(InputParamsBasic inputParams) throws Exception {
		super(inputParams);
	}
	
	//from RestApiAccess
	public MyCreatorSuiteRun() throws Exception {
		super();
	}
	
	@Override
	public SuiteMaker getSuiteMaker() throws Exception {
		switch ((Suites)inputParams.getSuite()) {
		case SmokeTest:
			return (new SuiteSmokeTest(inputParams)); 
		default:
			//That shouldn't happen because the access via CommandLine/RestApi validates 
			//that the parameter 'suite' is a value of the Suites enum.
			System.out.println("Suite Name not valid. Posible values: " + Arrays.asList(Suites.values()));
			return null;
		}
	}
}
