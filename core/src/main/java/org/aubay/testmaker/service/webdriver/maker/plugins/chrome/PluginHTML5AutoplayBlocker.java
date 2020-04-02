package org.jorge2m.testmaker.service.webdriver.maker.plugins.chrome;

import org.openqa.selenium.chrome.ChromeOptions;


public class PluginHTML5AutoplayBlocker extends PluginChrome {
    
	//How obtain the .crx file? -> https://www.maketecheasier.com/download-save-chrome-extension/
    private final String fileName = "autoplay_blocker_1.4.0_0.crx";
//    private static final String PageSetup = "chrome-extension://icppkihnmgkncknjlfkkjgfgoifkcgii/options.html";
//    private static final String XPathSelect = "//select[@id='default-mode']";
    
    @Override
    public void addPluginToChrome(ChromeOptions options) {
        super.addPluginToChrome(options, this.fileName);
    }
    
//    public static void disableAutoplay(WebDriver driver) {
//        driver.get(PageSetup);
//        new Select(driver.findElement(By.xpath(XPathSelect))).selectByVisibleText("Disable autoplay");
//    }
//    
//    public void disableNothing(WebDriver driver) {
//        driver.get(PageSetup);
//        new Select(driver.findElement(By.xpath(XPathSelect))).selectByVisibleText("Disable nothing");
//    }
}
