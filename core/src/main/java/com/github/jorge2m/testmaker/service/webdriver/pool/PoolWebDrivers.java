package com.github.jorge2m.testmaker.service.webdriver.pool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.Log4jConfig;
import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.domain.InputParamsTM.ManagementWebdriver;
import com.github.jorge2m.testmaker.domain.suitetree.TestRunTM;
import com.github.jorge2m.testmaker.service.webdriver.maker.FactoryWebdriverMaker;
import com.github.jorge2m.testmaker.service.webdriver.pool.StoredWebDrv.stateWd;
import com.github.jorge2m.testmaker.testreports.stepstore.NettrafficStorer;

/**
 * Clase encargada de gestionar un pool de objetos WebDriver
 * @author jorge.munoz
 *
 */

public class PoolWebDrivers {

	private final List<StoredWebDrv> poolWebDrivers = new CopyOnWriteArrayList<>();

	public WebDriver getWebDriver(String driverId, Channel channel, TestRunTM testRun) {
		String moreDataWdrv = getMoreDataWdrv(driverId, testRun);
		WebDriver driver = getFreeWebDriverFromPool(driverId, moreDataWdrv);
		if (driver != null) {
			return driver;
		}
		return createAndStoreNewWebDriver(driverId, channel, testRun, moreDataWdrv);
	}

	public void quitWebDriver(WebDriver driver, TestRunTM testRun) {
		InputParamsTM inputData = testRun.getSuiteParent().getInputParams();
		boolean netAnalysis = inputData.isNetAnalysis();
		if (netAnalysis) {
			NettrafficStorer.stopNetTrafficThread();
		}

		ManagementWebdriver managementWdrv = inputData.getTypeManageWebdriver();
		switch (managementWdrv) {
		case recycle:
			deleteAllCookies(driver);
			markWebDriverAsFreeInPool(driver);
			break;
		case discard:
			removeWebDriverFromPool(driver);
			try {
				if (driver!=null) {
					driver.quit();
				}
			}
			catch (Exception e) {
				Log4jConfig.pLogger.error("Problem deleging WebDriver",  e);
			}
		}
	}
	private void deleteAllCookies(WebDriver driver) {
		try {
			driver.manage().deleteAllCookies();
		} 
		catch (Exception e) {
			Log4jConfig.pLogger.warn("Problem deleting cookies for reciclye webdriver ", e.getMessage());
		}
	}

	private WebDriver createAndStoreNewWebDriver(String driverId, Channel channel, TestRunTM testRun, String moreDataWdrv) {
		InputParamsTM inputParams = testRun.getSuiteParent().getInputParams();
		boolean netAnalysis = testRun.getSuiteParent().getInputParams().isNetAnalysis();
		String driverVersion = inputParams.getDriverVersion();
		WebDriver driver = 
			FactoryWebdriverMaker.make(testRun)
				.setChannel(channel)
				.setNettraffic(netAnalysis)
				.setupDriverVersionFluent(driverVersion)
				.build();

		storeWebDriver(driver, StoredWebDrv.stateWd.busy, driverId, moreDataWdrv);
		return driver;
	}

	/**
	 * Busca un WebDriver libre entre la lista de webdrivers que sea del tipo especificado en los parámetros de entrada
	 * @param typeWebdrv tipo de WebDriver (Firefox, Chrome, BrowserStack...)
	 * @param moreDataWdrv especifica datos adicionales del WebDriver que necesitamos.
	 *                     actualmente sólo viene informado para el caso de 'BrowserStack' (especificamos el modelo de 'device' documentado en BrowserStack)
	 *                     en el resto de casos viene a "".
	 */
	private synchronized WebDriver getFreeWebDriverFromPool(String driverId, String moreDataWdrv) {
		WebDriver webdriverFree = null;
		Iterator<StoredWebDrv> itStrWd = poolWebDrivers.iterator();
		Log4jConfig.pLogger.debug(": Buscando WebDriver free. Type {}, moreDataWrdrv {}", driverId, moreDataWdrv);
		boolean encontrado = false;
		while (itStrWd.hasNext() && !encontrado) {
			StoredWebDrv strWd = itStrWd.next();
			if (strWd.isFree() &&
				strWd.getDriver().compareTo(driverId)==0) {
				if (strWd.getMoreDataWdrv() == moreDataWdrv ||
					strWd.getMoreDataWdrv().compareTo(moreDataWdrv)==0) {
					webdriverFree = strWd.getWebDriver();
					encontrado = true;
					strWd.markAsBusy();
					Log4jConfig.pLogger.debug(
						"Encontrado -> Mark as Busy WebDriver: {} (state: {}, driver: {}, moreDataWdrv: {})", 
						strWd.getWebDriver(), strWd.getState(), strWd.getDriver(), strWd.getMoreDataWdrv());
				}
			}
		}

		if (!encontrado) {
			Log4jConfig.pLogger.debug("No encontrado Webdriver free. Type: {}, moreDataWrdrv: {}", driverId, moreDataWdrv);
		}
		return webdriverFree;
	}

	private void markWebDriverAsFreeInPool(WebDriver driver) {
		StoredWebDrv strWd = searchWebDriver(driver);
		if (strWd != null) {
			strWd.markAsFree();
			Log4jConfig.pLogger.debug(
				"Mark as Free WebDriver: {} (state: {}, driver: {}, moreDataWdrv: {})", 
				strWd.getWebDriver(), strWd.getState(), strWd.getDriver(), strWd.getMoreDataWdrv());
		}
	}

	private void removeWebDriverFromPool(WebDriver driver) {
		StoredWebDrv strWd = searchWebDriver(driver);
		if (strWd != null) {
			deleteStrWedDriver(strWd);
		}
	}

	private void deleteStrWedDriver(StoredWebDrv strWd) {
		poolWebDrivers.remove(strWd);
		Log4jConfig.pLogger.debug(
			"Removed Stored WebDriver: {} (state: {}, type: {}, moreDataWdrv: {})", 
			strWd.getWebDriver(), strWd.getState(), strWd.getDriver(), strWd.getMoreDataWdrv());
	}

	/**
	 * Almacenamos el webdriver en la lista con los datos especificados en los parámetros
	 * @param state   estado 'busy', 'free'
	 * @param type    tipo de navegador/canal de ejecución de los scripts
	 * @param moreDataDrv especifica datos adicionales del WebDriver que necesitamos.
	 *                    actualmente sólo viene informado para el caso de 'browserstack' (especificamos el modelo de 'device' documentado en browserstack)
	 *                    en el resto de casos viene a "". 
	 */
	private void storeWebDriver(WebDriver driver, stateWd state, String driverId, String moreDataWdrv) {
		StoredWebDrv strWd = new StoredWebDrv(driver, state, driverId, moreDataWdrv);
		poolWebDrivers.add(strWd);
		Log4jConfig.pLogger.debug("Alta Stored WebDriver: {} (state: {}, type: {}, moreDataWdrv: {})", driver, state, driverId, moreDataWdrv);
	}

	private StoredWebDrv searchWebDriver(WebDriver driver) {
		StoredWebDrv strWdRet = null;
		Iterator<StoredWebDrv> itStrWd = poolWebDrivers.iterator();
		boolean encontrado = false;
		while (itStrWd.hasNext() && !encontrado) {
			StoredWebDrv strWd = itStrWd.next();
			if (strWd.getWebDriver() == driver) {
				strWdRet = strWd;
				encontrado = true;
			}
		}
		return strWdRet;
	}

	public void removeAllStrWd() {
		List<StoredWebDrv> strWdToDelete = new ArrayList<>();
		for (StoredWebDrv strWd : poolWebDrivers) {
			try {
				strWdToDelete.add(strWd);
				strWd.getWebDriver().quit();
			}
			catch (Exception e) {
				Log4jConfig.pLogger.error("Problem removing all WebDrivers", e);
			}
		}

		poolWebDrivers.removeAll(strWdToDelete);
		Log4jConfig.pLogger.info("Removed all WebDriver");
	}

	/**
	 * Devuelve datos adicionales del WebDriver que necesitamos.
	 * actualmente sólo lo informaremos para el caso de 'BrowserStack' (devolvemos el modelo de de dispositivo móvil especificado en BrowserStack)
	 * en el resto de casos devolveremos ""
	 */
	private String getMoreDataWdrv(String driverId, TestRunTM testRun) {
		String moreDataWdrv = "";
		switch (driverId) {
		//En el caso de BrowserStack como información específica del WebDriver incluiremos el modelo de dispositivo móvil asociado
		case "browserstack":
			moreDataWdrv = testRun.getSuiteParent().getInputParams().getDeviceBStack();
			break;

		//En el resto de tipos de WebDriver no habrá información específica sobre el WebDriver / Dispositivo de ejecución
		default:
			moreDataWdrv = "";
		}

		return moreDataWdrv;
	}
}