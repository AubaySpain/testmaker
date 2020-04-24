package com.github.jorge2m.testmaker.service.webdriver.maker.brwstack;

import com.github.jorge2m.testmaker.domain.InputParamsTM;

public class BrowserStackDataDesktop implements BrowserStackDesktopI {

	private final InputParamsTM inputParams;
	
	public BrowserStackDataDesktop(InputParamsTM inputParams) {
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
	public String getBrowser() {
		return inputParams.getBStackBrowser();
	}
	@Override
	public String getBrowserVersion() {
		return inputParams.getBStackBrowserVersion();
	}
	@Override
	public String getResolution() {
		return inputParams.getBStackResolution();
	}
	
}
