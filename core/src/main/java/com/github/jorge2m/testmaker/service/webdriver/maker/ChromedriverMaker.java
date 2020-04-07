package com.github.jorge2m.testmaker.service.webdriver.maker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.maker.FactoryWebdriverMaker.EmbebdedDriver;
import com.github.jorge2m.testmaker.service.webdriver.maker.plugins.PluginBrowserFactory;
import com.github.jorge2m.testmaker.service.webdriver.maker.plugins.chrome.PluginChrome;
import com.github.jorge2m.testmaker.service.webdriver.maker.plugins.chrome.PluginChrome.typePluginChrome;

import io.github.bonigarcia.wdm.ChromeDriverManager;

class ChromedriverMaker extends DriverMaker {
	
	//La versión de ChromeDriver ha de soportar la versión de Chrome instalada en el servidor donde se ejecute TestMaker
	private final boolean isHeadless;
	private final static String ChromeDriverVersionDefault = "80.0.3987.106";
	private ChromeOptions options = new ChromeOptions();
	
	public ChromedriverMaker(boolean isHeadless) {
		this.isHeadless = isHeadless;
	}
	
	@Override
	public String getTypeDriver() {
		if (isHeadless) {
			return EmbebdedDriver.chromehless.name();
		}
		return EmbebdedDriver.chrome.name();
	}
	
	@Override
	public void setupDriverVersion(String driverVersion) {
		if (driverVersion!=null && "".compareTo(driverVersion)!=0) {
			ChromeDriverManager.chromedriver().version(driverVersion).setup();
		} else {
			ChromeDriverManager.chromedriver().version(ChromeDriverVersionDefault).setup();
		}
	}
	
	@Override
	public WebDriver build() {
		initialConfig();
		preBuildConfig();
		ChromeDriver driver = new ChromeDriver(options);
		if (channel==Channel.desktop) {
			driver.manage().window().maximize();
		}

		deleteCookiesAndSetTimeouts(driver);
		return driver;
	}

	private void initialConfig() {
		options.addArguments("--ignore-certificate-errors");	
		options.addArguments("--no-proxy-server");
		options.addArguments("--privileged");
		activateLogs();
	}
	
	private void activateLogs() {
		LoggingPreferences logs = getLogsWebDriverEnabled();
		options.setCapability(CapabilityType.LOGGING_PREFS, logs);
		options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
	}
	
	private void preBuildConfig() {
		if (channel!=Channel.movil_web && isHeadless) {
			options.addArguments("--window-size=1920x1080");
		}
		
		options.setHeadless(isHeadless);
		addPlugins(isHeadless);
		if (nettraffic) {
			configNettrafficSnifer();
		}
		if (channel==Channel.movil_web) {
			configMobilSimulator();
		}
	}

	/**
	 * Da de alta/asocia en chrome una lista de plugins
	 */
	private void addPlugins(boolean isHeadless) {
		if (!isHeadless) {
			//TODO Desde Chrome61 ya es posible desactivar el autoplay directamente desde el navegador (chrome://flags/#autoplay-policy)
			//options.addArguments("--autoplay-policy", "--document-user-activation-required");        
			//Arrancamos Chrome con la extensión HTML5Autoplay para que no se ejecute el autoplay de los vídeos y sea posible paralelizar varios Chromes en una misma máquina
			List<PluginChrome.typePluginChrome> listPlugins = new ArrayList<>();
			listPlugins.add(typePluginChrome.HTML5AutoplayBlocker);
			for (typePluginChrome typePlugin : listPlugins) {
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
		mobileEmulation.put("deviceName", "Nexus 6"); // select the device to emulate
		options.setExperimentalOption("mobileEmulation", mobileEmulation);
	}
}
