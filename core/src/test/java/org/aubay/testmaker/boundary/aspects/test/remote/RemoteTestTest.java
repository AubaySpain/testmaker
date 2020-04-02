package org.jorge2m.testmaker.boundary.aspects.test.remote;

import java.net.URL;
import java.util.Arrays;

import org.jorge2m.testmaker.boundary.remotetest.RemoteTest;
import org.jorge2m.testmaker.conf.Channel;
import org.jorge2m.testmaker.domain.InputParamsBasic;
import org.jorge2m.testmaker.domain.ServerSubscribers.ServerSubscriber;
import org.jorge2m.testmaker.domain.suitetree.SuiteBean;
import org.jorge2m.testmaker.domain.suitetree.TestCaseBean;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

public class RemoteTestTest {

	private enum Suites {SmokeTest}
	private enum AppEcom {shop}
	
	@Ignore
	@Test
	public void testSuiteRun() throws Exception {
		//Given
		InputParamsBasic inputParams = new InputParamsBasic(Suites.class, AppEcom.class);
		inputParams.setSuite(Suites.SmokeTest);
		inputParams.setBrowser("chrome");
		inputParams.setChannel(Channel.desktop);
		inputParams.setApp(AppEcom.shop);
		inputParams.setVersion("V1");
		inputParams.setUrlBase("https://shop.mango.com/preHome.faces");
		
		//When
		ServerSubscriber server = new ServerSubscriber(new URL("https://localhost:80"));
		RemoteTest remoteTest = new RemoteTest(server);
		SuiteBean suiteRemote = remoteTest.suiteRun(inputParams, Arrays.asList("SES002"), null);
		
		//Then
		TestCaseBean testCaseRemote = suiteRemote.
				getListTestRun().get(0).
				getListTestCase().get(0);
		assertTrue("SES002_IniciarSesion_OK".compareTo(testCaseRemote.getName())==0);
	}

}
