package com.github.jorge2m.example_test.test.testcase.pageobject;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.SeleniumUtils.getElementWeb;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Page2BingResults {

	private final WebDriver driver;
	
	private final static String XPathNumResults = "//span[@class='sb_count']";
	
	public Page2BingResults(WebDriver driver) {
		this.driver = driver;
	}
	
	public long getNumResults() {
		WebElement numResultsElem = getElementWeb(By.xpath(XPathNumResults), driver);
		return Utils.getLongFromElement(numResultsElem);
	}
}
