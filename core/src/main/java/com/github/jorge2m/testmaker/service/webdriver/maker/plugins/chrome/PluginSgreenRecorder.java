package com.github.jorge2m.testmaker.service.webdriver.maker.plugins.chrome;

import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.domain.suitetree.VideoRecorder;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PluginSgreenRecorder extends PluginChrome implements VideoRecorder {

    private static final String FILE_NAME = "sgreen_recorder_0_1_2.crx";
    
    private static final int RETRY_START = 5;
    private static final int RETRY_STOP = 3;
    
    private final PageObjTM page;
    private final WebDriver driver;
    
    public static PluginChrome makePlugin() {
    	return new PluginSgreenRecorder();
    }
    public static VideoRecorder makeRecorder(WebDriver driver) {
    	return new PluginSgreenRecorder(driver);
    }
    
    private PluginSgreenRecorder() {
    	page = null;
    	driver = null;
    }
    
    private PluginSgreenRecorder(WebDriver driver) {
    	this.page = new PageObjTM(driver);
    	this.driver = driver;
    }    
    
    @Override
    protected String getCrxFileName() {
        return FILE_NAME;
    }
    
	@Override
	public void start() {
		for (int i=0; i<RETRY_START; i++) {
			keyPressAltR();
			if (selectStartButton(1)) {
				return;
			} else {
				PageObjTM.waitMillis(500);
			}
		}
		Log4jTM.getLogger().warn("Sgreen Chrome plugin not started after " + RETRY_START + " attempts");
	}
    
    @Override
	public void stop() {
    	String xpDownload = "//a[@download]";
		for (int i=0; i<RETRY_STOP; i++) {
			keyPressAltRSynchronized();
			if (isSwitchToSgreenTab(5)) {
				page.state(PRESENT, xpDownload).wait(3).check();
				page.click(xpDownload).exec();
				PageObjTM.waitMillis(2000);
				return;
			} else {
				PageObjTM.waitMillis(500);
			}
		}
		Log4jTM.getLogger().warn("Problems stopping Sgreen Chrome plugin after " + RETRY_STOP + " attempts");
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
        // Abre una nueva pestaña
        ((JavascriptExecutor) driver).executeScript("window.open()");

        // Cambia el enfoque a la nueva pestaña
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }

        // Ejecuta el código de la extensión en la pestaña actual
        ejecutarCodigoExtension(driver);
	 }
	
    private static void ejecutarCodigoExtension(WebDriver driver) {
    	((ChromeDriver) driver).executeScript("chrome.runtime.sendMessage({ action: 'ejecutarHandleExtensionClick', tabId: 123 })");
    }	
	
//	 private void keyPressAltR() {
//         try {
//             Robot robot = new Robot();
//        	 bringChromeToFront();
//        	 PageObjTM.waitMillis(300);
//            
//        	 robot.keyPress(KeyEvent.VK_ALT);
//        	 robot.keyPress(KeyEvent.VK_P);
//        	 robot.keyRelease(KeyEvent.VK_P);
//        	 robot.keyRelease(KeyEvent.VK_ALT);
//         } catch (Exception e) {
//        	 Log4jTM.getLogger().warn("Problems executin ALT+R against Chrome Window", e);
//         }
//	 }
//
//	private void bringChromeToFront() {
//		var originalSize = driver.manage().window().getSize();
//	    minimize();
//		maximize();
//		driver.manage().window().setSize(originalSize);
//	}
	
	private void minimize() {
		try {
			driver.manage().window().minimize();
		} catch (Exception e) {}
	}
	
	private void maximize() {
		try {
			driver.manage().window().maximize();
		} catch (Exception e) {}
	}
	
}
