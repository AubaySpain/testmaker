package com.github.jorge2m.testmaker.service.webdriver.maker;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import com.github.jorge2m.testmaker.service.webdriver.maker.FactoryWebdriverMaker.EmbeddedDriver;

class EdgedriverMaker extends DriverMaker {
	
	//Nota: si se modifica la versión sería conveniente regenerar la AMI correspondiente al Robotest en Cloud
	private EdgeOptions options = new EdgeOptions();
	
	@Override
	public String getTypeDriver() {
		return EmbeddedDriver.edge.name();
	}
	
	@Override
	public WebDriver build() {
		return (new EdgeDriver(options));
	}
}
