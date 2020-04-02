package org.jorge2m.testmaker.service.webdriver.pageobject;

public enum TypeClick {
    webdriver, 
    javascript;
	
	public static TypeClick next(TypeClick typeClick) {
		switch (typeClick) {
		case webdriver:
			return javascript;
		case javascript:
		default:
			return webdriver;
		}
	}
}
