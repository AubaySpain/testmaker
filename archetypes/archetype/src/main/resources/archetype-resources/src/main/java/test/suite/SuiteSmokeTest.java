package ${package}.test.suite;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.xml.XmlSuite.ParallelMode;

import ${package}.test.factory.SearchFactory;
import ${package}.test.testcase.script.TestsGoogle;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.domain.SuiteMaker;
import com.github.jorge2m.testmaker.domain.TestRunMaker;
import com.github.jorge2m.testmaker.service.webdriver.maker.DriverMaker;

public class SuiteSmokeTest extends SuiteMaker {

	public SuiteSmokeTest(InputParamsTM iParams) {
		super(iParams);
		setParameters(new HashMap<>());
		TestRunMaker testRun = TestRunMaker.from(
				iParams.getSuiteName(), 
				Arrays.asList(TestsGoogle.class, SearchFactory.class));
		//testRun.setDriverMaker(iParams.getDriver(), makeListDrivers()); 
		addTestRun(testRun);
		setParallelMode(ParallelMode.METHODS);
		setThreadCount(3);
	}
	
	
//	private static List<DriverMaker> makeListDrivers() {
//		return Arrays.asList(
//			new ChromeDriverMaker(),
//			new FirefoxDriverMaker()
//		);
//	}
//	private static class ChromeDriverMaker extends DriverMaker {
//		public String getTypeDriver() {
//			return "michrome";
//		}
//		public void setupDriverVersion(String driverVersion) {
//			ChromeDriverManager.chromedriver().version(driverVersion).setup();
//		}
//		public WebDriver build() {
//			return new ChromeDriver();
//		}
//	}
//	private static class FirefoxDriverMaker extends DriverMaker {
//		public String getTypeDriver() {
//			return "mifirefox";
//		}
//		public void setupDriverVersion(String driverVersion) {
//			FirefoxDriverManager.firefoxdriver().version(driverVersion).setup();
//		}
//		public WebDriver build() {
//			return new FirefoxDriver();
//		}
//	}
}
