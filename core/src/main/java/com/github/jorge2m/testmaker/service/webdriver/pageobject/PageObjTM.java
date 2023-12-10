package com.github.jorge2m.testmaker.service.webdriver.pageobject;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.CLICKABLE;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.service.webdriver.maker.FactoryWebdriverMaker.EmbeddedDriver;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.ClickElement.BuilderClick;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.SelectElement.BuilderSelect;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.BuilderState;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

public class PageObjTM {

	public final WebDriver driver;
	
	public PageObjTM(WebDriver driver) {
		this.driver = driver;
	}
	public PageObjTM() {
		this.driver = TestMaker.getDriverTestCase();
	}

	//Click
	public BuilderClick click(By by) {
		return new BuilderClick(by, driver);
	}
	public BuilderClick click(WebElement webelement) {
		return new BuilderClick(webelement, driver);
	}
	public static BuilderClick click(By by, WebDriver driver) {
		return new BuilderClick(by, driver);
	}
	public static BuilderClick click(WebElement webelement, WebDriver driver) {
		return new BuilderClick(webelement, driver);
	}
	
	//State
	public BuilderState state(State state, By by) {
		return new BuilderState(state, by, driver);
	}
	public BuilderState state(State state, WebElement webelement) {
		return new BuilderState(state, webelement, driver);
	}
	public static BuilderState state(State state, By by, WebDriver driver) {
		return new BuilderState(state, by, driver);
	}
	public static BuilderState state(State state, WebElement webelement, WebDriver driver) {
		return new BuilderState(state, webelement, driver);
	}
	
	//Select
	public BuilderSelect select(By by, String value) {
		return new BuilderSelect(by, value, driver);
	}
	public BuilderSelect select(WebElement webelement, String value) {
		return new BuilderSelect(webelement, value, driver);
	}
	public static BuilderSelect select(By by, String value, WebDriver driver) {
		return new BuilderSelect(by, value, driver);
	}
	public static BuilderSelect select(WebElement webelement, String value, WebDriver driver) {
		return new BuilderSelect(webelement, value, driver);
	}
	

	//Selenium Utils
    public static boolean titleContainsUntil(final WebDriver driver, final String title, int seconds) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(seconds)).until(ExpectedConditions.titleContains(title));
            return true;
        } 
        catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }    
    
    public static int getNumElementsVisible(WebDriver driver, By by) {
        int numberOfElements = 0;
        for (WebElement element : driver.findElements(by)) {
            if (element.isDisplayed()) {
                numberOfElements+=1;
            }
        }
        
        return numberOfElements;
    }
    
    public static WebElement getElementVisible(WebDriver driver, By by) {
        for (WebElement element : driver.findElements(by)) {
            if (element.isDisplayed()) {
                return element;
            }
        }
        
        return null;
    }
    
    public static WebElement getElementVisible(WebElement elementInput, By by) {
        for (WebElement element : elementInput.findElements(by)) {
            if (element.isDisplayed()) {
                return element;
            }
        }
        
        return null;
    }
    
    public static WebElement getElementWithSizeNot0(WebDriver driver, By by) {
        for (WebElement element : driver.findElements(by)) {
            if (element.getSize().height!=0 || element.getSize().width!=0) {
                return element;
            }
        }
        
        return null;
    }
    
    public static List<WebElement> getElementsVisible(WebDriver driver, By by) {
        List<WebElement> listaReturn = new ArrayList<>();
        for (WebElement element : driver.findElements(by)) {
            if (element.isDisplayed()) {
                listaReturn.add(element);
            }
        }
        
        return listaReturn;
    }
    
    public static WebElement getElementWeb(By byElem, WebDriver driver) {
    	try {
    		return (driver.findElement(byElem));
    	}
    	catch (NoSuchElementException e) {
    		return null;
    	}
    }
    
	public static WebElement getElementClickable(WebDriver driver, By by) {
		for (WebElement element : driver.findElements(by)) {
			if (PageObjTM.state(CLICKABLE, element, driver).check()) {
				return element;
			}
		}
		return null;
	}
    public static WebElement getElementClickable(WebElement elementInput, By by, WebDriver driver) {
        for (WebElement element : elementInput.findElements(by)) {
        	if (PageObjTM.state(CLICKABLE, element, driver).check()) {
                return element;
            }
        }
        
        return null;
    }
    
    public static boolean currentURLContains(final String literal, int seconds, WebDriver driver) {
        ExpectedCondition<Boolean> currentURLContains = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                return (Boolean.valueOf(d.getCurrentUrl().contains(literal)));
            }
        };
        
        try {
            new WebDriverWait(driver, Duration.ofSeconds(seconds)).until(currentURLContains);
            return true;
        } 
        catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    public static boolean isAlertPresent(final WebDriver driver) {
        try {
            driver.switchTo().alert();
            return true;
        } 
        catch (NoAlertPresentException e) {
            return false;
        }
    }

    /**
     * Función que reintenta la introducción de un texto en un campo de input n veces hasta que el texto introducido es correcto (es para paliar un bug de selenium según el cual en
     * ocasiones se introducen carácteres extraños en los campos de input)
     * @param numRetry  Número máximo de reintentos
     */
    public static void sendKeysWithRetry(String keys, By by, int numRetry, WebDriver driver) {
        sendKeysWithRetry(numRetry, driver.findElement(by), keys);
    }
    
    public static void ifNotValueSetedSendKeysWithRetry(int numRetry, String keys, By by, WebDriver driver) {
    	ifNotValueSetedSendKeysWithRetry(numRetry, driver.findElement(by), keys);
    }
    
    public static void ifNotValueSetedSendKeysWithRetry(int numRetry, WebElement input, String keys) {
    	boolean valueSeted = input.getAttribute("value").compareTo(keys)==0;
    	if (!valueSeted) {
    		sendKeysWithRetry(numRetry, input, keys);
    	}
    }   
    
    public static void sendKeysWithRetry(int numRetry, WebElement input, CharSequence... keys) {
    	String valueExpected = toString(keys);
        int i = 0;
        do {
            input.clear();
            input.sendKeys(keys);
            i += 1;
        } 
        while (input.getAttribute("value").compareTo(valueExpected) != 0 && i < numRetry);
    }    
    
    private static String toString(CharSequence... keys) { 
    	final StringBuilder sb = new StringBuilder();
    	for (CharSequence key : keys) {
	    	sb.append(key);
    	}
    	return sb.toString();
    }

    public static void waitForPageLoaded(WebDriver driver) {
        waitForPageLoaded(driver, 30/*waitSeconds*/);
    }

    /**
     * Espera a que la página esté cargada (en algunas condiciones ¿AJAX? parece que no acaba de funcionar del todo bien)
     */
    public static void waitForPageLoaded(WebDriver driver, int waitSeconds) {
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(final WebDriver webdriverParam) {
                boolean pageLoaded = pageLoaded(webdriverParam);
                return Boolean.valueOf(pageLoaded);
            }
        };
       
        try {
            new WebDriverWait(driver, Duration.ofSeconds(waitSeconds)).until(expectation);
        } catch (org.openqa.selenium.TimeoutException e) {
            // Si se ha producido Timeout, paramos la carga de la página y grabamos un log
            Actions action = new Actions(driver);
            action.sendKeys("Keys.ESCAPE").build().perform();
            Log4jTM.getLogger().warn("Problem waiting for page Loaded", e);
        }

        hesitate(250);
        waitForAjax(driver, 30/*waitSeconds*/);
    }
    
    /**
     * Indica si la página está cargada
     */
    public static boolean pageLoaded(WebDriver driver) {
        boolean pageLoaded = false;
        Object domContentLoaded = null;
        try {
            //Este evento funciona en todos los navegadores. Indica que el DOM está cargado (pero no espera por imágenes, estilos...)
            domContentLoaded = ((JavascriptExecutor) driver).executeScript("return window.performance.timing.domContentLoadedEventEnd");
        }
        catch (JavascriptException e) {
        	Log4jTM.getLogger().warn("Problem obtaining window.performance.timing.domContentLoadedEventEnd", e);
        }
        
        //Este evento no funciona en Safari (siempre retorna "complete"). Indica que el DOM está cargado (incluye la carga de imágenes, estilos...)
        Object readyState = ((JavascriptExecutor)driver).executeScript("return document.readyState");
        if (readyState!=null && readyState.equals("complete") && 
            domContentLoaded!=null && domContentLoaded.toString().compareTo("0")!=0) {
            pageLoaded = true;
        }
        return pageLoaded;
    }

    private static void hesitate(final long duration) {
        try {
            Thread.sleep(duration);
        } 
        catch (InterruptedException e) {
            /*
             * 
             */
        }
    }

    // En Beta (no tengo claro que todo funcione correctamente)
    public static void waitForAjax(final WebDriver driver, final int timeoutInSeconds) {
        try {
            if (driver instanceof JavascriptExecutor) {
                JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
                for (int i = 0; i < timeoutInSeconds; i++) {
                    Object numberOfAjaxConnections = jsDriver.executeScript("return jQuery.active");
                    // return should be a number
                    if (numberOfAjaxConnections instanceof Long) {
                        Long n = (Long) numberOfAjaxConnections;
                        if (n.longValue() == 0L) {
                            break;
                        }
                    }
                    Thread.sleep(1000);
                }
            } else {
            	Log4jTM.getLogger().debug("Web driver: {} cannot execute javascript");
            }
        } 
        catch (Exception e) {
        	Log4jTM.getLogger().debug("Problem waiting for jQuery.active. "+ e.getClass().getName() + ". " + e.getMessage());
        }
    }

    // Seleccionamos la pantalla no-principal (normalmente un popup)
    public static String switchToAnotherWindow(final WebDriver driver, final String mainWindowHandle) {
        String popupHandle = "";
        
        // Obtenemos todas las pantallas abiertas
        Set<String> s = driver.getWindowHandles();

        // Iteramos hasta encontrar una pantalla no principal (-> Es el popup)
        Iterator<String> ite = s.iterator();
        while (ite.hasNext()) {
            popupHandle = ite.next().toString();
            if (!popupHandle.contains(mainWindowHandle)) {
                driver.switchTo().window(popupHandle);
            }
        }
        
        return(popupHandle);
    }
    
    public static boolean switchToPopupThatContainsTitle(WebDriver driver, String title) {
        String windowParent = driver.getWindowHandle();
        Set<String> availableWindows = driver.getWindowHandles();
        for (String window : availableWindows) {
            if (!windowParent.equals(window)) {
                //Cambiamos a la ventana encontrada
                driver.switchTo().window(window);
                if (driver.getTitle().toLowerCase().contains(title)) {
                    return true;
                }
                
                //Si no se trata del popup que buscamos restauramos la ventana padre
                driver.switchTo().window(windowParent);
            }
        }
        
        return false;
    }
    
    public static String acceptAlertIfExists(WebDriver driver) {
        try {
            Alert alert = driver.switchTo().alert();
            String textAlert = alert.getText();
            alert.accept();
            return textAlert;
        } 
        catch (NoAlertPresentException e) {
            return "";
        }
    }    
    
    /**
     * An expectation for checking WebElement with given locator has attribute which contains specific value
     * @param element   used to check it's parameters
     * @param attribute used to define css or html attribute
     * @param value     used as expected attribute value
     * @return Boolean true when element has css or html attribute which contains the value
     */
    public static ExpectedCondition<Boolean> attributeContains(final WebElement element, final String attribute, final String value) {
        return new ExpectedCondition<Boolean>() {
            private String currentValue = null;
    
            @Override
            public Boolean apply(WebDriver driver) {
              Boolean contains = Boolean.valueOf(false);
              try {
                this.currentValue = element.getAttribute(attribute);
                if (this.currentValue == null || this.currentValue.isEmpty()) {
                	this.currentValue = element.getCssValue(attribute);
                }
                contains = Boolean.valueOf(this.currentValue.contains(value));
              } 
              catch (Exception e) {
                  //
              }
              
              return contains;
            }
    
            @Override
            public String toString() {
                return String.format("value to contain \"%s\". Current value: \"%s\"", value, this.currentValue);
            }
        };
    }    
    
    public static void loadUrlInAnotherTabTitle(String urlToLoad, String titlePant, WebDriver driver) throws Exception {
    	loadUrlInAnotherTabTitle(urlToLoad, titlePant, "", driver);
    }
    
    public static void loadUrlInAnotherMinimumTab(String urlToLoad, String titlePant, WebDriver driver) throws Exception {
    	loadUrlInAnotherTabTitle(urlToLoad, titlePant, "height=100,width=100", driver);
    }
    
    /**
     * @param specs A comma-separated list of items, no whitespaces 
     */
    public static void loadUrlInAnotherTabTitle(String urlToLoad, String titlePant, String specs, WebDriver driver) throws Exception {
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.open('" + urlToLoad + "', '" + titlePant + "', '" + specs + "');");
        driver.switchTo().window(titlePant);
        try {
        	waitForPageLoaded(driver);
        }
        catch (WebDriverException e) {
        	Log4jTM.getLogger().warn("Problem waiting for page loading in another tab", e);
        }
    }
    
    public static void closeTabByTitle(String titleTab, WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.close('" + titleTab + "');");
    }
    
    public static void closeTabByTitleAndReturnToWidow(String titleTab, String windowToReturnHandle, WebDriver driver) {
    	closeTabByTitle(titleTab, driver);
    	driver.switchTo().window(windowToReturnHandle);
    }
    
    public static void moveToElement(By byElem, WebDriver driver) {
        WebElement webElem = driver.findElement(byElem);
        moveToElement(webElem, driver);
    }
    
    public static void changeFocusTo(WebElement webElem, WebDriver driver) {
    	moveToElementViaJavaScript(webElem, driver);
    }
    
    public static void moveToElement(WebElement webElem, WebDriver driver) {
        try {
        	moveToElementViaAction(webElem, driver);
        }
        catch (Exception e) {
        	//TODO There is a opened Firefox-Geckodriver bug https://github.com/mozilla/geckodriver/issues/776
        	//when resolved we will eliminate the actual workarround
        	moveToElementViaJavaScript(webElem, driver); 
        }    	
    }
    
    private static void moveToElementViaAction(WebElement webElem, WebDriver driver) {
    	Actions actions = new Actions(driver);
    	actions.moveToElement(webElem).build().perform();
    }
    
    private static void moveToElementViaJavaScript(WebElement webElem, WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", webElem);
    }
    
    public static EmbeddedDriver getTypeDriver(WebDriver driver) {
    	Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
    	String browserName = caps.getBrowserName();
    	return EmbeddedDriver.valueOf(browserName);
    }    
    
    public static void waitMillis(int milliseconds) {
    	try {
    		Thread.sleep(milliseconds);
    	}
    	catch (Exception e) {
    		Log4jTM.getLogger().warn("Problem in wait", e);
    	}
    }
    
    public enum HtmlLocator {TagName, Id, ClassName};
    public static void hideHtmlComponent(HtmlLocator htmlLocator, String idLocator, WebDriver driver) {
    	try {
	    	switch (htmlLocator) {
	    	case TagName:
	    		hideHtmlElementByTag(idLocator, driver);
	    		break;
	    	case Id:
	    		hideHtmlElementById(idLocator, driver);
	    		break;
	    	case ClassName:
	    		hideHtmlElementByClass(idLocator, driver);
	    		break;
	    	}
    	}
    	catch (Exception e) {
    		//Es posible que la operativa funcione aún cuando no ha funcionado el hide
    	}
    }
    
    private static void hideHtmlElementByTag(String tagName, WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
        	"document.getElementsByTagName('" + tagName + "')[0].style.zIndex=99; " + 
        	"document.getElementsByTagName('" + tagName + "')[0].style.display='none'");    	
    }
    
    private static void hideHtmlElementById(String id, WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
        	"document.getElementById('" + id + "')[0].style.zIndex=99; " + 
        	"document.getElementById('" + id + "')[0].style.display='none'");    	
    }
    
    private static void hideHtmlElementByClass(String className, WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
        	"document.getElementsByClassName('" + className + "')[0].style.zIndex=99; " + 
        	"document.getElementsByClassName('" + className + "')[0].style.display='none'");    	
    }
    
	public static void orderElementsByPositionInScreen(List<WebElement> listElements) {
		Collections.sort(
			listElements, 
			new Comparator<WebElement>() {
				@Override
			    public int compare(WebElement banner1, WebElement banner2) {
					Point locationBanner1 = banner1.getLocation();
					Point locationBanner2 = banner2.getLocation();
					if (locationBanner1.y != locationBanner2.y) {
						if (locationBanner1.y > locationBanner2.y) {
							return 1;
						}
						return -1;
					}
					
					if (locationBanner1.x != locationBanner1.x) {
						if (locationBanner1.x > locationBanner2.x) {
							return 1;
						}
						return -1;
					}
					
					return 0;
			    }
			}
		);
	}	
	
}
