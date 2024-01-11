package com.github.jorge2m.testmaker.service.webdriver.maker.plugins.chrome;

import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.github.jorge2m.testmaker.domain.suitetree.VideoRecorder;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.testreports.testcasestore.TestCaseEvidence;
import com.github.jorge2m.testmaker.testreports.testcasestore.TestCaseEvidenceStorerBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PluginSgreenRecorder extends PluginChrome implements VideoRecorder {

    private static final String FILE_NAME = "sgreen_recorder_0_1_2.crx";
    
    private static final int RETRY_START = 5;
    private static final int RETRY_STOP = 3;
    
    private final PageObjTM page;
    private final TestCaseTM testcase;
    private final WebDriver driver;
    
    public static PluginChrome makePlugin() {
    	return new PluginSgreenRecorder();
    }
    public static VideoRecorder makeRecorder(TestCaseTM testcase) {
    	return new PluginSgreenRecorder(testcase);
    }
    
    private PluginSgreenRecorder() {
    	testcase = null;
    	page = null;
    	driver = null;
    }
    
    private PluginSgreenRecorder(TestCaseTM testcase) {
    	this.testcase = testcase;
    	this.driver = testcase.getDriver();
    	this.page = new PageObjTM(driver);
    }    
    
    @Override
    protected String getCrxFileName() {
        return FILE_NAME;
    }
    
	@Override
	public void start() {
		Log4jTM.getLogger().info("Starting Sgreen Chrome plugin...");
		for (int i=0; i<RETRY_START; i++) {
			keyPressAltR();
			if (selectStartButton(1)) {
				Log4jTM.getLogger().info("Started Sgreen Chrome plugin!");
				return;
			} else {
				PageObjTM.waitMillis(500);
			}
		}
		Log4jTM.getLogger().warn("Sgreen Chrome plugin not started after " + RETRY_START + " attempts");
	}
    
    @Override
	public void stop() {
    	Log4jTM.getLogger().info("Saving testcase video recorded with Sgreen Chrome...");
		for (int i=0; i<RETRY_STOP; i++) {
			keyPressAltRSynchronized();
			if (isSwitchToSgreenTab(5)) {
				if (downloadVideoAndCheck()) {
					Log4jTM.getLogger().info("Saved testcase video recorded with Sgreen Chrome!");
				} else {
					Log4jTM.getLogger().warn("Not saved testcase video recorded with Sgreen Chrome!");
				}
				return;
			} else {
				PageObjTM.waitMillis(500);
			}
		}
		Log4jTM.getLogger().warn("Problems saving video after " + RETRY_STOP + " attempts");
	}
	
    private boolean downloadVideoAndCheck() {
    	for (int i=0; i<5; i++) {
    		clickDownload(3);
    		if (TestCaseEvidenceStorerBase.existFileEvidence(TestCaseEvidence.VIDEO, testcase)) {
    			return true;
    		}
    		Log4jTM.getLogger().info("Not saved video downloaded...");    		
    		PageObjTM.waitMillis(3000);
    	}
    	return false;
    }
    
    private void clickDownload(int seconsWait) {
    	String xpDownload = "//a[@download]";
    	page.state(PRESENT, xpDownload).wait(seconsWait).check();
    	page.click(xpDownload).exec();
    }
    
	private boolean isSwitchToSgreenTab(int seconds) {
		for (int i=0; i<seconds; i++) {
			if (isSwitchToSgreenTab()) {
				return true;
			}
			PageObjTM.waitMillis(1000);
		}
		return false;
	}
	
	private boolean isSwitchToSgreenTab() {
        String ventanaPrincipal = driver.getWindowHandle();
        for (String ventana : driver.getWindowHandles()) {
            driver.switchTo().window(ventana);
            String tituloActual = driver.getTitle();
            if (tituloActual.contains("Sgreen")) {
                return true;
            }
        }

        driver.switchTo().window(ventanaPrincipal);
        return false;
	}
	
	private boolean selectStartButton(int seconds) {
		By byShadowHost = By.cssSelector("#sgreen_recorder");
		if (!page.state(PRESENT, byShadowHost).wait(seconds).check()) {
			return false;
		}
		
		var shadowRoot = page.getElement(byShadowHost).getShadowRoot();
		By buttonStartBy = By.cssSelector("button#start.select-none");
		
		if (!isPresentButtonStart(shadowRoot, buttonStartBy, seconds)) {
			return false;
		}
		
		shadowRoot.findElement(buttonStartBy).click();
		return true;
	}

	private boolean isPresentButtonStart(SearchContext shadowRoot, By buttonStartBy, int seconds) {
		for (int i=0; i<seconds; i++) {
			try {
				shadowRoot.findElement(buttonStartBy);
				return true;
			} catch (NoSuchElementException e) {
				PageObjTM.waitMillis(1000);
			}
		}
		return false;
	}
	
	private synchronized void keyPressAltRSynchronized() {
		keyPressAltR();
	}
	
	 private void keyPressAltR() {
         try {
             Robot robot = new Robot();
        	 bringChromeToFront();
        	 PageObjTM.waitMillis(300);
            
        	 robot.keyPress(KeyEvent.VK_ALT);
        	 robot.keyPress(KeyEvent.VK_P);
        	 robot.keyRelease(KeyEvent.VK_P);
        	 robot.keyRelease(KeyEvent.VK_ALT);
         } catch (Exception e) {
        	 Log4jTM.getLogger().warn("Problems executin ALT+R against Chrome Window", e);
         }
	 }
	 
	 private void bringChromeToFront() {
		 String windowHandle = driver.getWindowHandle();
		 driver.switchTo().newWindow(WindowType.TAB);
		 driver.close();
		 driver.switchTo().window(windowHandle);
	 }

}
