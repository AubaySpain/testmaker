package ${package}.runner;

import ${package}.runner.datamaker.Apps;
import ${package}.runner.datamaker.Suites;
import com.github.jorge2m.testmaker.boundary.access.CmdLineMaker;
import com.github.jorge2m.testmaker.domain.InputParamsBasic;

/**
 * Main class that implements access via Command Line
 * @author jorge.muñoz
 *
 */
public class TestRunner {

	public static void main(String[] args) throws Exception { 
		var inputParams = new InputParamsBasic(Suites.class, Apps.class);
		var cmdLineAccess = CmdLineMaker.from(args, inputParams);
		if (cmdLineAccess.checkOptionsValue().isOk()) {
			new CreatorSuiteRunTestGoogle(inputParams).execTestSuite(false);
		}
	}

}
