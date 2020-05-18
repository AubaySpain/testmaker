package com.github.jorge2m.example_test.test.testcase.pageobject;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

public class Page2GoogleResults extends PageObjTM {
	
	public Page2GoogleResults(WebDriver driver) {
		super(driver);
	}
	
	public boolean checkAreResultsUntil(int maxSeconds) {
		By byEntradaResultado = By.xpath("//h3[@class[contains(.,'LC20lb')]]");
		return (state(Visible, byEntradaResultado).wait(maxSeconds).check());
	}
	
	public long getNumResults() {
		WebElement numResultsElem = getElementWeb(By.id("result-stats"), driver);
		return Utils.getLongFromElement(numResultsElem);
	}
}
