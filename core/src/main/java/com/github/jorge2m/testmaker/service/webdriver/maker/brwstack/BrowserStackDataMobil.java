package com.github.jorge2m.testmaker.service.webdriver.maker.brwstack;

import com.github.jorge2m.testmaker.domain.InputParamsTM;

public class BrowserStackDataMobil implements BrowserStackMobilI {

	private final InputParamsTM inputParams;
	
	public BrowserStackDataMobil(InputParamsTM inputParams) {
		this.inputParams = inputParams;
	}
	
	@Override
	public String getUser() {
		return inputParams.getUserBStack();
	}
	@Override
	public String getPassword() {
		return inputParams.getPasswordBStack();
	}
	@Override
	public String getOs() {
		return inputParams.getOsBStack();
	}
	@Override
	public String getOsVersion() {
		return inputParams.getOsVersionBStack();
	}
	@Override
	public String getDevice() {
		return inputParams.getDeviceBStack();
	}
	@Override
	public String getRealMobile() {
		return inputParams.getRealMobileBStack();
	}
	@Override
	public String getBrowser() {
		return inputParams.getBrowserBStack();
	}
	
}
