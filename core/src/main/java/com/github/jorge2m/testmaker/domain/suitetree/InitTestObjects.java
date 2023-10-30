package com.github.jorge2m.testmaker.domain.suitetree;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.InitObject;

import com.github.jorge2m.testmaker.domain.InputParamsTM;

public class InitTestObjects {

	private final SuiteTM suiteParent;
	private final TestRunTM testRunParent;
	private InitObject makedObject;
	
	private WebDriver driver;
	
	public InitTestObjects(TestCaseTM testCase) {
		suiteParent = testCase.getSuiteParent();
		testRunParent = testCase.getTestRunParent();
	}
	
	public void make(InitObject initObject) {
		this.makedObject = initObject;
		switch (initObject) {
		case WebDriver:
			this.driver = getWebDriverForTestCase();
			break;
		case None:
			break;
		default:
			suiteParent.getLogger().warn("Init Test Object " + initObject.name() + " is not yet supported");
		}
	}
	
	public WebDriver getWebDriver() {
		return driver;
	}
	
	public void stopTestSignal() {
		if (makedObject==InitObject.WebDriver) {
			suiteParent.getPoolWebDrivers().quitWebDriver(driver, testRunParent);
		}
	}
	
	private WebDriver getWebDriverForTestCase() {
		var inputData = suiteParent.getInputParams();
		var driver = suiteParent
				.getPoolWebDrivers()
				.getWebDriver(
						inputData.getDriver(), 
						inputData.getChannel(), 
						testRunParent);
		initDriverContent(driver, inputData);
		return driver;
	}
	private void initDriverContent(WebDriver driver, InputParamsTM inputData) {
		try {
			driver.manage().deleteAllCookies();
			if (inputData.isExecInitUrl()) {
				driver.get(inputData.getUrlBase());
			}
		} 
		catch (Exception e) {
			suiteParent.getLogger().warn("Problem initializing Driver content ", e.getMessage());
		}
	}
	
}
