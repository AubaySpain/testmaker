package org.github.jorge2m.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class TestsGoogle {
	
	@Test (
		groups={"Canal:desktop_App:google"},
		description="Type \"Hello World!\" and Check the result")
	public void BUS001_CheckGoogleMoreResults() {
		WebDriver driver = TestCaseTM.getDriverTestCase();
		searchInGoogle("Hello World!", driver);
	}
	
	@Step (
		description="Input the text <b>#{textToSearch}</b> and click button \"Search with Google\"",
		expected="At leas one entry with the text #{textToSearch} appears",
		saveImagePage=SaveWhen.Always,
		saveHtmlPage=SaveWhen.Always)
	public void searchInGoogle(String textToSearch, WebDriver driver) {
		//Input Text to Search
		By byInputSearch = By.xpath("//input[@name='q']");
		driver.findElement(byInputSearch).sendKeys(textToSearch);
		
		//Click Search Button
		By buttonSearchBy = By.xpath("//input[@class='gNO89b']");
		click(buttonSearchBy, driver).exec();
		checkTextSearched(textToSearch, 2, driver);
	}
	
	@Validation (
		description="Appears at least an entry that contains the text #{textSearched}",
		level=State.Defect)
	public boolean checkTextSearched(String textSearched, int maxWait, WebDriver driver) {
		By entryWithTextBy = By.xpath("//h3[text()[contains(.,'" + textSearched + "')]]");
		return state(Visible, entryWithTextBy, driver).wait(maxWait).check();
	}
}
