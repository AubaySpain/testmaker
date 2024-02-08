package com.github.jorge2m.example_test.test.testcase.script;

import java.io.Serializable;

import org.testng.annotations.Test;

import com.github.jorge2m.example_test.test.testcase.stpv.Page1BingStpV;
import com.github.jorge2m.example_test.test.testcase.stpv.Page1GoogleStpV;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.TestFromFactory;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;

public class BuscarPatternPageObject implements TestFromFactory, Serializable {
	
	private static final long serialVersionUID = 7458665307721500197L;

	private final String itemToSearch;
	
	public BuscarPatternPageObject() {
		this.itemToSearch = "Mario Maker 2";
	}
	public BuscarPatternPageObject(String itemToSearch) {
		this.itemToSearch = itemToSearch;
	}
	
	@Override
	public String getIdTestInFactory() {
		return this.itemToSearch;
	}	
	
	@Test (
		groups={"Buscador", "Canal:desktop_App:google"}, alwaysRun=true, 
		description="Checkear que la búsqueda en Google devuelve más resultados que la búsqueda en Bing")
	public void BUS100_CheckGoogleMoreResults() {
		var driver = TestCaseTM.getDriverTestCase().get();

		var page1Google = new Page1GoogleStpV(driver);
		var page2Google = page1Google.search(itemToSearch);
		
		var page1Bing = new Page1BingStpV(driver);
		page1Bing.goToBing();
		var page2Bing = page1Bing.search(itemToSearch);

		float numResultsGoogle = page2Google.getNumResults(); 
		float numResultsBing = page2Bing.getNumResults();
		
		checkMoreResulstsInGoogle(numResultsGoogle, numResultsBing);
	}
	
	@Validation (
		description="Aparecen más resultados en Google (<b>#{numResultsGoogle}</b> obtenidos) que en Bing (<b>#{numResultsBing}</b> obtenidos)",
		level=State.WARN)
	public boolean checkMoreResulstsInGoogle(float numResultsGoogle, float numResultsBing) {
		return (numResultsGoogle > numResultsBing);
	}
	
}
