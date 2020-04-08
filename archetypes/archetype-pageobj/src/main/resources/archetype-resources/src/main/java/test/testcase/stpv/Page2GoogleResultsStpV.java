package ${package}.test.testcase.stpv;

import org.openqa.selenium.WebDriver;

import ${package}.test.testcase.pageobject.Page2GoogleResults;
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
		validations.add(
			"Aparece alguna entrada de resultado",
			page2GoogleResults.checkAreResults(), State.Defect);
		
		numResults = page2GoogleResults.getNumResults();
		validations.add(
			"El número de entradas obtenido (" + numResults + ") es mayor que 0",
			numResults > 0, State.Defect);
		
		return validations;
	}
	
	public long getNumResults() {
		return numResults;
	}

}
