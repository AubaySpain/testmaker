package org.github.jorge2m.test;

import java.util.Arrays;
import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.domain.SuiteMaker;
import com.github.jorge2m.testmaker.domain.TestRunMaker;

public class SuiteSmokeTest extends SuiteMaker {

	//Creation of the TestRun indicating the name of the suite and the classes with the @Test's to execute.
	public SuiteSmokeTest(InputParamsTM iParams) {
		super(iParams);
		TestRunMaker testRun = TestRunMaker.from(
				iParams.getSuiteName(), 
				Arrays.asList(TestsGoogle.class));
		
		//Assignate the TestRun to the suite
		addTestRun(testRun);
	}
}
