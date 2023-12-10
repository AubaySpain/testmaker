package com.github.jorge2m.example_test.test.testcase.stpv;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.example_test.test.testcase.pageobject.Page2GoogleResults;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;

public class Page2GoogleResultsStpV {

	private final Page2GoogleResults page2GoogleResults;
	private long numResults;
	
	public Page2GoogleResultsStpV(WebDriver driver) {
		this.page2GoogleResults = new Page2GoogleResults(driver);
	}
	
	@Validation
	public ChecksTM checkResults() {
		ChecksTM validations = ChecksTM.getNew();
		int maxSeconds = 2;
		validations.add(
			"Aparece alguna entrada de resultado (la esperamos hasta " + maxSeconds + " segundos)",
			page2GoogleResults.checkAreResultsUntil(maxSeconds), State.DEFECT);
		validations.add(
			"Aparece el número de entradas (lo esperamos hasta " + maxSeconds + " segundos)",
			page2GoogleResults.checkIsNumResultsUntil(maxSeconds),
			State.INFO);
		
		numResults = page2GoogleResults.getNumResults();
		validations.add(
			"El número de entradas obtenido (" + numResults + ") es mayor que 0",
			numResults > 0, State.INFO);
		
		return validations;
	}
	
	public long getNumResults() {
		return numResults;
	}

}
