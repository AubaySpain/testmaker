package com.github.jorge2m.example_test.test.testcase.script;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.github.jorge2m.example_test.test.testcase.stpv.Page1BingStpV;
import com.github.jorge2m.example_test.test.testcase.stpv.Page1GoogleStpV;
import com.github.jorge2m.example_test.test.testcase.stpv.Page2BingResultsStpV;
import com.github.jorge2m.example_test.test.testcase.stpv.Page2GoogleResultsStpV;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;

public class BuscarPatternPageObject {

	private final String itemToSearch;
	private final boolean factory;
	
	public BuscarPatternPageObject() {
		this.factory = false;
		this.itemToSearch = "Mario Maker 2";
	}
	public BuscarPatternPageObject(String itemToSearch) {
		this.factory = true;
		this.itemToSearch = itemToSearch;
	}
	
	@Test (
		groups={"Buscador", "Canal:desktop_App:google"}, alwaysRun=true, 
		description="Checkear que la búsqueda en Google devuelve más resultados que la búsqueda en Bing")
	public void BUS100_CheckGoogleMoreResults() {
		WebDriver driver = TestCaseTM.getDriverTestCase();
		if (factory) {
			TestCaseTM.addNameSufix(itemToSearch);
		}
		
		Page1GoogleStpV page1Google = new Page1GoogleStpV(driver);
		Page2GoogleResultsStpV page2Google = page1Google.search(itemToSearch);
		
		Page1BingStpV page1Bing = new Page1BingStpV(driver);
		page1Bing.goToBing();
		Page2BingResultsStpV page2Bing = page1Bing.search(itemToSearch);

		float numResultsGoogle = page2Google.getNumResults(); 
		float numResultsBing = page2Bing.getNumResults();
		
		checkMoreResulstsInGoogle(numResultsGoogle, numResultsBing);
	}
	
	@Validation (
		description="Aparecen más resultados en Google (<b>#{numResultsGoogle}</b> obtenidos) que en Bing (<b>#{numResultsBing}</b> obtenidos)",
		level=State.Warn)
	public boolean checkMoreResulstsInGoogle(float numResultsGoogle, float numResultsBing) {
		return (numResultsGoogle > numResultsBing);
	}
	
}
