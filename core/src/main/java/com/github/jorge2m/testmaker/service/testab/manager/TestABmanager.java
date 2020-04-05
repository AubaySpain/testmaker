package com.github.jorge2m.testmaker.service.testab.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.testab.TestAB;
import com.github.jorge2m.testmaker.service.testab.TestABGoogleExp;
import com.github.jorge2m.testmaker.service.testab.TestABOptimize;
import com.github.jorge2m.testmaker.service.testab.TestABactData;
import com.github.jorge2m.testmaker.service.testab.TestAB.TypeTestAB;

public interface TestABmanager {
	
	public void activateTestAB(int variante) throws Exception;
	public void activateTestAB() throws Exception;
	public void activateRandomTestABInBrowser() throws Exception;
	public int getVariant() throws UnsupportedOperationException;

	
	public static TestABmanager getInstance(TestAB testAB, Channel channel, Enum<?> app, WebDriver driver) {
		switch (testAB.getType()) {
		case GoogleExperiments:
			return (new TestABGoogleExpManager((TestABGoogleExp)testAB, channel, app, driver));
		case Optimize:
		default:
			return (new TestABOptimizeManager((TestABOptimize)testAB, channel, app, driver));
		}
	}

	public static void activateTestsAB(List<TestABactData> testsABtoActive, Channel channel, Enum<?> app, WebDriver driver) 
	throws Exception {
		List<TestABactData> listOptimize = filterByTestABtype(testsABtoActive, TypeTestAB.Optimize);
		if (listOptimize.size() > 0) {
			TestABOptimizeManager.activateTestsAB(listOptimize, channel, app, driver);
		}
		
		List<TestABactData> listExperiments = filterByTestABtype(testsABtoActive, TypeTestAB.GoogleExperiments);
		if (listExperiments.size() > 0) {
			TestABGoogleExpManager.activateTestsAB(listExperiments, channel, app, driver);
		}
	}

	static List<TestABactData> filterByTestABtype(List<TestABactData> listTestABs, TypeTestAB typeTestAB) {
		List<TestABactData> listToReturn = new ArrayList<>();
		for (TestABactData testAB : listTestABs) {
			if (testAB.getTestAB().getType()==typeTestAB) {
				listToReturn.add(testAB);
			}
		}
		
		return listToReturn;
	}

	default Cookie getClonedWithNewValue(Cookie actualCookie, String newValue) {
        Map<String,Object> jsonCookie = actualCookie.toJson();
        Cookie newCookie = 
        	new Cookie(
        		(String)jsonCookie.get("name"), 
        		newValue, 
        		(String)jsonCookie.get("domain"), 
        		(String)jsonCookie.get("path"), 
        		(Date)jsonCookie.get("expiry"), 
        		(boolean)jsonCookie.get("secure"), 
        		(boolean)jsonCookie.get("httpOnly"));
        return newCookie;
	}
}