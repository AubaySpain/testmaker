package ${package}.test.testcase.script;

import ${package}.test.testcase.stpv.GoogleMainPageStpV;
import ${package}.test.testcase.stpv.ResultsGooglePageStpV;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import org.jorge2m.testmaker.domain.suitetree.TestCaseTM;

public class Buscar {

	@Test (
		groups={"Buscar", "Canal:desktop_App:google"}, alwaysRun=true, 
		description="Se busca un literal que generará varias páginas de resultados")
	public void BUS001_Search_With_ManyPages() {
		TestCaseTM testCase = TestCaseTM.getTestCaseInExecution();
		WebDriver driver = testCase.getDriver();
		
		GoogleMainPageStpV googlePageStpV = GoogleMainPageStpV.getNew(driver);
		ResultsGooglePageStpV resultsPageStpV = 
			googlePageStpV.searchTextWithManyResultPages("Legend of Zelda Breath of The Wild", driver);
	}	
	
	@Test (
		groups={"Buscar", "Canal:desktop_App:google"}, alwaysRun=true, 
		description="Se busca un literal que no generará varias páginas de resultados")
	public void BUS002_Search_Without_ManyPages() {
		TestCaseTM testCase = TestCaseTM.getTestCaseInExecution();
		WebDriver driver = testCase.getDriver();
		
		GoogleMainPageStpV googlePageStpV = GoogleMainPageStpV.getNew(driver);
		ResultsGooglePageStpV resultsPageStpV = 
			googlePageStpV.searchTextWithoutManyResultPages("43rf4sdfsdf4444", driver);
	}	
}
