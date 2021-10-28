package com.github.jorge2m.testmaker.service.testab.manager;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.InvalidCookieDomainException;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.Log4jTM;
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
			List<TestABactData> testsABtoActive = Arrays.asList(TestABactData.getNew(testAB, varianteActivada));
			activateTestsAB(testsABtoActive, channelTest, app, driver);
		}
	}
	
	public static void activateTestsAB(List<TestABactData> testsABtoActive, Channel channel, Enum<?> app, WebDriver driver) 
	throws Exception {
		String valueCookie_gaexp = getValueCookie_gaexp(driver);
		for (TestABactData testABtoActive : testsABtoActive) {
			TestABOptimize testAB = (TestABOptimize)testABtoActive.getTestAB();
			int vTestAB = testABtoActive.getvToActive();
			if (isActiveForChannelAndApp(testAB, channel, app)) {
				valueCookie_gaexp = getNewValueCookie_gaexp(valueCookie_gaexp, testAB, vTestAB);
			}
		}
		setCookie_gaexp(valueCookie_gaexp, driver);
	}
	
	private static String getValueCookie_gaexp(WebDriver driver) {
		Cookie cookie = driver.manage().getCookieNamed("_gaexp");
		if (cookie!=null) {
			return cookie.getValue();
		}
		return "";
	}
	
	private static String getNewValueCookie_gaexp(String actualValue, TestABOptimize testAB, int variant) {
		String dataCookieTestAB = testAB.getIdExperiment() + ".19012." + String.valueOf(variant);
		if ("".compareTo(actualValue)==0) {
			return ("GAX1.2." + dataCookieTestAB);
		} else {
	    	Pattern pattern = Pattern.compile(testAB.getIdExperiment() + "\\.[0-9]+\\.[0-9]+");
	        Matcher matcher = pattern.matcher(actualValue);
	        if (matcher.find()) {
	             return pattern.matcher(actualValue).replaceAll(dataCookieTestAB);
	        }
			return (actualValue + "!" +dataCookieTestAB);
		}
	}
	
	private static void setCookie_gaexp(String value, WebDriver driver) {
		Cookie cookie_gaexp = new Cookie("_gaexp", value);
		setCookie(cookie_gaexp, driver);
		
		Cookie cookie_browser = getCookie_Gaex_WithValue(value, driver);
		if (cookie_browser!=null &&
			cookie_browser.getDomain().indexOf(".")!=0) {
			Pattern pattern = Pattern.compile(".*(\\..+\\..+)");
	        Matcher matcher = pattern.matcher(cookie_browser.getDomain());
	        if (matcher.find()) {
	    		Cookie cookie_gaexp2 = new Cookie(
    				"_gaexp", value, 
    	    		matcher.group(1), 
    	    		"/", null, false, false);
	    		setCookie(cookie_gaexp2, driver);
	        }
		}
	}
	
	private static Cookie getCookie_Gaex_WithValue(String value, WebDriver driver) {
		for (Cookie cookie : driver.manage().getCookies()) {
			if ("_gaexp".compareTo(cookie.getName())==0 &&
				value.compareTo(cookie.getValue())==0) {
				return cookie;
			}
		}
		return null;
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
			Log4jTM.getLogger().warn("Problem activating TestAB via add of Cookie " + cookie.getName(), e);
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
