package com.github.jorge2m.testmaker.service.testab.manager;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.InvalidCookieDomainException;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.Log4jConfig;
import com.github.jorge2m.testmaker.service.testab.TestABOptimize;
import com.github.jorge2m.testmaker.service.testab.TestABactData;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.SeleniumUtils;

public class TestABOptimizeManager implements TestABmanager {
	
	final public TestABOptimize testAB;
	final Channel channelTest;
	final Enum<?> app;
	final WebDriver driver;
	public int varianteActivada = 0;
	
	public TestABOptimizeManager(TestABOptimize testAB, Channel channel, Enum<?> app, WebDriver driver) {
		this.testAB = testAB;
		this.channelTest = channel;
		this.app = app;
		this.driver = driver;
	}
	
	public TestABOptimizeManager(TestABOptimize testAB, int variante, Channel channel, Enum<?> app, WebDriver driver) {
		this.testAB = testAB;
		this.varianteActivada = variante;
		this.channelTest = channel;
		this.app = app;
		this.driver = driver;
	}
	
	@Override
	public void activateTestAB(int variante) throws Exception {
        this.varianteActivada = variante;
        activateTestAB();
	}

	@Override
	public void activateTestAB() throws Exception {
		if (isActiveForChannelAndApp(testAB, channelTest, app)) {
			setCookieGtm_auth(testAB, driver);
			setCookieGtm_preview(testAB, driver);
			setCookieGtm_experiment(getVariantInGtm_experiment(testAB, varianteActivada), driver);
		}
	}
	
	public static void activateTestsAB(List<TestABactData> testsABtoActive, Channel channel, Enum<?> app, WebDriver driver) 
	throws Exception {
		String valueCookie = "";
		for (TestABactData testABtoActive : testsABtoActive) {
			TestABOptimize testAB = (TestABOptimize)testABtoActive.getTestAB();
			int vTestAB = testABtoActive.getvToActive();
			if (isActiveForChannelAndApp(testAB, channel, app)) {
				if ("".compareTo(valueCookie)==0) {
					setCookieGtm_auth(testAB, driver);
					setCookieGtm_preview(testAB, driver);
					valueCookie+=getVariantInGtm_experiment(testAB, vTestAB);
				} else {
					valueCookie=valueCookie+"&"+testAB.getIdExperiment() + vTestAB;
				}
			}
		}
		
		setCookieGtm_experiment(valueCookie, driver);
	}
	
	private static String getVariantInGtm_experiment(TestABOptimize testAB, int variante) {
		return (testAB.getGroup() + "=" + testAB.getIdExperiment() + variante);
	}
	
	private static void setCookieGtm_auth(TestABOptimize testAB, WebDriver driver) {
		String gtm_auth = "gtm_auth";
		Cookie cookieGtm_auth = new Cookie(
			gtm_auth, 
    		testAB.getGroup() + "=" + testAB.getAuth(), 
    		"www.google-analytics.com", 
    		"/gtm/",
    		null, 
    		false, 
    		false
    	);
		setCookie(cookieGtm_auth, driver);
	}
	
	private static void setCookieGtm_preview(TestABOptimize testAB, WebDriver driver) {
		String gtm_preview = "gtm_preview";
		Cookie cookieGtm_preview = new Cookie(
			gtm_preview, 
    		testAB.getGroup() + "=" + testAB.getPreview(), 
    		"www.google-analytics.com",
    		"/gtm/",
    		null, 
    		false, 
    		false
    	);
		setCookie(cookieGtm_preview, driver);
	}
	
	private static void setCookieGtm_experiment(String value, WebDriver driver) {
		String gtm_experiment = "gtm_experiment";
		Cookie cookieGtm_experiment = new Cookie(
			gtm_experiment, 
			value, 
    		"www.google-analytics.com", 
    		"/gtm/", 
    		null, 
    		false, 
    		false
    	);
		setCookie(cookieGtm_experiment, driver);
	}
	
	private static void setCookie(Cookie cookie, WebDriver driver) {
		try {
			driver.manage().addCookie(cookie);
		}
		catch (InvalidCookieDomainException e) {
			setCookieAfterInvalidDomainException(cookie, driver);
		}
	}
	
	private static void setCookieAfterInvalidDomainException(Cookie cookie, WebDriver driver) {
		String windowHandlerToReturn = driver.getWindowHandle();
		try {
			String titleTab = "SetCookie " + cookie.getName() + "_" + cookie.getValue();
			SeleniumUtils.loadUrlInAnotherMinimumTab("https://" + cookie.getDomain() + cookie.getPath(), titleTab, driver);
			driver.manage().addCookie(cookie);
			SeleniumUtils.closeTabByTitleAndReturnToWidow(titleTab, windowHandlerToReturn, driver);
		}
		catch (Exception e) {
			Log4jConfig.pLogger.warn("Problem activating TestAB via add of Cookie " + cookie.getName(), e);
		}
	}

	@Override
	public void activateRandomTestABInBrowser() throws Exception {
		int numVariantes = testAB.getVariantes().size();
		int variante = RandomNumber(0, numVariantes-1);
		activateTestAB(variante);
	}
	
	@Override
	public int getVariant() throws UnsupportedOperationException {
        throw new UnsupportedOperationException(
        	String.format("%s is of type %s that does not support this method yet.",
        	testAB,
            testAB.getType())
        );
	}	
	
	private int RandomNumber(int minimo, int maximo) {
		Random random = new Random();
		return (random.nextInt(maximo - minimo + 1) + minimo);
	}
	
	private static boolean isActiveForChannelAndApp(TestABOptimize testAB, Channel channel, Enum<?> app) {
		return (
			testAB.getChannels().contains(channel) &&
			testAB.getApps().contains(app));
	}
}
