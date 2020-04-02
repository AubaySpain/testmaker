package ${package}.test.suite;

import java.util.Arrays;
import java.util.HashMap;

import ${package}.test.testcase.script.Buscar;
import ${package}.test.testcase.script.BuscarWithoutRefactor;
import org.testng.xml.XmlSuite.ParallelMode;

import org.jorge2m.testmaker.domain.InputParamsTM;
import org.jorge2m.testmaker.domain.SuiteMaker;
import org.jorge2m.testmaker.domain.TestRunMaker;

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
