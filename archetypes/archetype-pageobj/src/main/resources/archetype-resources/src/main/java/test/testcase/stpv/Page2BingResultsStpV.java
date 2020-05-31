#set($hash = '#')
package ${package}.test.testcase.stpv;

import org.openqa.selenium.WebDriver;

import ${package}.test.testcase.pageobject.Page2BingResults;
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
		int maxSeconds = 2;
		validations.add(
			"Aparece algún resultado (lo esperamos hasta " + maxSeconds + " segundos)",
			page2BingResults.checkIsResultsUntil(maxSeconds),
			State.Defect);
		validations.add(
			"Aparece el número de entradas (lo esperamos hasta " + maxSeconds + " segundos)",
			page2BingResults.checkIsNumResultsUntil(maxSeconds),
			State.Info);
		
		numResults = page2BingResults.getNumResults();
		validations.add(
			"El número de entradas obtenido (" + numResults + ") es mayor que 0",
			numResults > 0, State.Info);
		
		return validations;
	}
	
	public long getNumResults() {
		return numResults;
	}
	
}
