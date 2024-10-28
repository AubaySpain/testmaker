package ${package}.test.testcase.pageobject;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import org.openqa.selenium.Keys;

public class PageGoogleSearch extends PageObjTM {

	public static final String URL_GOOGLE = "http://www.google.com";
	
	private static final String XP_BUTTON_ACCEPT_COOKIES = "//button[@id='L2AGLb']";
	private static final String XP_INPUT_SEARCH = "//*[@name='q']"; 
	
	public void goToPage() {
		driver.get(URL_GOOGLE);
	}
	
	public boolean checkIsPage() {
		return state(VISIBLE, XP_INPUT_SEARCH).check();
	}
	
	public void searchText(String textToSearch) {
		acceptCookies();
		getElement(XP_INPUT_SEARCH).sendKeys(textToSearch + Keys.RETURN); 
	}
	
	private void acceptCookies() {
		if (state(VISIBLE, XP_BUTTON_ACCEPT_COOKIES).wait(1).check()) {
			click(XP_BUTTON_ACCEPT_COOKIES).exec();
		}
	}
	
}
