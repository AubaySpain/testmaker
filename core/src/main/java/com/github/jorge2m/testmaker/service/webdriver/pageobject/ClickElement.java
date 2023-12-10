package com.github.jorge2m.testmaker.service.webdriver.pageobject;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

public class ClickElement {

	private final WebDriver driver;
	private final WebElement webelement;
	private final By by;
	private final State state;
	private final TypeClick typeOfClick;
	private final int waitLink;
	private final int waitLoadPage;
	private final int x;
	private final int y;
	
	private ClickElement(
			WebDriver driver, WebElement webelement, By by, State state, TypeClick typeOfClick, int waitLink, int waitLoadPage, int x, int y) {
		this.driver = driver;
		this.webelement = webelement;
		this.by = by;
		this.typeOfClick = typeOfClick;
		this.state = state;
		this.waitLink = waitLink;
		this.waitLoadPage = waitLoadPage;
		this.x = x;
		this.y = y;
	}
	
	public void click() {
		WebElement elementLink;
		if (waitLink > 0) {
			elementLink = waitForElementClickable(waitLink);
		} else {
			elementLink = getElementClick();			
		}
		click(typeOfClick, elementLink);
		if (waitLoadPage > 0) {
			PageObjTM.waitForPageLoaded(driver, waitLoadPage);
		}
	}
	
	private WebElement waitForElementClickable(int seconds) throws NoSuchElementException {
		NoSuchElementException eFinal = new NoSuchElementException("Element not clickable");
		for (int i=0; i<seconds; i++) {
			try {
				WebElement elementLink = getElementClick();
				if (state(CLICKABLE, elementLink, driver).check()) {
					return elementLink;
				}
			}
			catch (NoSuchElementException e) {
				eFinal = e;
			}
			waitMillis(1000);
		}
		throw eFinal;
	}
	
	private WebElement getElementClick() {
		switch (state) {
		case PRESENT:
			return getElementPresent();
		case VISIBLE:
			return getElementVisible();
		case CLICKABLE:
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
			return PageObjTM.getElementVisible(driver, by);
		}
		return PageObjTM.getElementVisible(webelement, by);
	}
	private WebElement getElementClickable() {
		if (by==null) {
			if (state(CLICKABLE, webelement, driver).check()) {
				return webelement;
			}
			return null;
		}
		if (webelement==null) {
			return PageObjTM.getElementClickable(driver, by);
		}
		return PageObjTM.getElementClickable(webelement, by, driver);
	}
	
	private void click(TypeClick typeOfClick, WebElement link) {
		switch (typeOfClick) {
		case JAVASCRIPT:
			clickJavaScript(link);
			break;
		case WEBDRIVER:
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
			if (x==0 && y==0) {
				link.click();
			} else {
				new Actions(driver)
					.moveToElement(link, x, y)
					.click().build().perform();
			}
		}
		catch (WebDriverException e) {
			clickJavaScript(link);
		}
	}
	
	public static class BuilderClick {
		private final WebDriver driver;
		private WebElement webelement = null;
		private By by = null;
		private State state = State.PRESENT;
		private TypeClick typeOfClick = TypeClick.WEBDRIVER;
		private int waitLink = 0;
		private int waitLoadPage = 30;
		private int x = 0;
		private int y = 0;
		
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
			if (state!=State.PRESENT && state!=State.CLICKABLE && state!=State.VISIBLE) {
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
		public BuilderClick waitLoadPage() {
			return waitLoadPage(30);
		}		
		public BuilderClick setX(int x) {
			this.x = x;
			return this;
		}
		public BuilderClick setY(int y) {
			this.y = y;
			return this;
		}
		
		public ClickElement build() {
			ClickElement clickElement = new ClickElement(driver, webelement, by, state, typeOfClick, waitLink, waitLoadPage, x, y);
			return clickElement;
		}
		public void exec() {
			build().click();
		}
	}
}
