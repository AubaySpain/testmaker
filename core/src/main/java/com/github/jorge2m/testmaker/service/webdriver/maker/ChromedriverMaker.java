package com.github.jorge2m.testmaker.service.webdriver.maker;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.maker.FactoryWebdriverMaker.EmbeddedDriver;
import com.github.jorge2m.testmaker.service.webdriver.maker.plugins.PluginBrowserFactory;
import com.github.jorge2m.testmaker.service.webdriver.maker.plugins.chrome.PluginChrome;

import static com.github.jorge2m.testmaker.service.webdriver.maker.plugins.chrome.PluginChrome.TypePluginChrome.*;

class ChromedriverMaker extends DriverMaker {
	
	//La versión de ChromeDriver ha de soportar la versión de Chrome instalada en el servidor donde se ejecute TestMaker
	private final boolean isHeadless;
	private final boolean isStartRecord;
	private final String pathTestCase;
	private final String idExecSuite;
	private final String testCase;
	private ChromeOptions options = new ChromeOptions();
	
	public ChromedriverMaker(
			boolean isHeadless, boolean isStartRecord, String pathTestCase, String idExecSuite, String testCase) {
		if (isStartRecord) {
			this.isHeadless = false;
		} else {
			this.isHeadless = isHeadless;
		}
		this.isStartRecord = isStartRecord;
		this.pathTestCase = pathTestCase;
		this.idExecSuite = idExecSuite;
		this.testCase = testCase;
	}
	
	@Override
	public String getTypeDriver() {
		if (isHeadless) {
			return EmbeddedDriver.chromehless.name();
		}
		return EmbeddedDriver.chrome.name();
	}
	
	@Override
	public WebDriver build() {
		initialConfig();
		preBuildConfig();
		var chromeDriver = new ChromeDriverTM(options, idExecSuite, testCase);
		if (channel==Channel.desktop) {
			if (isHeadless) {
				chromeDriver.manage().window().setSize(new Dimension(1920, 1080));
			} else {
				chromeDriver.manage().window().maximize();
			}
		}

		deleteCookiesAndSetTimeouts(chromeDriver);
		return chromeDriver;
	}
		
	private void initialConfig() {
		options.addArguments("--ignore-certificate-errors");	
		options.addArguments("--no-proxy-server");
		options.addArguments("--privileged");
		options.addArguments("--remote-allow-origins=*");
		options.addArguments("enable-automation");
		options.addArguments("--disable-search-engine-choice-screen");

		if (isStartRecord) {
			createPathForEvidencesStore();
			HashMap<String, Object> chromePrefs = new HashMap<>();
			chromePrefs.put("download.default_directory", pathTestCase);
			options.setExperimentalOption("prefs", chromePrefs);
		}
		activateLogs();
	}
	
	private void createPathForEvidencesStore() {
		File directorio = new File(pathTestCase);
		if (!directorio.exists()) {
			directorio.mkdirs();
		}
	}
	
	private void activateLogs() {
		var logs = getLogsWebDriverEnabled();
		options.setCapability("goog:loggingPrefs", logs);
		options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
	}
	
	private void preBuildConfig() {
		if (isHeadless) {
			options.addArguments("--headless=new");
		}
		addPlugins(isHeadless);
		if (nettraffic) {
			configNettrafficSnifer();
		}
		if (channel==Channel.mobile || channel==Channel.tablet) {
			configMobilSimulator();
		}
	}

	/**
	 * Da de alta/asocia en chrome una lista de plugins
	 */
	private void addPlugins(boolean isHeadless) {
		if (!isHeadless) {
			List<PluginChrome.TypePluginChrome> listPlugins = new ArrayList<>();
			listPlugins.add(MANGO_HEADERS);
			if (isStartRecord) {
				listPlugins.add(MOVAVI_SCREEN_RECORDER);
			}
			for (var typePlugin : listPlugins) {
				PluginChrome pluginChrome = PluginBrowserFactory.makePluginChrome(typePlugin);
				pluginChrome.addPluginToChrome(options);
			}
		}
	}

	//TODO se puede pasar el código de generación del Proxy al interface
	private void configNettrafficSnifer() {
		Proxy seleniumProxy = getProxyForNettraffic();
		options.setCapability(CapabilityType.PROXY, seleniumProxy);
	}

	private void configMobilSimulator() {
		Map<String, Object> mobileEmulation = new HashMap<>();
		mobileEmulation.put("deviceName", "Nexus 6");
		if (channel==Channel.tablet) {
//			Map<String, Object> deviceMetrics = new HashMap<String, Object>();
//	        deviceMetrics.put("width", 1280);  
//	        deviceMetrics.put("height", 800); 
//	        deviceMetrics.put("pixelRatio", 2.0);
//	        mobileEmulation.put("deviceMetrics", deviceMetrics);
			mobileEmulation.put("deviceName", "Nexus 10");
		}
		options.setExperimentalOption("mobileEmulation", mobileEmulation);
	}
	
}
