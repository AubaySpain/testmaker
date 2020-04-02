package org.jorge2m.testmaker.service.webdriver.pageobject;

import org.jorge2m.testmaker.conf.Channel;
import org.openqa.selenium.By;

public interface ElementPage {

	By getBy();
	
	default By getBy(Channel channel) {
		return getBy();
	}
	default By getBy(Enum<?> app) {
		return getBy();
	}
	default By getBy(Channel channel, Enum<?> app) {
		return getBy();
	}
}
