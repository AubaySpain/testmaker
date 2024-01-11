package com.github.jorge2m.testmaker.service.webdriver.maker;

import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;

public class FactoryWebdriverMaker {

	public enum EmbeddedDriver { 
		firefox(false, false), 
		firefoxhless(true, false), 
		chrome(false, true), 
		chromehless(true, true),
		edge(false, false),
		browserstack(false, false);
		
		boolean headless;
		boolean supportsVideo;
		private EmbeddedDriver(boolean headless, boolean supportsVideo) {
			this.headless = headless;
			this.supportsVideo = supportsVideo;
		}
		
		public boolean isHeadless() {
			return headless;
		}
		
		public boolean supportsVideo() {
			return supportsVideo;
		}
	}	

	public static DriverMaker make(TestCaseTM testCase) {
		var inputParams = testCase.getSuiteParent().getInputParams();
		String driver = inputParams.getDriver();
		var driverMakerUser = testCase.getTestRunParent().getDriverMakerUser();
		if (driverMakerUser!=null) {
			return driverMakerUser;
		}
		return getEmbebdedDriverMaker(driver, testCase);
	}
	
	private static DriverMaker getEmbebdedDriverMaker(String browser, TestCaseTM testCase) {
		var driverType = EmbeddedDriver.valueOf(browser);
		switch (driverType) {
		case firefox:
		case firefoxhless:
			return (new FirefoxdriverMaker(driverType.isHeadless()));
		case browserstack:
			return (new BrowserStackDriverMaker(testCase));
		case edge:
			return (new EdgedriverMaker());
		case chrome:
		case chromehless:
		default:
			boolean isHeadless = driverType.isHeadless();
			boolean isStartRecord = testCase.isStartRecordNeeded();
			return new ChromedriverMaker(isHeadless, isStartRecord, testCase.getTestPathDirectory());
		}
	}
	
}
