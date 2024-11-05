package ${package}.test.testcase.pageobject;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageBingResults extends PageObjTM {

	private static final String XP_RESULT_ITEM = "//li[@data-bm]";
	private static final String XP_NUM_RESULTS_DESKTOP = "//span[@class='sb_count']";
	
	public boolean checkIsResultsUntil(int seconds) {
		return state(VISIBLE, XP_RESULT_ITEM).wait(seconds).check();
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
