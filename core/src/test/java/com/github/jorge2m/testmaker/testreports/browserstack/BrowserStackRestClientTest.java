package com.github.jorge2m.testmaker.testreports.browserstack;

import org.junit.Ignore;
import org.junit.Test;

public class BrowserStackRestClientTest {
	
	@Ignore
	@Test
	public void getUrlBuild() throws Exception {
		String idSuiteExec = "200421_220001343";
		String urlExpected = "https://automate.browserstack.com/dashboard/v2/builds/cc4f0a9c1439823e7b1b03232c515feccc45e30d";
		
		BrowserStackRestClient client = new BrowserStackRestClient("equipoqa1", "qp3dr5VJbFMAxPsT4k1b");
		String urlBuildBrowserStack = client.getUrlBuild(idSuiteExec);
		assert(urlExpected.compareTo(urlBuildBrowserStack)==0);
	}

}
