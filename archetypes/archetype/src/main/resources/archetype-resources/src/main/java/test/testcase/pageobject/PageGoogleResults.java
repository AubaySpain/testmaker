package ${package}.test.testcase.pageobject;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

public class PageGoogleResults extends PageObjTM {

	public static final String XP_RESULT_ITEM = "//div[@jsdata[contains(.,'PhoHd')]]";
	public static final String XP_NUM_RESULTS_DESKTOP = "//*[@id='result-stats']";
	
	public boolean checkAreResults(int seconds) {
		return state(PRESENT, XP_RESULT_ITEM).wait(seconds).check();
	}
	
	public boolean checkIsNumResultsUntil(int seconds) {
		return state(VISIBLE, XP_NUM_RESULTS_DESKTOP).wait(seconds).check();
	}
	
	public long getNumResults() {
		if (isDesktop()) {
			var numResultsElem = getElement(XP_NUM_RESULTS_DESKTOP);
			return Utils.getLongFromElement(numResultsElem);			
		}
		return getElements(XP_RESULT_ITEM).size();
	}
	
}
