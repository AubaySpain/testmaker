package com.github.jorge2m.example_test.test.testcase.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

public class Page2BingResults extends PageObjTM {
	
	private static final String XPATH_NUM_RESULTS = "//span[@class='sb_count']";
	private static final String XPATH_LIST_RESULTS = "//ol[@id='b_results']";
	
	public Page2BingResults(WebDriver driver) {
		super(driver);
	}
	
	public boolean checkIsResultsUntil(int maxSeconds) {
		return state(State.VISIBLE, By.xpath(XPATH_LIST_RESULTS)).wait(maxSeconds).check();
	}
	
	public boolean checkIsNumResultsUntil(int maxSeconds) {
		return state(State.VISIBLE, By.xpath(XPATH_NUM_RESULTS)).wait(maxSeconds).check();
	}
	
	public long getNumResults() {
		WebElement numResultsElem = getElementWeb(By.xpath(XPATH_NUM_RESULTS), driver);
		return Utils.getLongFromElement(numResultsElem);
	}
}
