package com.github.jorge2m.example_test.test.testcase.stpv;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.example_test.test.testcase.pageobject.Page2BingResults;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;

public class Page2BingResultsStpV {

	private final Page2BingResults page2BingResults;
	private long numResults;
	
	public Page2BingResultsStpV(WebDriver driver) {
		page2BingResults = new Page2BingResults(driver);
	}
	
	@Validation
	public ChecksTM checkResultsSearchBing() {
		ChecksTM validations = ChecksTM.getNew();
		int maxSeconds = 1;
		validations.add(
			"Aparece algún resultado (lo esperamos hasta " + maxSeconds + " segundos)",
			page2BingResults.checkIsResultsUntil(maxSeconds),
			State.Defect);
		
		numResults = page2BingResults.getNumResults();
		validations.add(
			"El número de entradas obtenido (" + numResults + ") es mayor que 0",
			numResults > 0, State.Defect);
		
		return validations;
	}
	
	public long getNumResults() {
		return numResults;
	}
	
}
