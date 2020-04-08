package ${package}.test.testcase.pageobject;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.click;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Page1Google {

	private final WebDriver driver;
	
	private static final String XPathInputSearch = "//input[@title='Buscar']";
	private static final String XPathButtonBuscar = "//input[@class='gNO89b']";
	
	public Page1Google(WebDriver driver) {
		this.driver = driver;
	}
	
	public void searchText(String textToSearch) {
		By byInputInicio = By.xpath(XPathInputSearch);
		driver.findElement(byInputInicio).sendKeys(textToSearch);
		
		By byButtonBuscarConGoogle = By.xpath(XPathButtonBuscar);
		click(byButtonBuscarConGoogle, driver).exec();
	}
	
}
