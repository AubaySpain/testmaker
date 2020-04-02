package ${package}.access;

import ${package}.access.datatmaker.Apps;
import ${package}.access.datatmaker.Suites;
import org.jorge2m.testmaker.boundary.access.CmdLineMaker;
import org.jorge2m.testmaker.domain.CreatorSuiteRun;
import org.jorge2m.testmaker.domain.InputParamsBasic;

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
