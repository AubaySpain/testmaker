package com.github.jorge2m.testmaker.service.webdriver.maker;

import java.util.Optional;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.v125.network.Network;

import com.github.jorge2m.testmaker.domain.suitetree.TestCaseBean;

import org.openqa.selenium.devtools.DevTools;

public class ChromeDriverTM extends ChromeDriver {

	private final String idExecSuite;
	private final String testCase;
	private final DevTools devTools;
	
	public ChromeDriverTM(ChromeOptions options, String idExecSuite, String testCase) {
		super(options);
		this.idExecSuite = idExecSuite;
		this.testCase = testCase;
		
		devTools = getDevTools();
		devTools.createSession();
		setUserAgent();
	}
	
	@Override
	public void get(String url) {
		super.get(url);
		//setCookie(url);
	}
	
    public void setUserAgent() {
        String currentUserAgent = (String) ((JavascriptExecutor)this).executeScript("return navigator.userAgent;");
		String modifiedUserAgent = 
				currentUserAgent + " " + 
				"Execution:" + idExecSuite + "; " +
				"Testcase:" + TestCaseBean.getNormalized(testCase) + "; " +
				" (MangoRobotest)";
		
		devTools.send(Network.setUserAgentOverride(modifiedUserAgent, Optional.empty(), Optional.empty(), Optional.empty()));
    }
    
	public void setCookie(String url) {
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        devTools.send(Network.setCookie(
        		"X-Robotest", 
        		TestCaseBean.getNormalized(testCase), 
        		Optional.of(url),
        		Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
        		Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
        		Optional.empty(), Optional.empty(), Optional.empty()
        ));
    }
	
}
