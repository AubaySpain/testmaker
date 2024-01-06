package com.github.jorge2m.testmaker.service.webdriver.maker.plugins.chrome;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

public abstract class PluginChrome { 

    public static final String FLODER_IN_RESOURCES = "pluginsBrowser"; 
    
    public enum TypePluginChrome { HTML5_AUTOPLAY_BLOCKER, MOVAVI_SCREEN_RECORDER }
    
    protected abstract String getCrxFileName();
    
    public void addPluginToChrome(ChromeOptions options) {
    	String pathPlugin = "/" + FLODER_IN_RESOURCES + "/" + getCrxFileName();
        try (var inputStream = getClass().getResourceAsStream(pathPlugin)) {
            File tmpFile = File.createTempFile("pluginChrome", "temp.crx");
            tmpFile.deleteOnExit();
            FileUtils.copyInputStreamToFile(inputStream, tmpFile);
            options.addExtensions(tmpFile);
        }
        catch (IOException e) {
        	Log4jTM.getLogger().warn("Problem creating chrome plugin with path " + pathPlugin, e);
        }
    }
    
    /**
     * idPlugin p.e. edbkhhpodjkbgenodomhfoldapghpddk
     * Nota: de momento no le podemos dar uso porque ChromDriver no permite la ejecución de ShortCuts
     */
    public static void setShortCut(String idPlugin, String shortCut, WebDriver driver) {
        //Save the current window
        //String windowHandle = driver.getWindowHandle();
        
        //Open the Extension's Page in another Tab
        //String titlePant = Thread.currentThread().getName() + "_Extensiones";
        //JavascriptExecutor js = (JavascriptExecutor)driver;
        //js.executeScript("window.open('chrome://extensions/', '" + titlePant + "');");
        //driver.switchTo().window(titlePant);
        driver.get("chrome://extensions-frame");
        
        //Wait 2 seconds for the page of Extensions
        String xpathLinkShortCuts = "//a[@class='extension-commands-config']";
        PageObjTM.state(State.VISIBLE, By.xpath(xpathLinkShortCuts), driver).wait(2).check();
        
        //Set the ShortCut Extension
        driver.findElement(By.xpath(xpathLinkShortCuts)).click();
        driver.findElement(By.xpath("//div[@id[contains(.,'" + idPlugin + "')]]//span[@class='command-shortcut-text']")).sendKeys(shortCut);
        driver.findElement(By.xpath("//button[@id[contains(.,'extension-commands')]]")).click();
        
        //Close Tab and Switch to the Original Page
        //js.executeScript("window.close('" + Thread.currentThread().getName() + "');");
        //driver.switchTo().window(windowHandle);
    }
    
}
