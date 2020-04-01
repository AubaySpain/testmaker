package org.aubay.testmaker.service.webdriver.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;


public class SelectElement {

	public enum TypeSelect { VisibleText, Value, ValueJavaScript }
	
	private final WebDriver driver;
	private final WebElement webelement;
	private final By by;
	private final String value;
	private final TypeSelect typeSelect;
	private final int waitLoadPage;
	
	private SelectElement(WebDriver driver, WebElement webelement, By by, String value, TypeSelect typeSelect, int waitLoadPage) {
		this.driver = driver;
		this.webelement = webelement;
		this.by = by;
		this.value = value;
		this.typeSelect = typeSelect;
		this.waitLoadPage = waitLoadPage;
	}
	
	public void exec() {
		WebElement selectElem = getElement(); 
		switch (typeSelect) {
		case VisibleText:
			new Select(selectElem).selectByVisibleText(value);
			break;
		case Value:
			if (selectElem.getAttribute("value").compareTo(value)!=0) {
				selectElem.sendKeys(value);
			}
			break;
		case ValueJavaScript:
			if (selectElem.getAttribute("value").compareTo(value)!=0) {
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript(
					"const textToFind = '" + value + "';" +
					"const dd = arguments[0];" +
					"dd.selectedIndex = [...dd.options].findIndex (option => option.text === textToFind);", selectElem
				);
			}
		}
		
		if (waitLoadPage > 0) {
			SeleniumUtils.waitForPageLoaded(driver, waitLoadPage);
		}
	}
	
	private WebElement getElement() {
		if (by==null) {
			return webelement;
		}
		if (webelement==null) {
			return driver.findElement(by);
		}
		return webelement.findElement(by);
	}
	
	public static class BuilderSelect {
		private final String value;
		private final WebDriver driver;
		private WebElement webelement;
		private By by;
		private TypeSelect typeSelect = TypeSelect.VisibleText;
		private int waitLoadPage;
		
		public BuilderSelect(By by, String value, WebDriver driver) {
			this.value = value;
			this.driver = driver;
			this.by = by;
		}
		public BuilderSelect(WebElement webelement, String value, WebDriver driver) {
			this.value = value;
			this.webelement = webelement;
			this.driver = driver;
		}
		
		public BuilderSelect by(By by) {
			this.by = by;
			return this;
		}
		public BuilderSelect type(TypeSelect typeSelect) {
			this.typeSelect = typeSelect;
			return this;
		}
		public BuilderSelect wait(int waitLoadPage) {
			this.waitLoadPage = waitLoadPage;
			return this;
		}
		
		public SelectElement build() {
			SelectElement selectElement = new SelectElement(driver, webelement, by, value, typeSelect, waitLoadPage);
			return selectElement;
		}
		public void exec() {
			build().exec();
		}
	}
	
}
