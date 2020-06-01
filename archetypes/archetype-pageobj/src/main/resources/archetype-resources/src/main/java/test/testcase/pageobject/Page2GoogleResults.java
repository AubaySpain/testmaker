package ${package}.test.testcase.pageobject;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

public class Page2GoogleResults extends PageObjTM {
	
	public final static String XPathResult = "//h3[@class[contains(.,'LC20lb')]]";
	public final static String IdNumResults = "result-stats";
	
	public Page2GoogleResults(WebDriver driver) {
		super(driver);
	}
	
	public boolean checkAreResultsUntil(int maxSeconds) {
		By byEntradaResultado = By.xpath(XPathResult);
		return (state(Present, byEntradaResultado).wait(maxSeconds).check());
	}
	
	public boolean checkIsNumResultsUntil(int maxSeconds) {
		By byNumResults = By.id(IdNumResults);
		return (state(Visible, byNumResults).wait(maxSeconds).check());
	}
	
	public long getNumResults() {
		WebElement numResultsElem = getElementWeb(By.id(IdNumResults), driver);
		return Utils.getLongFromElement(numResultsElem);
	}
}
