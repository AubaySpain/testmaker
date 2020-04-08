package com.github.jorge2m.example_test.test.testcase.pageobject;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.state;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.SeleniumUtils.getElementWeb;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Page2GoogleResults {

	private final WebDriver driver;
	
	public Page2GoogleResults(WebDriver driver) {
		this.driver = driver;
	}
	
	public boolean checkAreResults() {
		By byEntradaResultado = By.xpath("//h3[@class[contains(.,'LC20lb')]]");
		return (state(Visible, byEntradaResultado, driver).check());
	}
	
	public long getNumResults() {
		WebElement numResultsElem = getElementWeb(By.id("result-stats"), driver);
		return Utils.getLongFromElement(numResultsElem);
	}
}
