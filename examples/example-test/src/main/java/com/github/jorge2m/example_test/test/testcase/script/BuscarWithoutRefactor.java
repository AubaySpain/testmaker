package com.github.jorge2m.example_test.test.testcase.script;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.github.jorge2m.example_test.test.testcase.pageobject.Page1Bing;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.SeleniumUtils.getElementWeb;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BuscarWithoutRefactor implements Serializable {
	
	private static final long serialVersionUID = 7458665307721500197L;

	private final String itemToSearch;
	private final boolean factory;
	long numResultsGoogle;
	long numResultsBing;
	
	public BuscarWithoutRefactor() {
		this.factory = false;
		this.itemToSearch = "Mario Maker 2";
	}
	public BuscarWithoutRefactor(String itemToSearch) {
		this.factory = true;
		this.itemToSearch = itemToSearch;
	}
	
	@Test (
		groups={"Buscador", "Canal:desktop_App:google"}, alwaysRun=true, 
		description="Checkear que la búsqueda en Google devuelve más resultados que la búsqueda en Bing")
	public void BUS001_CheckGoogleMoreResults() {
		WebDriver driver = TestCaseTM.getDriverTestCase().get();
		if (factory) {
			TestCaseTM.addNameSufix(itemToSearch);
		}
		searchInGoogle(itemToSearch, driver);
		goToBingUrl(Page1Bing.URL_BING, driver);
		searchInBing(itemToSearch, driver);
		checkMoreResulstsInGoogle(numResultsGoogle, numResultsBing);
	}
	
	@Step (
		description="Introducimos el texto <b>#{textToSearch}</b> y clickamos el botón \"Buscar con Google\"",
		expected="Aparecen resultados de búsqueda")
	public void searchInGoogle(String textToSearch, WebDriver driver) {
		By byInputInicio = By.xpath("//input[@name='q']");
		driver.findElement(byInputInicio).sendKeys(textToSearch);
		
		By byButtonBuscarConGoogle = By.xpath("//input[@class='gNO89b']");
		click(byButtonBuscarConGoogle, driver).exec();
		checkResultsSearchGoogle(driver);
	}
	
	@Validation
	public ChecksTM checkResultsSearchGoogle(WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
		By byEntradaResultado = By.xpath("//h3[@class[contains(.,'LC20lb')]]");
		validations.add(
			"Aparece alguna entrada de resultado",
			state(Visible, byEntradaResultado, driver).check(), State.Defect);
		
		numResultsGoogle = getNumResultsGoogle(driver);
		validations.add(
			"El número de entradas obtenido (" + numResultsGoogle + ") es mayor que 0",
			numResultsGoogle > 0, State.Defect);
		
		return validations;
	}
	
	@Step (
		description="Vamos a la Url de Bing <b>#{urlBing}</b>",
		expected="Aparece la página de búsqueda de Bing")
	public void goToBingUrl(String urlBing, WebDriver driver) {
		driver.get(urlBing);
		checkPageSearchBing(driver);
	}	
	
	@Validation (
		description="Aparece la página con el buscador de Bing",
		level=State.Defect)
	public boolean checkPageSearchBing(WebDriver driver) {
		By formSearchBy = By.xpath("//form[@action='/search' and @id='sb_form']");
		return (state(Visible, formSearchBy, driver).check());
	}
	
	@Step (
		description="Introducimos el texto <b>#{textToSearch}</b> y clickamos el icono de la Lupa",
		expected="Aparecen resultados de búsqueda",
		saveImagePage=SaveWhen.Always,
		saveHtmlPage=SaveWhen.Always)
	public void searchInBing(String textToSearch, WebDriver driver) {
		By byInputInicio = By.id("sb_form_q");
		driver.findElement(byInputInicio).sendKeys(textToSearch);
		
		By byIconLupa = By.xpath("//label[@for='sb_form_go']/*[@viewBox]");
		click(byIconLupa, driver).exec();
		checkResultsSearchBing(driver);
	}
	
	@Validation
	public ChecksTM checkResultsSearchBing(WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
		numResultsBing = getNumResultsBing(driver);
		validations.add(
			"El número de entradas obtenido (" + numResultsBing + ") es mayor que 0",
			numResultsGoogle > 0, State.Defect);
		
		return validations;
	}
	
	@Validation (
		description="Aparecen más resultados en Google (<b>#{numResultsGoogle}</b> obtenidos) que en Bing (<b>#{numResultsBing}</b> obtenidos)",
		level=State.Warn)
	public boolean checkMoreResulstsInGoogle(float numResultsGoogle, float numResultsBing) {
		return (numResultsGoogle > numResultsBing);
	}
	
	public static long getNumResultsGoogle(WebDriver driver) {
		WebElement numResultsElem = getElementWeb(By.id("result-stats"), driver);
		return getLongFromElement(numResultsElem);
	}
	
	public static long getNumResultsBing(WebDriver driver) {
		WebElement numResultsElem = getElementWeb(By.xpath("//span[@class='sb_count']"), driver);
		return getLongFromElement(numResultsElem);
	}
	private static long getLongFromElement(WebElement element) {
		if (element!=null && "".compareTo(element.getText())!=0) {
			Pattern pattern = Pattern.compile("([\\d.,]+)");
			Matcher matcher = pattern.matcher(element.getText());
			if (matcher.find()) {
				return Long.valueOf(
					matcher.group(0).
					trim().
					replace(",", "").
					replace(".", ""));
			}
		}
		return 0;
	}
}
