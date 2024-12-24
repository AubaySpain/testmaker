package com.github.jorge2m.testmaker.service.webdriver.maker;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.v130.network.Network;

import com.github.jorge2m.testmaker.conf.Log4jTM;
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
		
		Logger.getLogger("org.openqa.selenium.devtools.Connection").setLevel(Level.WARNING);
		
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
				"(MangoRobotest)";
		
	    int retries = 0;
	    boolean isUserAgentSet = false;
	    
	    while (retries < 3 && !isUserAgentSet) {
	        if (applyUserAgent(modifiedUserAgent)) {
	            isUserAgentSet = true;
	            Log4jTM.getLogger().info("User agent " + modifiedUserAgent + " set successfully on attempt " + (retries + 1));
	        } else {
	            retries++;
	            Log4jTM.getLogger().warn("User agent not correctly set, retrying... (attempt " + (retries + 1) + ")");
	            try {
	                Thread.sleep(1000);
	            } catch (InterruptedException e) {
	                Thread.currentThread().interrupt();
	                Log4jTM.getLogger().error("Interrupted during user agent retry wait", e);
	            }
	        }
	    }

	    if (!isUserAgentSet) {
	        Log4jTM.getLogger().error("Failed to set user agent after 3 attempts.");
	    }
    }
    
    private boolean applyUserAgent(String userAgent) {
        devTools.send(Network.setUserAgentOverride(userAgent, Optional.empty(), Optional.empty(), Optional.empty()));
        Log4jTM.getLogger().info("Attempting to set user agent: " + userAgent);
        
        String verifiedUserAgent = (String) ((JavascriptExecutor) this).executeScript("return navigator.userAgent;");
        
        return verifiedUserAgent.contains("MangoRobotest");
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
