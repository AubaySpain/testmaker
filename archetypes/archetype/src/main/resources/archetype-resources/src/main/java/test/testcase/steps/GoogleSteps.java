package ${package}.test.testcase.steps;

import static ${package}.test.testcase.pageobject.PageGoogleSearch.URL_GOOGLE;
import static com.github.jorge2m.testmaker.conf.State.INFO;
import static com.github.jorge2m.testmaker.conf.State.WARN;

import ${package}.test.testcase.pageobject.PageGoogleResults;
import ${package}.test.testcase.pageobject.PageGoogleSearch;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

public class GoogleSteps extends PageObjTM {

	private final PageGoogleSearch pageGoogleSearch = new PageGoogleSearch();
	private final PageGoogleResults pageGoogleResults = new PageGoogleResults();
	
	private long numResults;
	
	@Step (
		description="Vamos a la Url de Google <b>" + URL_GOOGLE + "</b>",
		expected="Aparece la página de búsqueda de Google")
	public void goToGoogle() {
		pageGoogleSearch.goToPage();
		checkIsPage();
	}	
	
	@Validation (description="Aparece la página con el buscador de Google")
	public boolean checkIsPage() {
		return pageGoogleSearch.checkIsPage();
	}
	
	@Step (
		description="Introducimos el texto <b>#{textToSearch}</b> y clickamos el botón \"Buscar con Google\"",
		expected="Aparecen resultados de búsqueda",
		saveImagePage=SaveWhen.ALWAYS,
		saveHtmlPage=SaveWhen.ALWAYS)
	public long search(String textToSearch) {
		pageGoogleSearch.searchText(textToSearch);
		checkResults();
		return numResults;
	}
	
	@Validation
	public ChecksTM checkResults() {
		var checks = ChecksTM.getNew();
		int maxSeconds = 2;
		checks.add(
			"Aparece alguna entrada de resultado (la esperamos hasta " + maxSeconds + " segundos)",
			pageGoogleResults.checkAreResults(maxSeconds));
		
		if (isDesktop()) {
			checks.add(
				"Aparece el número de entradas (lo esperamos hasta " + maxSeconds + " segundos)",
				pageGoogleResults.checkIsNumResultsUntil(maxSeconds), INFO);
		}
		
		numResults = pageGoogleResults.getNumResults();
		checks.add(
			"El número de entradas obtenido (" + numResults + ") es mayor que 0",
			numResults > 0, INFO);
		
		return checks;
	}
		
	@Validation (
		description="Aparecen más resultados en Google (<b>#{numResultsGoogle}</b> obtenidos) que en Bing (<b>#{numResultsBing}</b> obtenidos)",
		level=WARN)
	public boolean checkMoreResulstsInGoogle(float numResultsGoogle, float numResultsBing) {
		return numResultsGoogle > numResultsBing;
	}
	
}
