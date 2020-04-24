package com.github.jorge2m.testmaker.service.webdriver.maker;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import com.github.jorge2m.testmaker.service.webdriver.maker.FactoryWebdriverMaker.EmbeddedDriver;
import io.github.bonigarcia.wdm.EdgeDriverManager;

class EdgedriverMaker extends DriverMaker {
	
	//Nota: si se modifica la versión sería conveniente regenerar la AMI correspondiente al Robotest en Cloud
	private final static String EdgeDriverVersionDefault = "75.0.137.0";
	private EdgeOptions options = new EdgeOptions();
	
	@Override
	public String getTypeDriver() {
		return EmbeddedDriver.edge.name();
	}
	
	@Override
	public void setupDriverVersion(String driverVersion) {
		if (driverVersion!=null && "".compareTo(driverVersion)!=0) {
			EdgeDriverManager.edgedriver().version(driverVersion).setup();
		} else {
			EdgeDriverManager.edgedriver().version(EdgeDriverVersionDefault).setup();
		}
	}
	
	@Override
	public WebDriver build() {
		return (new EdgeDriver(options));
	}
}
