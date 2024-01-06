package com.github.jorge2m.testmaker.service.webdriver.maker.plugins.chrome;

public class PluginHTML5AutoplayBlocker extends PluginChrome {
    
	//How obtain the .crx file? -> https://www.maketecheasier.com/download-save-chrome-extension/
    private static final String FILE_NAME = "autoplay_blocker_1.5.0_0.crx";
//    private static final String PageSetup = "chrome-extension://icppkihnmgkncknjlfkkjgfgoifkcgii/options.html";
//    private static final String XPathSelect = "//select[@id='default-mode']";
    
    @Override
    public String getCrxFileName() {
        return FILE_NAME;
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
