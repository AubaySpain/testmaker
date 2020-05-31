package ${package}.test.testcase.pageobject;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.state;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class Page1Bing {

	private final WebDriver driver;
	
	public static final String URL_BING = "http://www.bing.com";
	private static final String XPathFormSearch = "//form[@action='/search' and @id='sb_form']";
	private static final String IdInputSearch = "sb_form_q";

	
	public Page1Bing(WebDriver driver) {
		this.driver = driver;
	}
	
	public void goToPage() {
		driver.get(URL_BING);
	}
	
	public boolean checkIsPage() {
		By formSearchBy = By.xpath(XPathFormSearch);
		return (state(Visible, formSearchBy, driver).check());
	}
	
	public void searchText(String textToSearch) {
		By byInputInicio = By.id(IdInputSearch);
		driver.findElement(byInputInicio).sendKeys(textToSearch);
		driver.findElement(byInputInicio).sendKeys(Keys.ENTER);
	}
}
