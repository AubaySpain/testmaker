package org.aubay.testmaker.service.webdriver.pool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.aubay.testmaker.conf.Channel;
import org.aubay.testmaker.conf.Log4jConfig;
import org.aubay.testmaker.domain.InputParamsTM;
import org.aubay.testmaker.domain.InputParamsTM.ManagementWebdriver;
import org.aubay.testmaker.domain.suitetree.TestRunTM;
import org.aubay.testmaker.service.webdriver.maker.FactoryWebdriverMaker;
import org.aubay.testmaker.service.webdriver.maker.FactoryWebdriverMaker.WebDriverType;
import org.aubay.testmaker.service.webdriver.maker.brwstack.BrowserStackMobil;
import org.aubay.testmaker.service.webdriver.pool.StoredWebDrv.stateWd;
import org.aubay.testmaker.testreports.stepstore.NettrafficStorer;
import org.openqa.selenium.WebDriver;

/**
 * Clase encargada de gestionar un pool de objetos WebDriver
 * @author jorge.munoz
 *
 */

public class PoolWebDrivers {
    
    private final List<StoredWebDrv> poolWebDrivers = new CopyOnWriteArrayList<>();
    
    public WebDriver getWebDriver(WebDriverType webDriverType, Channel channel, TestRunTM testRun) {
        String moreDataWdrv = getMoreDataWdrv(webDriverType, testRun);
        WebDriver driver = getFreeWebDriverFromPool(webDriverType, moreDataWdrv);
        if (driver != null) {
            return driver;
        }
        return createAndStoreNewWebDriver(webDriverType, channel, testRun, moreDataWdrv);
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
            driver.manage().deleteAllCookies();
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
    
    private WebDriver createAndStoreNewWebDriver (
    		WebDriverType webDriverType, Channel channel, TestRunTM testRun, String moreDataWdrv) {
        boolean netAnalysis = testRun.getSuiteParent().getInputParams().isNetAnalysis();
		WebDriver driver = 
			FactoryWebdriverMaker.make(webDriverType, testRun)
				.setChannel(channel)
				.setNettraffic(netAnalysis)
				.build();
        
        storeWebDriver(driver, StoredWebDrv.stateWd.busy, webDriverType, moreDataWdrv);
        return driver;
    }
    
    /**
     * Busca un WebDriver libre entre la lista de webdrivers que sea del tipo especificado en los parámetros de entrada
     * @param typeWebdrv tipo de WebDriver (Firefox, Chrome, BrowserStack...)
     * @param moreDataWdrv especifica datos adicionales del WebDriver que necesitamos.
     *                     actualmente sólo viene informado para el caso de 'BrowserStack' (especificamos el modelo de 'device' documentado en BrowserStack)
     *                     en el resto de casos viene a "".
     */
    private synchronized WebDriver getFreeWebDriverFromPool(WebDriverType typeWdrv, String moreDataWdrv) {
        WebDriver webdriverFree = null;
        Iterator<StoredWebDrv> itStrWd = poolWebDrivers.iterator();
        Log4jConfig.pLogger.debug(": Buscando WebDriver free. Type {}, moreDataWrdrv {}", typeWdrv, moreDataWdrv);
        boolean encontrado = false;
        while (itStrWd.hasNext() && !encontrado) {
            StoredWebDrv strWd = itStrWd.next();
            if (strWd.isFree() &&
                strWd.getTypeWdrv() == typeWdrv &&
                strWd.getMoreDataWdrv().compareTo(moreDataWdrv) == 0) {
                
                //Lo obtenemos
                webdriverFree = strWd.getWebDriver();
                encontrado = true;
                
                //Le cambiamos el estado a 'busy' en el gestor
                strWd.markAsBusy();
                Log4jConfig.pLogger.debug(
                	"Encontrado -> Mark as Busy WebDriver: {} (state: {}, type: {}, moreDataWdrv: {})", 
                	strWd.getWebDriver(), strWd.getState(), strWd.getTypeWdrv(), strWd.getMoreDataWdrv());
            }
        }
        
        if (!encontrado) {
        	Log4jConfig.pLogger.debug("No encontrado Webdriver free. Type: {}, moreDataWrdrv: {}", typeWdrv, moreDataWdrv);
        }
        return webdriverFree;
    }
    
    private void markWebDriverAsFreeInPool(WebDriver driver) {
        StoredWebDrv strWd = searchWebDriver(driver);
        if (strWd != null) {
            strWd.markAsFree();
            Log4jConfig.pLogger.debug(
            	"Mark as Free WebDriver: {} (state: {}, type: {}, moreDataWdrv: {})", 
            	strWd.getWebDriver(), strWd.getState(), strWd.getTypeWdrv(), strWd.getMoreDataWdrv());
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
        	strWd.getWebDriver(), strWd.getState(), strWd.getTypeWdrv(), strWd.getMoreDataWdrv());
    }

    /**
     * Almacenamos el webdriver en la lista con los datos especificados en los parámetros
     * @param state   estado 'busy', 'free'
     * @param type    tipo de navegador/canal de ejecución de los scripts
     * @param moreDataDrv especifica datos adicionales del WebDriver que necesitamos.
     *                    actualmente sólo viene informado para el caso de 'browserstack' (especificamos el modelo de 'device' documentado en browserstack)
     *                    en el resto de casos viene a "". 
     */
    private void storeWebDriver(WebDriver driver, stateWd state, WebDriverType type, String moreDataWdrv) {
        StoredWebDrv strWd = new StoredWebDrv(driver, state, type, moreDataWdrv);
        poolWebDrivers.add(strWd);
        Log4jConfig.pLogger.debug("Alta Stored WebDriver: {} (state: {}, type: {}, moreDataWdrv: {})", driver, state, type, moreDataWdrv);
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
    private String getMoreDataWdrv(WebDriverType canalWebDriver, TestRunTM testRun) {
        String moreDataWdrv = "";
        switch (canalWebDriver) {
        //En el caso de BrowserStack como información específica del WebDriver incluiremos el modelo de dispositivo móvil asociado
        case browserstack:
            BrowserStackMobil bsStackMobil = testRun.getBrowserStackMobil();
            if (bsStackMobil!=null) {
                moreDataWdrv = bsStackMobil.getDevice();
            }
            break;
	        
        //En el resto de tipos de WebDriver no habrá información específica sobre el WebDriver / Dispositivo de ejecución
        case firefox:
        case chrome:
        case edge:
            moreDataWdrv = "";
                break;	  
        default:
            break;
        }
	    
        return moreDataWdrv;
    }
}