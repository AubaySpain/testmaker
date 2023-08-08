package com.github.jorge2m.testmaker.service.webdriver.maker;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.testreports.stepstore.ReverseProxy;

import net.lightbody.bmp.client.ClientUtil;

public abstract class DriverMaker {

	Channel channel = Channel.desktop;
	boolean nettraffic = false;
	
	abstract public String getTypeDriver();
	abstract public WebDriver build();
	
	public DriverMaker setChannel(Channel channel) {
		this.channel = channel;
		return this;
	}

	public DriverMaker setNettraffic(boolean nettraffic) {
		this.nettraffic = nettraffic;
		return this;
	}
	
	Proxy getProxyForNettraffic() {
		Proxy seleniumProxy = ClientUtil.createSeleniumProxy(ReverseProxy.getProxy());
		return seleniumProxy;
	}

	LoggingPreferences getLogsWebDriverEnabled() {
		//Configuramos la recopilación de logs a nivel de WebDriver
		LoggingPreferences logs = new LoggingPreferences();
		logs.enable(LogType.BROWSER, Level.SEVERE);
		logs.enable(LogType.CLIENT, Level.SEVERE);
		logs.enable(LogType.DRIVER, Level.OFF);
		logs.enable(LogType.PERFORMANCE, Level.SEVERE);
		logs.enable(LogType.PROFILER, Level.SEVERE);
		logs.enable(LogType.SERVER, Level.SEVERE);
		return logs;
	}

	void deleteCookiesAndSetTimeouts(WebDriver driver) {
		if (driver!=null) {
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			driver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		}
	}
}
