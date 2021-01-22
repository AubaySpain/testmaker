package com.github.jorge2m.testmaker.service.webdriver.maker;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import com.github.jorge2m.testmaker.service.webdriver.maker.FactoryWebdriverMaker.EmbeddedDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

class EdgedriverMaker extends DriverMaker {
	
	//Nota: si se modifica la versión sería conveniente regenerar la AMI correspondiente al Robotest en Cloud
	private EdgeOptions options = new EdgeOptions();
	
	@Override
	public String getTypeDriver() {
		return EmbeddedDriver.edge.name();
	}
	
	@Override
	public void setupDriverVersion(String driverVersion) {
		if (driverVersion!=null && "".compareTo(driverVersion)!=0) {
			WebDriverManager.edgedriver().driverVersion(driverVersion).setup();
			//EdgeDriverManager.edgedriver().version(driverVersion).setup();
		} else {
			WebDriverManager.edgedriver().setup();
			//EdgeDriverManager.edgedriver().version(EdgeDriverVersionDefault).setup();
		}
	}
	
	@Override
	public WebDriver build() {
		return (new EdgeDriver(options));
	}
}
