package com.github.jorge2m.testmaker.service.webdriver.pageobject;

import java.util.List;
import java.util.regex.Pattern;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ExpectedConditionsTM {

	private ExpectedConditionsTM() {
		// Utility class
	}
	
	public static ExpectedCondition<WebElement> presenceOfShadowElementLocated(final By locator, final By shadowLocator) {
	    return new ExpectedCondition<WebElement>() {
	    	@Override
	    	public WebElement apply(WebDriver driver) {
	    		return driver.findElement(locator).getShadowRoot().findElement(shadowLocator);
	    	}

	    	@Override
	    	public String toString() {
	    		return "presence of shadow element located by: " + locator + " and element in shadow: " + shadowLocator;
	    	}
	    };
	}	
	
	public static ExpectedCondition<WebElement> visibilityOfShadowElementLocated(final By locator, final By shadowLocator) {
		return new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver driver) {
				try {
					return elementIfVisible(driver.findElement(locator).getShadowRoot().findElement(shadowLocator));
				} catch (StaleElementReferenceException | NoSuchElementException e) {
					// Returns null because the element is no longer or not present in DOM.
					return null;
				}
			}

			@Override
			public String toString() {
	    		return "visibility of shadow element located by: " + locator + " and element in shadow: " + shadowLocator;
			}
		};
	}
	
	public static ExpectedCondition<Boolean> invisibilityOfShadowElementLocated(final By locator, final By shadowLocator) {
		return new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return !(driver.findElement(locator).getShadowRoot().findElement(shadowLocator).isDisplayed());
				} catch (NoSuchElementException | StaleElementReferenceException e) {
					// Returns true because the element is not present in DOM. The
					// try block checks if the element is present but is invisible.
					return true;
				}
			}

			@Override
			public String toString() {
	    		return "invisibility of shadow element located by: " + locator + " and element in shadow: " + shadowLocator;
			}
		};
	}	
	
	public static ExpectedCondition<WebElement> elementShadowToBeClickable(final By locator, final By shadowLocator) {
		return new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver driver) {
				WebElement element = visibilityOfShadowElementLocated(locator, shadowLocator).apply(driver);
				try {
					if (element != null && element.isEnabled()) {
						return element;
					}
					return null;
				} catch (StaleElementReferenceException e) {
					return null;
				}
			}

			@Override
			public String toString() {
				return "element to be clickable: " + locator;
			}
		};
	}
	
	private static WebElement elementIfVisible(WebElement element) {
		return element.isDisplayed() ? element : null;
	}	

	public static ExpectedCondition<Boolean> titleIs(final String title) {
		return ExpectedConditions.titleIs(title);
	}

	public static ExpectedCondition<Boolean> titleContains(final String title) {
		return ExpectedConditions.titleContains(title);
	}

	public static ExpectedCondition<Boolean> urlToBe(final String url) {
		return ExpectedConditions.urlToBe(url);
	}

	public static ExpectedCondition<Boolean> urlContains(final String fraction) {
		return ExpectedConditions.urlContains(fraction);
	}

	public static ExpectedCondition<Boolean> urlMatches(final String regex) {
		return ExpectedConditions.urlMatches(regex);
	}

	public static ExpectedCondition<WebElement> presenceOfElementLocated(final By locator) {
		return ExpectedConditions.presenceOfElementLocated(locator);
	}

	  public static ExpectedCondition<WebElement> visibilityOfElementLocated(final By locator) {
		  return ExpectedConditions.visibilityOfElementLocated(locator);
	  }

	  public static ExpectedCondition<List<WebElement>> visibilityOfAllElementsLocatedBy(
	    final By locator) {
		  return ExpectedConditions.visibilityOfAllElementsLocatedBy(locator);
	  }

	  public static ExpectedCondition<List<WebElement>> visibilityOfAllElements(
	    final WebElement... elements) {
		  return ExpectedConditions.visibilityOfAllElements(elements);
	  }

	  public static ExpectedCondition<List<WebElement>> visibilityOfAllElements(
	    final List<WebElement> elements) {
		  return ExpectedConditions.visibilityOfAllElements(elements);
	  }

	  public static ExpectedCondition<WebElement> visibilityOf(final WebElement element) {
		  return ExpectedConditions.visibilityOf(element);
	  }

	  public static ExpectedCondition<List<WebElement>> presenceOfAllElementsLocatedBy(
	    final By locator) {
		  return ExpectedConditions.presenceOfAllElementsLocatedBy(locator);
	  }

	  public static ExpectedCondition<Boolean> textToBePresentInElement(final WebElement element,
	                                                                    final String text) {
		  return ExpectedConditions.textToBePresentInElement(element, text);
	  }

	  public static ExpectedCondition<Boolean> textToBePresentInElementLocated(final By locator,
	                                                                           final String text) {
		  return ExpectedConditions.textToBePresentInElementLocated(locator, text);
	  }

	  public static ExpectedCondition<Boolean> textToBePresentInElementValue(final WebElement element,
	                                                                         final String text) {
		  return ExpectedConditions.textToBePresentInElementValue(element, text);
	  }

	  public static ExpectedCondition<Boolean> textToBePresentInElementValue(final By locator,
	                                                                         final String text) {
		  return ExpectedConditions.textToBePresentInElementValue(locator, text);
	  }

	  public static ExpectedCondition<WebDriver> frameToBeAvailableAndSwitchToIt(
	    final String frameLocator) {
		  return ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator);
	  }

	  public static ExpectedCondition<WebDriver> frameToBeAvailableAndSwitchToIt(final By locator) {
	    	return ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator);
	  }

	  public static ExpectedCondition<WebDriver> frameToBeAvailableAndSwitchToIt(
	    final int frameLocator) {
		  return ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator);
	  }

	  public static ExpectedCondition<WebDriver> frameToBeAvailableAndSwitchToIt(
	    final WebElement frameLocator) {
		  return ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator);
	  }

	  public static ExpectedCondition<Boolean> invisibilityOfElementLocated(final By locator) {
		  return ExpectedConditions.invisibilityOfElementLocated(locator);
	  }

	  public static ExpectedCondition<Boolean> invisibilityOfElementWithText(final By locator,
	                                                                         final String text) {
		  return ExpectedConditions.invisibilityOfElementWithText(locator, text);
	  }

	  public static ExpectedCondition<WebElement> elementToBeClickable(final By locator) {
		  return ExpectedConditions.elementToBeClickable(locator);
	  }

	  public static ExpectedCondition<WebElement> elementToBeClickable(final WebElement element) {
		  return ExpectedConditions.elementToBeClickable(element);
	  }

	  public static ExpectedCondition<Boolean> stalenessOf(final WebElement element) {
		  return ExpectedConditions.stalenessOf(element);
	  }

	  public static <T> ExpectedCondition<T> refreshed(final ExpectedCondition<T> condition) {
		  return ExpectedConditions.refreshed(condition);
	  }

	  public static ExpectedCondition<Boolean> elementToBeSelected(final WebElement element) {
	    return ExpectedConditions.elementSelectionStateToBe(element, true);
	  }

	  public static ExpectedCondition<Boolean> elementSelectionStateToBe(final WebElement element,
	                                                                     final boolean selected) {
		  return ExpectedConditions.elementSelectionStateToBe(element, selected);
	  }

	  public static ExpectedCondition<Boolean> elementToBeSelected(final By locator) {
	    return ExpectedConditions.elementSelectionStateToBe(locator, true);
	  }

	  public static ExpectedCondition<Boolean> elementSelectionStateToBe(final By locator,
	                                                                     final boolean selected) {
	    return ExpectedConditions.elementSelectionStateToBe(locator, selected);
	  }

	  public static ExpectedCondition<Alert> alertIsPresent() {
		  return ExpectedConditions.alertIsPresent();
	  }

	  public static ExpectedCondition<Boolean> numberOfWindowsToBe(final int expectedNumberOfWindows) {
		  return ExpectedConditions.numberOfWindowsToBe(expectedNumberOfWindows);
	  }

	  public static ExpectedCondition<Boolean> not(final ExpectedCondition<?> condition) {
		  return ExpectedConditions.not(condition);
	  }

	  public static ExpectedCondition<Boolean> attributeToBe(
	    final By locator,
	    final String attribute,
	    final String value) {
	    	return ExpectedConditions.attributeToBe(locator, attribute, value);
	  }

	  public static ExpectedCondition<Boolean> textToBe(final By locator, final String value) {
		  return ExpectedConditions.textToBe(locator, value);
	  }

	  public static ExpectedCondition<Boolean> textMatches(final By locator, final Pattern pattern) {
		  return ExpectedConditions.textMatches(locator, pattern);
	  }

	  public static ExpectedCondition<List<WebElement>> numberOfElementsToBeMoreThan(final By locator,
	                                                                                 final Integer number) {
		  return ExpectedConditions.numberOfElementsToBeMoreThan(locator, number);
	  }

	  public static ExpectedCondition<List<WebElement>> numberOfElementsToBeLessThan(final By locator,
	                                                                                 final Integer number) {
	    return ExpectedConditions.numberOfElementsToBeLessThan(locator, number);
	  }

	  public static ExpectedCondition<List<WebElement>> numberOfElementsToBe(final By locator,
	                                                                         final Integer number) {
		  return ExpectedConditions.numberOfElementsToBe(locator, number);
	  }

	  public static ExpectedCondition<Boolean> domPropertyToBe(final WebElement element,
	                                                           final String property,
	                                                           final String value) {
		  return ExpectedConditions.domPropertyToBe(element, property, value);
	  }

	  public static ExpectedCondition<Boolean> domAttributeToBe(final WebElement element,
	                                                            final String attribute,
	                                                            final String value) {
		  return ExpectedConditions.domAttributeToBe(element, attribute, value);
	  }

	  public static ExpectedCondition<Boolean> attributeToBe(final WebElement element,
	                                                         final String attribute,
	                                                         final String value) {
		  return ExpectedConditions.attributeToBe(element, attribute, value);
	  }

	  public static ExpectedCondition<Boolean> attributeContains(final WebElement element,
	                                                             final String attribute,
	                                                             final String value) {
		  return ExpectedConditions.attributeContains(element, attribute, value);
	  }

	  public static ExpectedCondition<Boolean> attributeContains(final By locator,
	                                                             final String attribute,
	                                                             final String value) {
		  return ExpectedConditions.attributeContains(locator, attribute, value);
	  }

	  public static ExpectedCondition<Boolean> attributeToBeNotEmpty(final WebElement element,
	                                                                 final String attribute) {
		  return ExpectedConditions.attributeToBeNotEmpty(element, attribute);
	  }

	  public static ExpectedCondition<List<WebElement>> visibilityOfNestedElementsLocatedBy(
	    final By parent,
	    final By childLocator) {
		  return ExpectedConditions.visibilityOfNestedElementsLocatedBy(parent, childLocator);
	  }

	  public static ExpectedCondition<List<WebElement>> visibilityOfNestedElementsLocatedBy(
	    final WebElement element, final By childLocator) {
		  return ExpectedConditions.visibilityOfNestedElementsLocatedBy(element, childLocator);
	  }

	  public static ExpectedCondition<WebElement> presenceOfNestedElementLocatedBy(
	    final By locator, final By childLocator) {
		  return ExpectedConditions.presenceOfNestedElementLocatedBy(locator, childLocator);
	  }

	  public static ExpectedCondition<WebElement> presenceOfNestedElementLocatedBy(
	    final WebElement element, final By childLocator) {
		  return ExpectedConditions.presenceOfNestedElementLocatedBy(element, childLocator);
	  }

	  public static ExpectedCondition<List<WebElement>> presenceOfNestedElementsLocatedBy(
	    final By parent, final By childLocator) {
		  return ExpectedConditions.presenceOfNestedElementsLocatedBy(parent, childLocator);
	  }

	  public static ExpectedCondition<Boolean> invisibilityOfAllElements(final WebElement... elements) {
		  return ExpectedConditions.invisibilityOfAllElements(elements);
	  }

	  public static ExpectedCondition<Boolean> invisibilityOfAllElements(final List<WebElement> elements) {
		  return ExpectedConditions.invisibilityOfAllElements(elements);
	  }

	  public static ExpectedCondition<Boolean> invisibilityOf(final WebElement element) {
		  return ExpectedConditions.invisibilityOf(element);
	  }

	  public static ExpectedCondition<Boolean> or(final ExpectedCondition<?>... conditions) {
		  return ExpectedConditions.or(conditions);
	  }

	  public static ExpectedCondition<Boolean> and(final ExpectedCondition<?>... conditions) {
		  return ExpectedConditions.and(conditions);
	  }

	  public static ExpectedCondition<Boolean> javaScriptThrowsNoExceptions(final String javaScript) {
		  return ExpectedConditions.javaScriptThrowsNoExceptions(javaScript);
	  }

	  public static ExpectedCondition<Object> jsReturnsValue(final String javaScript) {
		  return ExpectedConditions.jsReturnsValue(javaScript);
	  }	
	
}
