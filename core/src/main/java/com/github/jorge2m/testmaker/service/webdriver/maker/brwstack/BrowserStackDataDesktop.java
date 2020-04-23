package com.github.jorge2m.testmaker.service.webdriver.maker.brwstack;

import com.github.jorge2m.testmaker.domain.InputParamsTM;

public class BrowserStackDataDesktop implements BrowserStackDesktopI {

	private final InputParamsTM inputParams;
	
	public BrowserStackDataDesktop(InputParamsTM inputParams) {
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
	public String getBrowser() {
		return inputParams.getBrowserBStack();
	}
	@Override
	public String getBrowserVersion() {
		return inputParams.getBrowserVersionBStack();
	}
	@Override
	public String getResolution() {
		return inputParams.getResolutionBStack();
	}
	
}
