package com.github.jorge2m.testmaker.service.webdriver.maker;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.github.jorge2m.testmaker.conf.Log4jConfig;
import com.github.jorge2m.testmaker.domain.suitetree.TestRunTM;
import com.github.jorge2m.testmaker.service.webdriver.maker.FactoryWebdriverMaker.EmbebdedDriver;
import com.github.jorge2m.testmaker.service.webdriver.maker.brwstack.BrowserStackDesktop;
import com.github.jorge2m.testmaker.service.webdriver.maker.brwstack.BrowserStackMobil;


public class BrowserStackDriverMaker extends DriverMaker {
	
	private final TestRunTM testRun;
	String buildProject;
	String sessionName;
	String userBStack;
	String passBStack;
	
	public BrowserStackDriverMaker(TestRunTM testRun) {
		this.testRun = testRun;
		buildProject = 
			testRun.getSuite().getName() + 
			" (" + testRun.getSuiteParent().getIdExecution() + ")";
		sessionName = testRun.getName();
	}

	@Override
	public String getTypeDriver() {
		return EmbebdedDriver.browserstack.name();
	}
	
	@Override
	public void setupDriverVersion(String driverVersion) {
		//Nothing to do. The Driver creation is managed in the BrowserStack platform
	}
	
	@Override
	public WebDriver build() {
		WebDriver driver;
		switch (channel) {
		case movil: 
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
		BrowserStackMobil bsStackMobil = testRun.getBrowserStackMobil();
		if (bsStackMobil==null) {
			throw new RuntimeException("The data for connect with BrowserStack is not in the context");
		}

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("os", bsStackMobil.getSo());
		capabilities.setCapability("os_version", bsStackMobil.getSoVersion());
		capabilities.setCapability("device", bsStackMobil.getDevice());
		capabilities.setCapability("realMobile", bsStackMobil.getRealMobil());
		capabilities.setCapability("browserName", bsStackMobil.getBrowser());
		return (runBrowserStack(bsStackMobil.getUser(), bsStackMobil.getPassword(), capabilities));
	}

	private WebDriver createBStackDriverDesktop() {
		BrowserStackDesktop bsStackDesktop = testRun.getBrowserStackDesktop();
		if (bsStackDesktop==null) {
			throw new RuntimeException("The data for connect with BrowserStack is not in the context");
		}

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("os", bsStackDesktop.getSo());
		capabilities.setCapability("os_version", bsStackDesktop.getSoVersion());
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
			Log4jConfig.pLogger.error("Incorrect URL for BrowserStack " + urlBString, e);
			return null;
		}

		return (new RemoteWebDriver(urlBrowserStack, capabilities));
	}
}
