package com.github.jorge2m.example_test.test.testcase.script;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;

import io.appium.java_client.windows.WindowsDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.SeleniumUtils.getElementWeb;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


//Important: this script is implemented against a spanish version of the Windows Calculator application. 
//But this test includes comments in the code for the quick adaptation to a English Version.
public class Calculator {
	
	@Test (
		groups={"Canal:desktop_App:calculator"}, 
		description="Checks the Add operation in the Calculator Windows App")
	public void CAL001_CheckAddition() {
		WindowsDriver driver = (WindowsDriver)TestCaseTM.getDriverTestCase();
		clickClearButton(driver);
		execOnePlusSeven(driver);
	}
	
	@Step (
		description="Click the <b>Clear</b> button",
		expected="Appears a result 0")
	public void clickClearButton(WindowsDriver driver) {
		driver.findElementByName("Borrar").click(); //Value in the Calc English Version: "Clear"
		checkResult("0", driver);
	}
	
	@Step (
		description="Execute the operation <b>One + Seven =</b>",
		expected="Appears the reslt Eight")
	public void execOnePlusSeven(WindowsDriver driver) {
		driver.findElementByName("Uno").click(); //In english "One" 
		driver.findElementByName("Más").click(); //In english "Plus"
		driver.findElementByName("Siete").click(); //In english "Seven"
		driver.findElementByName("Es igual a").click(); //In englis "Equals"
		checkResult("8", driver);
	}
	
	@Validation (
		description="The result is <b>#{resultExpected}</b>",
		level=State.Defect)
	public boolean checkResult(String resultExpected, WindowsDriver driver) {
		String result = getCalculatorResultText(driver);
		return result.compareTo(resultExpected)==0;
	}
	
	private String getCalculatorResultText(WindowsDriver driver) {
		WebElement calculatorResult = driver.findElementByAccessibilityId("CalculatorResults");
		return calculatorResult.getText().replace("La pantalla muestra", "").trim(); //In Calc English Version: "Display is"
	}
}
