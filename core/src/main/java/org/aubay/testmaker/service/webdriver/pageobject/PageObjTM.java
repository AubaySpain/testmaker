package org.jorge2m.testmaker.service.webdriver.pageobject;

import org.jorge2m.testmaker.service.webdriver.pageobject.ClickElement.BuilderClick;
import org.jorge2m.testmaker.service.webdriver.pageobject.SelectElement.BuilderSelect;
import org.jorge2m.testmaker.service.webdriver.pageobject.StateElement.BuilderState;
import org.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PageObjTM extends SeleniumUtils {

	public final WebDriver driver;
	
	public PageObjTM(WebDriver driver) {
		this.driver = driver;
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
	
}
