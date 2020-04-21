package com.github.jorge2m.testmaker.service.webdriver.pool;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.maker.FactoryWebdriverMaker.EmbebdedDriver;

public class StoredWebDrv {

    public enum stateWd {busy, free}
    
    private String driver = EmbebdedDriver.chrome.name();
    private String moreDataWdrv = "";
    private WebDriver webdriver;
    private stateWd state = stateWd.free;

    public StoredWebDrv(WebDriver webdriver, stateWd state, String driver, String moreDataWdrv) {
        this.webdriver = webdriver;
        this.state = state;
        this.driver = driver;
        this.moreDataWdrv = moreDataWdrv;
    }
    
    public String getDriver() {
        return this.driver;
    }
    
    public String getMoreDataWdrv() {
        return this.moreDataWdrv;
    }
    
    public void setMoreDataWdrv(String moreDataWdrv) {
        this.moreDataWdrv = moreDataWdrv;
    }
    
    public WebDriver getWebDriver() {
        return this.webdriver;
    }
    
    public void setWebDriver(WebDriver driver) {
        this.webdriver = driver;
    }

    public stateWd getState() {
        return (this.state);
    }
    
    public void setState(stateWd state) {
        this.state = state;
    }
    
    public boolean isFree() {
        if (this.state == stateWd.free) {
            return true;
        }
        return false;
    }
    
    public boolean isBusy() {
        if (this.state == stateWd.busy) {
            return true;
        }
        return false;
    }

    public void markAsBusy() {
        this.setState(stateWd.busy);
    }
    
    public void markAsFree() {
        this.setState(stateWd.free);
    }
}


