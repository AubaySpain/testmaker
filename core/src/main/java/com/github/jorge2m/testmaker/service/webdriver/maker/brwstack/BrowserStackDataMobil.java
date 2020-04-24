package com.github.jorge2m.testmaker.service.webdriver.maker.brwstack;

import com.github.jorge2m.testmaker.domain.InputParamsTM;

public class BrowserStackDataMobil implements BrowserStackMobilI {

	private final InputParamsTM inputParams;
	
	public BrowserStackDataMobil(InputParamsTM inputParams) {
		this.inputParams = inputParams;
	}
	
	@Override
	public String getUser() {
		return inputParams.getBStackUser();
	}
	@Override
	public String getPassword() {
		return inputParams.getBStackPassword();
	}
	@Override
	public String getOs() {
		return inputParams.getBStackOs();
	}
	@Override
	public String getOsVersion() {
		return inputParams.getBStackOsVersion();
	}
	@Override
	public String getDevice() {
		return inputParams.getBStackDevice();
	}
	@Override
	public String getRealMobile() {
		return inputParams.getBStackRealMobile();
	}
	@Override
	public String getBrowser() {
		return inputParams.getBStackBrowser();
	}
	
}
