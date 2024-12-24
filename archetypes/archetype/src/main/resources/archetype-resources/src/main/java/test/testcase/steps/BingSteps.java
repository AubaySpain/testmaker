package com.aubay.qa.testaubay.test.testcase.steps;

import com.aubay.qa.testaubay.test.testcase.pageobject.PageBingResults;
import com.aubay.qa.testaubay.test.testcase.pageobject.PageBingSearch;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;

import static com.aubay.qa.testaubay.test.testcase.pageobject.PageBingSearch.URL_BING;
import static com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen.*;
import static com.github.jorge2m.testmaker.conf.State.INFO;

public class BingSteps {

	private final PageBingSearch pageBingSearch = new PageBingSearch();
	private final PageBingResults pageBingResults = new PageBingResults();
	
	private long numResults;
	
	@Step (
		description="Vamos a la Url de Bing <b>" + URL_BING + "</b>",
		expected="Aparece la página de búsqueda de Bing")
	public void goToBing() {
		pageBingSearch.goToPage();
		checkIsPage(2);
	}	
	
	@Validation (description="Aparece la página con el buscador de Bing (la esperamos hasta #{seconds} segundos)")
	public boolean checkIsPage(int seconds) {
		return pageBingSearch.checkIsPage(seconds);
	}
	
	@Step (
		description="Introducimos el texto <b>#{textToSearch}</b> y pulsamos ENTER",
		expected="Aparecen resultados de búsqueda",
		saveImagePage=ALWAYS,
		saveHtmlPage=ALWAYS)
	public long search(String textToSearch) {
		pageBingSearch.searchText(textToSearch);
		checkResultsSearchBing();
		return numResults;
	}
	
	@Validation
	public ChecksTM checkResultsSearchBing() {  
		var checks = ChecksTM.getNew();
		int maxSeconds = 2;
		checks.add(
			"Aparece algún resultado (lo esperamos hasta " + maxSeconds + " segundos)",
			pageBingResults.checkIsResultsUntil(maxSeconds));
		
		checks.add(
			"Aparece el número de entradas (lo esperamos hasta " + maxSeconds + " segundos)",
			pageBingResults.checkIsNumResultsUntil(maxSeconds), INFO);
		
		numResults = pageBingResults.getNumResults();
		checks.add(
			"El número de entradas obtenido (" + numResults + ") es mayor que 0",
			numResults > 0, INFO);
		
		return checks;
	}
	
}
