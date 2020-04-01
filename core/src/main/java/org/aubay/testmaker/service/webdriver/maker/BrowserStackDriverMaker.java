package org.aubay.testmaker.service.webdriver.maker;

import java.net.MalformedURLException;
import java.net.URL;

import org.aubay.testmaker.conf.Channel;
import org.aubay.testmaker.conf.Log4jConfig;
import org.aubay.testmaker.domain.suitetree.TestRunTM;
import org.aubay.testmaker.service.webdriver.maker.brwstack.BrowserStackDesktop;
import org.aubay.testmaker.service.webdriver.maker.brwstack.BrowserStackMobil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BrowserStackDriverMaker implements WebdriverMaker {
	
	private final TestRunTM testRun;
	String buildProject;
	String sessionName;
	String userBStack;
	String passBStack;
	Channel channel;
	boolean nettraffic = false;
	
	private BrowserStackDriverMaker(TestRunTM testRun) {
		this.testRun = testRun;
        buildProject = 
            testRun.getSuite().getName() + 
            " (" + testRun.getSuiteParent().getIdExecution() + ")";
        sessionName = testRun.getName();
	}
	
	public static BrowserStackDriverMaker getNew(TestRunTM testRun) {
		return (new BrowserStackDriverMaker(testRun));
	}
	
    @Override
	public WebdriverMaker setChannel(Channel channel) {
		this.channel = channel;
		return this;
	}
    
    @Override
	public WebdriverMaker setNettraffic(boolean nettraffic) {
    	this.nettraffic = nettraffic;
    	return this;
    }

    @Override
	public WebDriver build() {
    	WebDriver driver;
        switch (channel) {
        case movil_web: 
        	driver = createBStackDriverMobil();
        	break;
        case desktop:
        default:
        	driver = createBStackDriverDesktop();
        	driver.manage().window().maximize();
        }
        
        WebdriverMaker.deleteCookiesAndSetTimeouts(driver);
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
