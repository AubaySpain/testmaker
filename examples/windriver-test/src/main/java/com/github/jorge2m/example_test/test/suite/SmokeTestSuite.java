package com.github.jorge2m.example_test.test.suite;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.github.jorge2m.example_test.test.testcase.script.Calculator;
import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.domain.SuiteMaker;
import com.github.jorge2m.testmaker.domain.TestRunMaker;
import com.github.jorge2m.testmaker.service.webdriver.maker.DriverMaker;

import io.appium.java_client.windows.WindowsDriver;

//Note: the WinAppDriver doesn't support the parallelization of tests against the same application. If you want to 
//paralellize that type of tests you must use the execution distributed in many machines that provides TestMaker
public class SmokeTestSuite extends SuiteMaker {

	public SmokeTestSuite(InputParamsTM iParams) {
		super(iParams);
		TestRunMaker testRun = TestRunMaker.from(
				iParams.getSuiteName(), 
				Arrays.asList(Calculator.class));
		testRun.setDriverMaker(new WinAppDriverMaker());
		addTestRun(testRun);
	}
	
	//TestMaker doesn't manage the automatic installation of the WinAppDriver. Then you need install in your machine
	//You can find the installation steps in https://github.com/microsoft/WinAppDriver
	public static class WinAppDriverMaker extends DriverMaker {
		@Override
		public String getTypeDriver() {
			return "winappdriver";
		}
		
		@Override
		public WebDriver build() {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("app", "Microsoft.WindowsCalculator_8wekyb3d8bbwe!App");
			try {
				var winDriver = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
				return winDriver;
			}
			catch(MalformedURLException e){
				e.printStackTrace();
				return null;
			}
		}
	}
}
