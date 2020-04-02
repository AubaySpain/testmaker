package org.jorge2m.testmaker.service.webdriver.maker.brwstack;

import org.jorge2m.testmaker.service.webdriver.maker.brwstack.BrowserStackSO.PlatformMobilBS;

public interface BrowserStackMobil {

	public String getUser();
	public String getPassword();
	public PlatformMobilBS getSo();
	public String getSoVersion();
	public String getDevice();
	public String getRealMobil();
	public String getBrowser();
	
}
