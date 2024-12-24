package com.github.jorge2m.testmaker.service.webdriver.pageobject;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Log4jTM;

public class ScreenVisibility {

	private final WebDriver driver;
	
	public ScreenVisibility(WebDriver driver) {
		this.driver = driver;
	}
	
	public boolean isVisibleInScreen(WebElement element) {
		try {
			return isVisibleCenterInScreen(element);
		} catch (Exception e) {
			Log4jTM.getLogger().warn("Problem checking visibility of element in screen ", e.getCause());
			return false;
		}
	}
	
	public boolean isVisibleCenterInScreen(WebElement element) {
		var centerX = element.getRect().getWidth() / 2;
		var centerY = element.getRect().getHeight() / 2; 
		var cornerX = element.getRect().getWidth() / 4;
		var cornerY = element.getRect().getWidth() / 4;
		return 
			isVisibleInScreen(element, centerX, centerY) ||
			isVisibleInScreen(element, cornerX, cornerY);		
	}
	
	public boolean isVisibleInScreen(WebElement element, int pointX, int pointY) {
		if (!element.isDisplayed()) {
			return false;
		}
		return (Boolean) ((JavascriptExecutor) driver).executeScript(
            "var elem = arguments[0], " +
            "    box = elem.getBoundingClientRect(), " +
            "    cx = box.left + " + pointX + ", " +
            "    cy = box.top + " + pointY + ", " +
            "    e = document.elementFromPoint(cx, cy); " +
            "for (; e; e = e.parentElement) { " +
            "    if (e === elem) " +
            "        return true; " +
            "} " +
            "return false;",
            element);
	}	

}
