package org.jorge2m.example_test.test.testcase.script;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import org.jorge2m.testmaker.boundary.aspects.step.Step;
import org.jorge2m.testmaker.boundary.aspects.validation.Validation;
import org.jorge2m.testmaker.conf.State;
import org.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import static org.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static org.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class BuscarWithoutRefactor {

	@Test (
		groups={"Buscador", "Canal:desktop_App:google"}, alwaysRun=true, 
		description="Buscar un literal que devuelva algún resultado")
	public void BUS001_Buscar_literal_con_resultados() {
		TestCaseTM testCase = TestCaseTM.getTestCaseInExecution();
		WebDriver driver = testCase.getDriver();
		inputTextAndClickBuscarConGoogle("Wikipedia", driver);
	}
	
	@Step (
		description="Introducimos el texto <b>#textToInput</b> y clickamos el botón \"Buscar con Google\"",
		expected="Aparecen resultados de búsqueda")
	public void inputTextAndClickBuscarConGoogle(String textToInput, WebDriver driver) {
		By byInputInicio = By.xpath("//input[@title='Buscar']");
		driver
			.findElement(byInputInicio)
			.sendKeys(textToInput);
		
		By byButtonBuscarConGoogle = By.xpath("//input[@class='gNO89b']");
		click(byButtonBuscarConGoogle, driver).exec();
		
		checkAreResults(driver);
	}
	
	@Validation(
		description="Aparecen resultados de búsqueda",
		level=State.Defect)
	public boolean checkAreResults(WebDriver driver) {
		By byEntradaResultado = By.xpath("//h3[@class[contains(.,'LC20lb')]]");
		return (state(Visible, byEntradaResultado, driver).check());
	}
	
}
