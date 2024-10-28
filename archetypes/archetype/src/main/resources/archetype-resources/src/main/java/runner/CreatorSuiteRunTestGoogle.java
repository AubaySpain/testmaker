package ${package}.runner;

import ${package}.runner.datamaker.Suites;
import ${package}.test.suite.FactorySuite;
import ${package}.test.suite.SmokeTestSuite;
import com.github.jorge2m.testmaker.domain.CreatorSuiteRun;
import com.github.jorge2m.testmaker.domain.InputParamsBasic;
import com.github.jorge2m.testmaker.domain.SuiteMaker;

public class CreatorSuiteRunTestGoogle extends CreatorSuiteRun {

	public CreatorSuiteRunTestGoogle() throws Exception {
		super();
	}
	
	public CreatorSuiteRunTestGoogle(InputParamsBasic inputParams) throws Exception {
		super(inputParams);
	}
	
	@Override
	public SuiteMaker getSuiteMaker() throws Exception {
		if (isSuite(Suites.FactorySearch)) {
			return new FactorySuite(inputParams);
		}
		return new SmokeTestSuite(inputParams);
	}

}
