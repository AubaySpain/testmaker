package com.github.jorge2m.testmaker.service.webdriver.maker;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.github.jorge2m.testmaker.service.webdriver.maker.FactoryWebdriverMaker.EmbeddedDriver;
import com.github.jorge2m.testmaker.service.webdriver.maker.brwstack.BrowserStackDesktopI;
import com.github.jorge2m.testmaker.service.webdriver.maker.brwstack.BrowserStackMobilI;
import com.github.jorge2m.testmaker.service.webdriver.maker.brwstack.BrowserStackDataDesktop;
import com.github.jorge2m.testmaker.service.webdriver.maker.brwstack.BrowserStackDataMobil;

public class BrowserStackDriverMaker extends DriverMaker {
	
	private final InputParamsTM inputParams;
	String buildProject;
	String sessionName;
	String userBStack;
	String passBStack;
	
	public BrowserStackDriverMaker(TestCaseTM testCase) {
		this.inputParams = testCase.getSuiteParent().getInputParams();
		buildProject = 
			testCase.getSuiteParent().getName() + 
			" (" + testCase.getSuiteParent().getIdExecution() + ")";
		sessionName = testCase.getTestRunParent().getName();
		setChannel(inputParams.getChannel());
	}

	@Override
	public String getTypeDriver() {
		return EmbeddedDriver.browserstack.name();
	}
	
	@Override
	public WebDriver build() {
		WebDriver driver;
		switch (channel) {
		case mobile: 
		case tablet:
			driver = createBStackDriverMobil();
			break;
		case desktop:
		default:
			driver = createBStackDriverDesktop();
			driver.manage().window().maximize();
		}

		deleteCookiesAndSetTimeouts(driver);
		return driver;
	}

	private WebDriver createBStackDriverMobil() {
//		BrowserStackMobil bsStackMobil = testRun.getBrowserStackMobil();
		BrowserStackMobilI bsStackMobil = new BrowserStackDataMobil(inputParams);
//		if (bsStackMobil==null) {
//			throw new RuntimeException("The data for connect with BrowserStack is not in the context");
//		}

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("os", bsStackMobil.getOs());
		capabilities.setCapability("os_version", bsStackMobil.getOsVersion());
		capabilities.setCapability("device", bsStackMobil.getDevice());
		capabilities.setCapability("realMobile", bsStackMobil.getRealMobile());
		capabilities.setCapability("browserName", bsStackMobil.getBrowser());
		return (runBrowserStack(bsStackMobil.getUser(), bsStackMobil.getPassword(), capabilities));
	}

	private WebDriver createBStackDriverDesktop() {
//		BrowserStackDesktopI bsStackDesktop = testRun.getBrowserStackDesktop();
		BrowserStackDesktopI bsStackDesktop = new BrowserStackDataDesktop(inputParams);
//		if (bsStackDesktop==null) {
//			throw new RuntimeException("The data for connect with BrowserStack is not in the context");
//		}

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("os", bsStackDesktop.getOs());
		capabilities.setCapability("os_version", bsStackDesktop.getOsVersion());
		capabilities.setCapability("browser", bsStackDesktop.getBrowser());
		capabilities.setCapability("browser_version", bsStackDesktop.getBrowserVersion());
		capabilities.setCapability("resolution", bsStackDesktop.getResolution());
		capabilities.setCapability("browserstack.use_w3c", true);
		return (runBrowserStack(bsStackDesktop.getUser(), bsStackDesktop.getPassword(), capabilities));
	}

	private WebDriver runBrowserStack(String user, String password, DesiredCapabilities capabilities) {
		capabilities.setCapability("build", buildProject);
		capabilities.setCapability("name", sessionName);
		capabilities.setCapability("browserstack.debug", "false");
		capabilities.setCapability("browserstack.local", "false");
		URL urlBrowserStack;
		String urlBString = "http://"+user+":"+password+"@hub-cloud.browserstack.com/wd/hub";
		try {
			urlBrowserStack = new URL(urlBString);
		}
		catch (MalformedURLException e) {
			Log4jTM.getLogger().error("Incorrect URL for BrowserStack " + urlBString, e);
			return null;
		}

		return (new RemoteWebDriver(urlBrowserStack, capabilities));
	}
}
