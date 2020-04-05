package com.github.jorge2m.testmaker.service.webdriver.maker;

import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestRunTM;

public class FactoryWebdriverMaker {

	public enum WebDriverType { 
		firefox(false), 
		firefoxhless(true), 
		chrome(false), 
		chromehless(true),
		edge(false),
		safari(false),
		browserstack(false);
		
		boolean headless;
		private WebDriverType(boolean headless) {
			this.headless = headless;
		}
		
		public boolean isHeadless() {
			return headless;
		}
	}	

	public static WebdriverMaker make(WebDriverType webDriverType, TestRunTM testRun) {
		InputParamsTM inputParams = testRun.getSuiteParent().getInputParams();
		switch (webDriverType) {
		case firefox:
			return (FirefoxdriverMaker.getNew(webDriverType, inputParams.getGeckoDriverVersion()));
		case browserstack:
			return (BrowserStackDriverMaker.getNew(testRun));
		case edge:
			return (EdgedriverMaker.getNew());
		case chrome:
		default:
			return (ChromedriverMaker.getNew(webDriverType, inputParams.getChromeDriverVersion()));
		}
	}
}
