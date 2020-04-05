package com.github.jorge2m.example_test.test.testcase.pageobject;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

public class ResultsGooglePage extends PageObject {

	private final static String XPathLinkSiguiente = "//a[@id='pnnext']";
	
	private ResultsGooglePage(WebDriver driver) {
		super(driver);
	}
	public static ResultsGooglePage getNew(WebDriver driver) {
		return new ResultsGooglePage(driver);
	}
	
	public boolean checkAreManyPages() {
		return (state(Visible, By.xpath(XPathLinkSiguiente)).check());
	}
	
}
