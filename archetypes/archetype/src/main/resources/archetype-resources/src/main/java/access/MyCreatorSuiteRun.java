package ${package}.access;

import java.util.Arrays;

import ${package}.access.datatmaker.Suites;
import ${package}.test.suite.SuiteSmokeTest;
import com.github.jorge2m.testmaker.domain.CreatorSuiteRun;
import com.github.jorge2m.testmaker.domain.InputParamsBasic;
import com.github.jorge2m.testmaker.domain.SuiteMaker;

public class MyCreatorSuiteRun extends CreatorSuiteRun {

	private final String ChromeDriverVersionDefault = "85.0.4183.83";
	private final String GeckoDriverVersionDefault = "0.27.0";
	
	private MyCreatorSuiteRun() throws Exception {
		super();
	}
	private MyCreatorSuiteRun(InputParamsBasic inputParams) throws Exception {
		super(inputParams);
	}
	public static MyCreatorSuiteRun getNew() throws Exception {
		return new MyCreatorSuiteRun();
	}
	public static MyCreatorSuiteRun getNew(InputParamsBasic inputParams) throws Exception {
		return new MyCreatorSuiteRun(inputParams);
	}
	
	@Override
	public SuiteMaker getSuiteMaker() throws Exception {
		setWebDriverVersion();
		try {
			switch ((Suites)inputParams.getSuite()) {
			case SmokeTest:
				return (new SuiteSmokeTest(inputParams));
			case OtherSuite:  
			default:
			}
		}
		catch (IllegalArgumentException e) {
			System.out.println("Suite Name not valid. Posible values: " + Arrays.asList(Suites.values()));
		}

		return null;
	}
	
	private void setWebDriverVersion() {
		if (inputParams.getDriverVersion()==null) {
			switch (inputParams.getDriver()) {
			case "firefox":
				inputParams.setDriverVersion(GeckoDriverVersionDefault);
				break;
			case "chrome":
				inputParams.setDriverVersion(ChromeDriverVersionDefault);
				break;
			}
		}
	}
}
