package com.github.jorge2m.testmaker.service.webdriver.pageobject;

public enum TypeClick {
    WEBDRIVER, 
    JAVASCRIPT;
	
	public static TypeClick next(TypeClick typeClick) {
		switch (typeClick) {
		case WEBDRIVER:
			return JAVASCRIPT;
		case JAVASCRIPT:
		default:
			return WEBDRIVER;
		}
	}
	
}
