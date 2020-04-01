package ${package}.test.testcase.stpv;

import ${package}.test.testcase.pageobject.GoogleMainPage;
import ${package}.test.testcase.pageobject.ResultsGooglePage;
import org.openqa.selenium.WebDriver;

import org.aubay.testmaker.boundary.aspects.validation.Validation;
import org.aubay.testmaker.conf.State;

public class ResultsGooglePageStpV {

	private final ResultsGooglePage resultsPage;
	
	private ResultsGooglePageStpV(WebDriver driver) {
		resultsPage = ResultsGooglePage.getNew(driver);
	}
	private ResultsGooglePageStpV(ResultsGooglePage resultsPage) {
		this.resultsPage = resultsPage;
	}
	public static ResultsGooglePageStpV getNew(WebDriver driver) {
		return new ResultsGooglePageStpV(driver);
	}
	public static ResultsGooglePageStpV getNew(ResultsGooglePage resultsPage) {
		return new ResultsGooglePageStpV(resultsPage);
	}
	
	@Validation (
		description="Aparecen varias páginas de búsqueda",
		level=State.Defect)
	public boolean checkAreManyPages(GoogleMainPage googlePage) {
		return resultsPage.checkAreManyPages();
	}
	
	@Validation (
		description="No aparecen varias páginas de búsqueda",
		level=State.Defect)
	public boolean checkAreNotManyPages(GoogleMainPage googlePage) {
		return !resultsPage.checkAreManyPages();
	}
	
}
