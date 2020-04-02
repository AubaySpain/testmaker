package org.jorge2m.testmaker.service.webdriver.maker;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.jorge2m.testmaker.conf.Channel;
import org.jorge2m.testmaker.testreports.stepstore.NettrafficStorer;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;

import net.lightbody.bmp.client.ClientUtil;

public interface WebdriverMaker {

	abstract public WebdriverMaker setChannel(Channel channel);
	abstract public WebdriverMaker setNettraffic(boolean nettraffic);
	abstract public WebDriver build();
	
	default Proxy getProxyForNettraffic() {
		new NettrafficStorer();
		Proxy seleniumProxy = ClientUtil.createSeleniumProxy(NettrafficStorer.getProxy());
		return seleniumProxy;
	}

	default LoggingPreferences getLogsWebDriverEnabled() {
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

	static void deleteCookiesAndSetTimeouts(WebDriver driver) {
		if (driver!=null) {
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			driver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		}
	}
}
