package com.github.jorge2m.testmaker.service.webdriver.maker;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import com.github.jorge2m.testmaker.conf.Channel;

import io.github.bonigarcia.wdm.EdgeDriverManager;

class EdgedriverMaker implements WebdriverMaker {
	
	//Nota: si se modifica la versión sería conveniente regenerar la AMI correspondiente al Robotest en Cloud
	final static String EdgeDriverVersion = "75.0.137.0";
	Channel channel = Channel.desktop;
	EdgeOptions options = new EdgeOptions();
	boolean headless = false;
	boolean nettraffic = false;
	
	private EdgedriverMaker() {
		setDriverEdge();
	}
	
	static EdgedriverMaker getNew() {
		return (new EdgedriverMaker());
	}
	
    @Override
	public WebdriverMaker setChannel(Channel channel) {
		this.channel = channel;
		return this;
	}
    
    @Override
	public WebdriverMaker setNettraffic(boolean nettraffic) {
    	this.nettraffic = nettraffic;
    	return this;
    }

    @Override
	public WebDriver build() {
    	return (new EdgeDriver(options));
	}
    
    private static void setDriverEdge() {        
        EdgeDriverManager.edgedriver().version(EdgeDriverVersion).setup();
    }
}
