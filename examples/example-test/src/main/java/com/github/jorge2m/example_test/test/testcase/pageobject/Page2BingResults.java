package com.github.jorge2m.example_test.test.testcase.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

public class Page2BingResults extends PageObjTM {
	
	private final static String XPathNumResults = "//span[@class='sb_count']";
	private final static String XPathListResults = "//ol[@id='b_results']";
	
	public Page2BingResults(WebDriver driver) {
		super(driver);
	}
	
	public boolean checkIsResultsUntil(int maxSeconds) {
		return state(State.Visible, By.xpath(XPathListResults)).wait(maxSeconds).check();
	}
	
	public boolean checkIsNumResultsUntil(int maxSeconds) {
		return state(State.Visible, By.xpath(XPathNumResults)).wait(maxSeconds).check();
	}
	
	public long getNumResults() {
		WebElement numResultsElem = getElementWeb(By.xpath(XPathNumResults), driver);
		return Utils.getLongFromElement(numResultsElem);
	}
}
