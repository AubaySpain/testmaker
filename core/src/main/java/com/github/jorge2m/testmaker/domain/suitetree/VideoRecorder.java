package com.github.jorge2m.testmaker.domain.suitetree;

import com.github.jorge2m.testmaker.service.webdriver.maker.plugins.chrome.PluginSgreenRecorder;

public interface VideoRecorder {

	public void start();
	public void stop();
	
	public static VideoRecorder make(TestCaseTM testcase) {
		return PluginSgreenRecorder.makeRecorder(testcase);
	}
	
}
