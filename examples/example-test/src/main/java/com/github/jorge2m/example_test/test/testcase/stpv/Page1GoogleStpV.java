package com.github.jorge2m.example_test.test.testcase.stpv;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.example_test.test.testcase.pageobject.Page1Google;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;

public class Page1GoogleStpV {

	private final WebDriver driver;
	
	public Page1GoogleStpV(WebDriver driver) {
		this.driver = driver;
	}
	
	@Step (
		description="Introducimos el texto <b>#{textToSearch}</b> y clickamos el botón \"Buscar con Google\"",
		expected="Aparecen resultados de búsqueda",
		saveImagePage=SaveWhen.Always,
		saveHtmlPage=SaveWhen.Always)
	public Page2GoogleResultsStpV search(String textToSearch) {
		Page1Google page1Google = new Page1Google(driver);
		page1Google.searchText(textToSearch);
		
		Page2GoogleResultsStpV page2GoogleStpV = new Page2GoogleResultsStpV(driver);
		page2GoogleStpV.checkResults();
		
		return page2GoogleStpV;
	}
	
}
