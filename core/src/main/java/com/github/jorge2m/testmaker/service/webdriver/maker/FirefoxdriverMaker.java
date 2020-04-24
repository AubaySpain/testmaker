package com.github.jorge2m.testmaker.service.webdriver.maker;

import java.awt.Toolkit;

import io.github.bonigarcia.wdm.FirefoxDriverManager;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.maker.FactoryWebdriverMaker.EmbeddedDriver;

class FirefoxdriverMaker extends DriverMaker {
	
	//Nota: si se modifica la versión sería conveniente regenerar la AMI correspondiente al Robotest en Cloud
	private final boolean isHeadless;
	private final static String GeckoDriverVersionDefault = "0.26.0";
	private FirefoxProfile fp = new FirefoxProfile();
	private FirefoxOptions options;
	
	public FirefoxdriverMaker(boolean isHeadless) {
		this.isHeadless = isHeadless;
	}
	
	@Override
	public String getTypeDriver() {
		if (isHeadless) {
			return EmbeddedDriver.firefoxhless.name();
		}
		return EmbeddedDriver.firefox.name();
	}
	
	@Override
	public void setupDriverVersion(String driverVersion) {
		if (driverVersion!=null && "".compareTo(driverVersion)!=0) {
			FirefoxDriverManager.firefoxdriver().version(driverVersion).setup();
		} else {
			FirefoxDriverManager.firefoxdriver().version(GeckoDriverVersionDefault).setup();
		}
	}

	@Override
	public WebDriver build() {
		initialConfig();
		preBuildConfig();
		FirefoxDriver driver = new FirefoxDriver(options);
		resizeBrowserIfNeeded(driver);
		deleteCookiesAndSetTimeouts(driver);
		return driver;
	}

	private void initialConfig() {
		fp.setPreference("network.proxy.type", 0); // Sin proxy (1 -> Con proxy de sistema)
		fp.setPreference("toolkit.startup.max_resumed_crashes", -1); // Desactivar el Safe Mode
		fp.setPreference("browser.sessionstore.postdata", -1); // Desactivar el "Document Expired"
		fp.setPreference("browser.cache.disk.enable", true);
		fp.setPreference("browser.cache.memory.enable", true);
		fp.setPreference("browser.cache.offline.enable", true);
		fp.setPreference("network.http.use-cache", true);
		fp.setPreference("xpinstall.signatures.required", false);
		fp.setPreference("xpinstall.whitelist.required", false);
		fp.setPreference("extensions.checkCompatibility.nightly", false);
		fp.setPreference("startup.homepage_welcome_url.additional", "");
		fp.setPreference("startup.homepage_welcome_url", "");
		setAutoplayOff();
	}
	
	private void setAutoplayOff() {
		fp.setPreference("media.autoplay.default", 1); 
		fp.setPreference("media.autoplay.allow-muted", false);
		fp.setPreference("media.autoplay.enabled", false);
		fp.setPreference("media.ogg.enabled", false); 
		fp.setPreference("media.webm.enabled", false); 
		fp.setPreference("media.windows-media-foundation.enabled", false); 
	}
	
	private void activateLogs() {
		LoggingPreferences logs = getLogsWebDriverEnabled();
		options.setCapability(CapabilityType.LOGGING_PREFS, logs);
		options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
	}
	
	private void preBuildConfig() {
		if (channel==Channel.mobile) {
			configMobilSimulator();
		}
		setFirefoxOptions();
		activateLogs();
		activeLogs();
		if (nettraffic) {
			configNettrafficSnifer();
		}
	}

	private void setFirefoxOptions() {
		//Con FirefoxBinary intentamos eliminar el tema de la excepción
		//"org.openqa.selenium.WebDriverException: Unable to bind to locking port 7054 within 45000 ms" -> Aumentamos de 45 a 60 segundos
		FirefoxBinary fb = new FirefoxBinary();  
		fb.setTimeout(java.util.concurrent.TimeUnit.SECONDS.toMillis(60));
		options = new FirefoxOptions().setBinary(fb);
		options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, "true");
		options.setCapability("acceptSslCerts", true);
		options.setCapability(FirefoxDriver.PROFILE, fp);
		options.setProfile(fp);
		options.setCapability("marionette", true);
		System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, String.valueOf(true));
		options.setHeadless(isHeadless);
	}

	private void activeLogs() {
		LoggingPreferences logs = getLogsWebDriverEnabled();
		options.setCapability(CapabilityType.LOGGING_PREFS, logs);
	}

	private void configNettrafficSnifer() {
		Proxy seleniumProxy = getProxyForNettraffic();
		options.setCapability(CapabilityType.PROXY, seleniumProxy);
	}

	private void configMobilSimulator() {
		fp.setPreference(
				"general.useragent.override", 
				"Mozilla/5.0 (Linux; Android 4.0.4; Galaxy Nexus Build/IMM76B) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.133 Mobile Safari/535.19");
	}

	private void resizeBrowserIfNeeded(WebDriver driver) {
		if (channel==Channel.mobile) {
			//En caso de móvil redimensionaremos el navegador a unas dimensiones tipo 'móvil'
			driver.manage().window().setSize(new Dimension(640, 1136));
		} else {
			//En caso de Desktop maximizamos la ventana
			driver.manage().window().maximize();
			java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			if (screenSize.height<=1024) {
				driver.manage().window().setSize(new Dimension(1920, 1080));
			}
		}
	}
}
