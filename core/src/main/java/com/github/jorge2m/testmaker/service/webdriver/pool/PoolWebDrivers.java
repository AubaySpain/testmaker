package com.github.jorge2m.testmaker.service.webdriver.pool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestRunTM;
import com.github.jorge2m.testmaker.service.webdriver.maker.FactoryWebdriverMaker;
import com.github.jorge2m.testmaker.service.webdriver.pool.StoredWebDrv.stateWd;
import com.github.jorge2m.testmaker.testreports.stepstore.NettrafficStorer;

import static com.github.jorge2m.testmaker.domain.InputParamsTM.ManagementWebdriver.*;

public class PoolWebDrivers implements Serializable {

	private static final long serialVersionUID = 1L;
	private final List<StoredWebDrv> listWebDrivers = new CopyOnWriteArrayList<>();
	private final SuiteTM suite;
	
	public PoolWebDrivers(SuiteTM suite) {
		this.suite = suite;
	}

	public WebDriver getWebDriver(String driverId, Channel channel, TestCaseTM testCase) {
		String moreDataWdrv = getMoreDataWdrv(driverId, testCase.getTestRunParent());
		WebDriver driver = getFreeWebDriverFromPool(driverId, moreDataWdrv);
		if (driver != null) {
			return driver;
		}
		return createAndStoreNewWebDriver(driverId, channel, testCase, moreDataWdrv);
	}

	public void quitWebDriver(WebDriver driver, TestRunTM testRun) {
		var inputData = testRun.getSuiteParent().getInputParams();
		boolean netAnalysis = inputData.isNetAnalysis();
		if (netAnalysis) {
			NettrafficStorer.stopNetTrafficThread();
		}

		if (inputData.getTypeManageWebdriver()==RECYCLE) {
			deleteAllCookies(driver);
			markWebDriverAsFreeInPool(driver);
		} else {
			removeWebDriverFromPool(driver);
			try {
				if (driver!=null) {
					driver.quit();
				}
			}
			catch (Exception e) {
				suite.getLogger().error("Problem deleging WebDriver",  e);
			}
		}
	}
	
	private void deleteAllCookies(WebDriver driver) {
		try {
			driver.manage().deleteAllCookies();
		} 
		catch (Exception e) {
			suite.getLogger().warn("Problem deleting cookies for reciclye webdriver %s", e.getMessage());
		}
	}

	private WebDriver createAndStoreNewWebDriver(
			String driverId, Channel channel, TestCaseTM testCase, String moreDataWdrv) {
		boolean netAnalysis = testCase.getSuiteParent().getInputParams().isNetAnalysis();
		WebDriver driver = 
			FactoryWebdriverMaker.make(testCase)
				.setChannel(channel)
				.setNettraffic(netAnalysis)
				.build();

//	    var eventListener = new WebDriverListenerExample();
//	    EventFiringDecorator<WebDriver> decorator = new EventFiringDecorator<>(eventListener);
//	    WebDriver driver = decorator.decorate(driverOrigin);
		
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
		Iterator<StoredWebDrv> itStrWd = listWebDrivers.iterator();
		suite.getLogger().debug(": Buscando WebDriver free. Type {}, moreDataWrdrv {}", driverId, moreDataWdrv);
		boolean encontrado = false;
		while (itStrWd.hasNext() && !encontrado) {
			StoredWebDrv strWd = itStrWd.next();
			if (strWd.isFree() &&
				strWd.getDriver().compareTo(driverId)==0 &&
				(strWd.getMoreDataWdrv().equals(moreDataWdrv) || strWd.getMoreDataWdrv().compareTo(moreDataWdrv)==0)) {
				webdriverFree = strWd.getWebDriver();
				encontrado = true;
				strWd.markAsBusy();
				suite.getLogger().debug(
					"Encontrado -> Mark as Busy WebDriver: {} (state: {}, driver: {}, moreDataWdrv: {})", 
					strWd.getWebDriver(), strWd.getState(), strWd.getDriver(), strWd.getMoreDataWdrv());
			}
		}

		if (!encontrado) {
			suite.getLogger().debug("No encontrado Webdriver free. Type: {}, moreDataWrdrv: {}", driverId, moreDataWdrv);
		}
		return webdriverFree;
	}

	private void markWebDriverAsFreeInPool(WebDriver driver) {
		StoredWebDrv strWd = searchWebDriver(driver);
		if (strWd != null) {
			strWd.markAsFree();
			suite.getLogger().debug(
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
		listWebDrivers.remove(strWd);
		suite.getLogger().debug(
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
		listWebDrivers.add(strWd);
		suite.getLogger().debug("Alta Stored WebDriver: {} (state: {}, type: {}, moreDataWdrv: {})", driver, state, driverId, moreDataWdrv);
	}

	private StoredWebDrv searchWebDriver(WebDriver driver) {
		StoredWebDrv strWdRet = null;
		Iterator<StoredWebDrv> itStrWd = listWebDrivers.iterator();
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
		for (StoredWebDrv strWd : listWebDrivers) {
			try {
				strWdToDelete.add(strWd);
				strWd.getWebDriver().quit();
			}
			catch (Exception e) {
				suite.getLogger().error("Problem removing all WebDrivers", e);
			}
		}

		listWebDrivers.removeAll(strWdToDelete);
		suite.getLogger().info("Removed all WebDriver");
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
			moreDataWdrv = testRun.getSuiteParent().getInputParams().getBStackDevice();
			break;

		//En el resto de tipos de WebDriver no habrá información específica sobre el WebDriver / Dispositivo de ejecución
		default:
			moreDataWdrv = "";
		}

		return moreDataWdrv;
	}
	
	
//	public class WebDriverListenerExample implements WebDriverListener {
//
//		@Override
//	    public void beforeGet(WebDriver driver, String url) {
//	        System.out.println("Before navigating to URL: " + url);
//	    }
//
//	}	
}