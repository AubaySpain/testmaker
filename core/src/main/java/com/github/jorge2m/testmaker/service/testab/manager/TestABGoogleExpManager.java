package com.github.jorge2m.testmaker.service.testab.manager;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.testab.TestABGoogleExp;
import com.github.jorge2m.testmaker.service.testab.TestABactData;

public class TestABGoogleExpManager implements TestABmanager {
    
	public final static String nameCookieGoogleExperiments = "googleexperiments";
    final public TestABGoogleExp testAB;
	final Channel channelTest;
	final Enum<?> app;
	final WebDriver driver;
	
	public int varianteActivada = 0;
	
	public TestABGoogleExpManager(TestABGoogleExp testAB, Channel channel, Enum<?> app, WebDriver driver) {
		this.testAB = testAB;
		this.channelTest = channel;
		this.app = app;
		this.driver = driver;
	}
	
	public TestABGoogleExpManager(TestABGoogleExp testAB, int variante, Channel channel, Enum<?> app, WebDriver driver) {
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
		if (isActiveForChannelAndApp()) {
			String valueCookieRemovingTestAB = getValueCookieResetingAllTestABvariants(driver);
			if (valueCookieRemovingTestAB!=null) { 
				String testABvalueForVariant = testAB.getValueCookie(app) + "%3A" + varianteActivada + "%2C";
				String newValueCookie = valueCookieRemovingTestAB + testABvalueForVariant;
				Cookie actualCookie = driver.manage().getCookieNamed(nameCookieGoogleExperiments);
				Cookie newCookie = getClonedWithNewValue(actualCookie, newValueCookie);
				driver.manage().deleteCookieNamed(nameCookieGoogleExperiments);
				driver.manage().addCookie(newCookie);
			}
		}
	}
	
	public static void activateTestsAB(List<TestABactData> testsABtoActive, Channel channel, Enum<?> app, WebDriver driver) throws Exception {
		for (TestABactData testABtoActive : testsABtoActive) {
			TestABGoogleExp testABact = (TestABGoogleExp)testABtoActive.getTestAB();
			int varianteAct = testABtoActive.getvToActive();
			TestABGoogleExpManager testAB = new TestABGoogleExpManager(testABact, varianteAct, channel, app, driver);
			testAB.activateTestAB();
		}
	}
	
	@Override
	public int getVariant() {
		return (getVariantFromCookie(driver));
	}
	
	String getValueCookieResetingAllTestABvariants(WebDriver driver) {
		List<Integer> listVariantes = testAB.getVariantes();
		String valueCookie = getValueCookieGoogleExperiments(driver);
		if (valueCookie!=null) {
			for (Integer variante : listVariantes) {
				valueCookie = getValueCookieResetingTestABVariant(variante, valueCookie, driver);
			}
			return valueCookie;
		}
		return null;
	}
	
	private String getValueCookieResetingTestABVariant(int variante, String valueCookie, WebDriver driver) {
		if (existVariantInCookie(driver)) {
			String valueTestABvariant = getValueExpectedInCookie(testAB, variante);
			return (valueCookie.replaceAll(valueTestABvariant, ""));
		}
		
		return valueCookie;
	}
	
	
	private boolean existVariantInCookie(WebDriver driver) {
		return (getVariantFromCookie(driver)>=0 ? true : false);
	}
	
	/**
	 * @return id-integer variant if exists or -1 if doesn't exists
	 */
	public int getVariantFromCookie(WebDriver driver) {
		String valueCookie = getValueCookieGoogleExperiments(driver);
		if (valueCookie!=null) {
			List<Integer> listVariantes = testAB.getVariantes();
			for (Integer variante : listVariantes) {
				String valueTestABvariantExpected = getValueExpectedInCookie(testAB, variante);
				if (valueCookie.contains(valueTestABvariantExpected)) {
					return variante;
				}
			}
		}
		
		return -1;
	}
	
	String getValueExpectedInCookie(TestABGoogleExp testAB, int variante) {
		return (testAB.getValueCookie(app) + "%3A" + variante + "%2C");
	}

	String getValueCookieGoogleExperiments(WebDriver driver) {
		Cookie cookieGoogleExperiments = driver.manage().getCookieNamed(nameCookieGoogleExperiments);
		if (cookieGoogleExperiments!=null) {
			return (cookieGoogleExperiments.getValue());
		}
		
		return null;
	}
	
	@Override
	public void activateRandomTestABInBrowser() throws Exception {
		int numVariantes = testAB.getVariantes().size();
		int variante = RandomNumber(0, numVariantes-1);
		activateTestAB(variante);
	}
	
	private int RandomNumber(int minimo, int maximo) {
		Random random = new Random();
		return (random.nextInt(maximo - minimo + 1) + minimo);
	}
	
	private boolean isActiveForChannelAndApp() {
		return (
			testAB.getChannels().contains(channelTest) &&
			testAB.getApps().contains(app));
	}
}
