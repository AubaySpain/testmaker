package org.aubay.testmaker.service.webdriver.pageobject;

import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class StateElement {
	
	public enum State {Present, Visible, Invisible, Clickable, Unclickable}
	
	private final WebDriver driver;
	private final WebElement webelement;
	private final State state;
	private final By by;
	private final int seconds;
	
	private StateElement(State state, WebDriver driver, WebElement webelement, By by, int seconds) {
		this.driver = driver;
		this.webelement = webelement;
		this.state = state;
		this.by = by;
		this.seconds = seconds;
	}
	
	public boolean check() {
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		ExpectedCondition<?> expectedCondition;
		switch (state) {
			case Present:
				expectedCondition = getConditionForElementPresent();
				break;
			case Visible:
				expectedCondition = getConditionForElementVisible();
				break;
			case Invisible:
				expectedCondition = getConditionForElementInvisible();
				break;
			case Clickable:
				expectedCondition = getConditionForElementClickable();
				break;
			case Unclickable:
			default:
				expectedCondition = getConditionForElementUnclickable();
		}
		
		if (expectedCondition==null) {
			return false;
		}
		
		try {
			wait.until(expectedCondition);
			return true;
		} 
		catch (org.openqa.selenium.TimeoutException e) {
			return false;
		}
	}
	
	private ExpectedCondition<WebElement> getConditionForElementPresent() {
		if (webelement==null) {
			return ExpectedConditions.presenceOfElementLocated(by);
		} else {
			if (by==null) {
				return ExpectedConditions.presenceOfElementLocated(By.tagName("html"));
			}
			return ExpectedConditions.presenceOfNestedElementLocatedBy(webelement, by);
		}
	}
	
	private ExpectedCondition<WebElement> getConditionForElementVisible() {
		if (webelement==null) {
			return ExpectedConditions.visibilityOfElementLocated(by);
		} else {
			if (by==null) {
				return ExpectedConditions.visibilityOf(webelement);
			} else {
				WebElement childElement = getChildElement(webelement, by);
				if (childElement==null) {
					return null;
				}
				return ExpectedConditions.visibilityOf(childElement);
			}
		}
	}
	
	private ExpectedCondition<Boolean> getConditionForElementInvisible() {
		if (webelement==null) {
			return ExpectedConditions.invisibilityOfElementLocated(by);
		} else {
			if (by==null) {
				return ExpectedConditions.invisibilityOf(webelement);
			} else {
				WebElement childElement = getChildElement(webelement, by);
				if (childElement==null) {
					return null;
				}
				return ExpectedConditions.invisibilityOf(childElement);
			}
		}
	}
	
	private ExpectedCondition<WebElement> getConditionForElementClickable() {
		if (webelement==null) {
			return ExpectedConditions.elementToBeClickable(by);
		} else {
			if (by==null) {
				return ExpectedConditions.elementToBeClickable(webelement);
			} else {
				WebElement childElement = getChildElement(webelement, by);
				if (childElement==null) {
					return null;
				}
				return ExpectedConditions.elementToBeClickable(childElement);
			}
		}
	}
	
	private ExpectedCondition<Boolean> getConditionForElementUnclickable() {
		if (webelement==null) {
			return ExpectedConditions.not(ExpectedConditions.elementToBeClickable(by));
		} else {
			if (by==null) {
				return ExpectedConditions.not(ExpectedConditions.elementToBeClickable(webelement));
			} else {
				WebElement childElement = getChildElement(webelement, by);
				if (childElement==null) {
					return null;
				}
				return ExpectedConditions.not(ExpectedConditions.elementToBeClickable(childElement));
			}
		}
	}
	
	private WebElement getChildElement(WebElement webelement, By child) {
		try {
			WebElement childElem = webelement.findElement(by);
			return childElem;
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	
	public static class BuilderState {
		private final State state;
		private final WebDriver driver;
		private WebElement webelement;
		private By by;
		private int seconds;
		
		public BuilderState(State state, By by, WebDriver driver) {
			this.state = state;
			this.driver = driver;
			this.by = by;
		}
		public BuilderState(State state, WebElement webelement, WebDriver driver) {
			this.state = state;
			this.driver = driver;
			this.webelement = webelement;
		}
		
		public BuilderState webelement(WebElement webelement) {
			this.webelement = webelement;
			return this;
		}
		public BuilderState by(By by) {
			this.by = by;
			return this;
		}
		public BuilderState wait(int seconds) {
			this.seconds = seconds;
			return this;
		}
		
		public StateElement build() {
			StateElement stateElement = new StateElement(state, driver, webelement, by, seconds);
			return stateElement;
		}
		public boolean check() {
			return build().check();
		}
	}
}
