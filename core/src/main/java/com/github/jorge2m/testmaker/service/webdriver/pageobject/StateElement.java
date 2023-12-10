package com.github.jorge2m.testmaker.service.webdriver.pageobject;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class StateElement {
	
	public enum State { PRESENT, VISIBLE, INVISIBLE, CLICKABLE, UNCLICKABLE }
	
	private final WebDriver driver;
	private final WebElement webelement;
	private final State state;
	private final By by;
	private final By byShadow;
	private final int seconds;
	
	private StateElement(State state, WebDriver driver, WebElement webelement, By by, By byShadow, int seconds) {
		this.driver = driver;
		this.webelement = webelement;
		this.state = state;
		this.by = by;
		this.byShadow = byShadow;
		this.seconds = seconds;
	}
	
	public boolean check() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
		ExpectedCondition<?> expectedCondition;
		switch (state) {
			case PRESENT:
				expectedCondition = getConditionForElementPresent();
				break;
			case VISIBLE:
				expectedCondition = getConditionForElementVisible();
				break;
			case INVISIBLE:
				expectedCondition = getConditionForElementInvisible();
				break;
			case CLICKABLE:
				expectedCondition = getConditionForElementClickable();
				break;
			case UNCLICKABLE:
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
			if (byShadow==null) {
				return ExpectedConditionsTM.presenceOfElementLocated(by);
			}
			return ExpectedConditionsTM.presenceOfShadowElementLocated(by, byShadow);
		} else {
			if (by==null) {
				return ExpectedConditionsTM.presenceOfElementLocated(By.tagName("html"));
			}
			return ExpectedConditionsTM.presenceOfNestedElementLocatedBy(webelement, by);
		}
	}
	
	private ExpectedCondition<WebElement> getConditionForElementVisible() {
		if (webelement==null) {
			if (byShadow==null) {
				return ExpectedConditionsTM.visibilityOfElementLocated(by);
			}
			return ExpectedConditionsTM.visibilityOfShadowElementLocated(by, byShadow);			
		} else {
			if (by==null) {
				return ExpectedConditionsTM.visibilityOf(webelement);
			} else {
				WebElement childElement = getChildElement(webelement, by);
				if (childElement==null) {
					return null;
				}
				return ExpectedConditionsTM.visibilityOf(childElement);
			}
		}
	}
	
	private ExpectedCondition<Boolean> getConditionForElementInvisible() {
		if (webelement==null) {
			if (byShadow==null) {
				return ExpectedConditionsTM.invisibilityOfElementLocated(by);
			}
			return ExpectedConditionsTM.invisibilityOfShadowElementLocated(by, byShadow);			
		} else {
			if (by==null) {
				return ExpectedConditionsTM.invisibilityOf(webelement);
			} else {
				WebElement childElement = getChildElement(webelement, by);
				if (childElement==null) {
					return null;
				}
				return ExpectedConditionsTM.invisibilityOf(childElement);
			}
		}
	}
	
	private ExpectedCondition<WebElement> getConditionForElementClickable() {
		if (webelement==null) {
			if (byShadow==null) {
				return ExpectedConditionsTM.elementToBeClickable(by);
			}
			return ExpectedConditionsTM.elementShadowToBeClickable(by, byShadow);			
		} else {
			if (by==null) {
				return ExpectedConditionsTM.elementToBeClickable(webelement);
			} else {
				WebElement childElement = getChildElement(webelement, by);
				if (childElement==null) {
					return null;
				}
				return ExpectedConditionsTM.elementToBeClickable(childElement);
			}
		}
	}
	
	private ExpectedCondition<Boolean> getConditionForElementUnclickable() {
		if (webelement==null) {
			if (byShadow==null) {
				return ExpectedConditionsTM.not(ExpectedConditions.elementToBeClickable(by));
			}
			return ExpectedConditionsTM.not(ExpectedConditionsTM.elementShadowToBeClickable(by, byShadow));			
		} else {
			if (by==null) {
				return ExpectedConditionsTM.not(ExpectedConditionsTM.elementToBeClickable(webelement));
			} else {
				WebElement childElement = getChildElement(webelement, by);
				if (childElement==null) {
					return null;
				}
				return ExpectedConditionsTM.not(ExpectedConditionsTM.elementToBeClickable(childElement));
			}
		}
	}
	
	private WebElement getChildElement(WebElement webelement, By child) {
		try {
			return webelement.findElement(child);
		} catch (NoSuchElementException e) {
			return null;
		}
	}
	
	
	
	public static class BuilderState {
		private final State state;
		private final WebDriver driver;
		private WebElement webelement;
		private By by;
		private By byShadow;
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
		public BuilderState byShadow(By byShadow) {
			this.byShadow = byShadow;
			return this;
		}
		public BuilderState wait(int seconds) {
			this.seconds = seconds;
			return this;
		}
		
		public StateElement build() {
			return new StateElement(state, driver, webelement, by, byShadow, seconds);
		}
		public boolean check() {
			return build().check();
		}
	}
}
