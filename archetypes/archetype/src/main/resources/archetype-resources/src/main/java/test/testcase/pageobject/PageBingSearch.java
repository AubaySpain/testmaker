package ${package}.test.testcase.pageobject;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

public class PageBingSearch extends PageObjTM {

	public static final String URL_BING = "http://www.bing.com";
	
	private static final String XP_BUTTON_ACCEPT_COOKIES = "//button[@id='bnp_btn_accept']";
	private static final String XP_FORM_SEARCH = "//form[@action='/search' and @id='sb_form']";
	private static final String ID_INPUT_SEARCH = "sb_form_q";

	public void goToPage() {
		driver.get(URL_BING);
	}
	
	public boolean checkIsPage() {
		return state(VISIBLE, XP_FORM_SEARCH).check();
	}
	
	public void searchText(String textToSearch) {
		acceptCookies();
		By byInputInicio = By.id(ID_INPUT_SEARCH);
		getElement(byInputInicio).sendKeys(textToSearch);
		getElement(byInputInicio).sendKeys(Keys.ENTER);
	}
	
	private void acceptCookies() {
		if (state(VISIBLE, XP_BUTTON_ACCEPT_COOKIES).wait(2).check()) {
			click(XP_BUTTON_ACCEPT_COOKIES).exec();
		}
	}
}
