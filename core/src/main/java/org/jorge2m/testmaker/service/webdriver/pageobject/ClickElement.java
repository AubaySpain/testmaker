package org.jorge2m.testmaker.service.webdriver.pageobject;

import static org.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static org.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import org.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

public class ClickElement {

	private final WebDriver driver;
	private final WebElement webelement;
	private final By by;
	private final State state;
	private final TypeClick typeOfClick;
	private final int waitLink;
	private final int waitLoadPage;
	
	private ClickElement(
			WebDriver driver, WebElement webelement, By by, State state, TypeClick typeOfClick, int waitLink, int waitLoadPage) {
		this.driver = driver;
		this.webelement = webelement;
		this.by = by;
		this.typeOfClick = typeOfClick;
		this.state = state;
		this.waitLink = waitLink;
		this.waitLoadPage = waitLoadPage;
	}
	
	public void click() {
		WebElement elementLink = getElementClick();
		if (waitLink > 0) {
			state(Clickable, elementLink, driver).wait(waitLink).check();
		}
		click(typeOfClick, elementLink);
		if (waitLoadPage > 0) {
			SeleniumUtils.waitForPageLoaded(driver, waitLoadPage);
		}
	}
	
	private WebElement getElementClick() {
		switch (state) {
		case Present:
			return getElementPresent();
		case Visible:
			return getElementVisible();
		case Clickable:
			return getElementClickable();
		default:
			return null;
		}
	}
	
	private WebElement getElementPresent() {
		if (by==null) {
			return webelement;
		}
		if (webelement==null) {
			return driver.findElement(by);
		}
		return webelement.findElement(by);
	}
	private WebElement getElementVisible() {
		if (by==null) {
			if (webelement.isDisplayed()) {
				return webelement;
			}
			return null;
		}
		if (webelement==null) {
			return SeleniumUtils.getElementVisible(driver, by);
		}
		return SeleniumUtils.getElementVisible(webelement, by);
	}
	private WebElement getElementClickable() {
		if (by==null) {
			if (state(Clickable, webelement, driver).check()) {
				return webelement;
			}
			return null;
		}
		if (webelement==null) {
			return SeleniumUtils.getElementClickable(driver, by);
		}
		return SeleniumUtils.getElementClickable(webelement, by, driver);
	}
	
	private void click(TypeClick typeOfClick, WebElement link) {
		switch (typeOfClick) {
		case javascript:
			clickJavaScript(link);
			break;
		case webdriver:
		default:
			clickWebdriverFirst(link);
		}
	}
	private void clickJavaScript(WebElement link) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", link);
	}
	private void clickWebdriverFirst(WebElement link) {
		try {
			link.click();
		}
		catch (WebDriverException e) {
			clickJavaScript(link);
		}
	}
	
	public static class BuilderClick {
		private final WebDriver driver;
		private WebElement webelement = null;
		private By by = null;
		private State state = State.Present;
		private TypeClick typeOfClick = TypeClick.webdriver;
		private int waitLink = 0;
		private int waitLoadPage = 30;
		
		public BuilderClick(By by, WebDriver driver) {
			this.driver = driver;
			this.by = by;
		}
		public BuilderClick(WebElement webelement, WebDriver driver) {
			this.driver = driver;
			this.webelement = webelement;
		}
		
		public BuilderClick by(By by) {
			this.by = by;
			return this;
		}
		public BuilderClick state(State state) {
			if (state!=State.Present && state!=State.Clickable && state!=State.Visible) {
				throw new IllegalArgumentException("Param state state only accept values Present, Clickable and Visible");
			}
			this.state = state;
			return this;
		}
		public BuilderClick type(TypeClick typeOfClick) {
			this.typeOfClick = typeOfClick;
			return this;
		}
		public BuilderClick waitLink(int seconds) {
			this.waitLink = seconds;
			return this;
		}
		public BuilderClick waitLoadPage(int seconds) {
			this.waitLoadPage = seconds;
			return this;
		}
		
		public ClickElement build() {
			ClickElement clickElement = new ClickElement(driver, webelement, by, state, typeOfClick, waitLink, waitLoadPage);
			return clickElement;
		}
		public void exec() {
			build().click();
		}
	}
}
