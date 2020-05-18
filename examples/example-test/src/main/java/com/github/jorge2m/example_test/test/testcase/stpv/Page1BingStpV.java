package com.github.jorge2m.example_test.test.testcase.stpv;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.example_test.test.testcase.pageobject.Page1Bing;
import static com.github.jorge2m.example_test.test.testcase.pageobject.Page1Bing.URL_BING;

import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;

public class Page1BingStpV {

	private final Page1Bing page1Bing;
	private final WebDriver driver;
	
	public Page1BingStpV(WebDriver driver) {
		this.driver = driver;
		this.page1Bing = new Page1Bing(driver);
	}
	
	@Step (
		description="Vamos a la Url de Bing <b>" + URL_BING + "</b>",
		expected="Aparece la página de búsqueda de Bing")
	public void goToBing() {
		page1Bing.goToPage();
		checkIsPage();
	}	
	
	@Validation (
		description="Aparece la página con el buscador de Bing",
		level=State.Defect)
	public boolean checkIsPage() {
		return page1Bing.checkIsPage();
	}
	
	@Step (
		description="Introducimos el texto <b>#{textToSearch}</b> y clickamos el icono de la Lupa",
		expected="Aparecen resultados de búsqueda",
		saveHtmlPage=SaveWhen.Always)
	public Page2BingResultsStpV search(String textToSearch) {
		page1Bing.searchText(textToSearch);

		Page2BingResultsStpV page2BingResultsStpV = new Page2BingResultsStpV(driver);
		page2BingResultsStpV.checkResultsSearchBing();
		
		return page2BingResultsStpV;
	}
	
}
