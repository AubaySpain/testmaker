package com.mng.example_test;

import static org.junit.Assert.assertTrue;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.junit.Ignore;
import org.junit.Test;

import com.github.jorge2m.testmaker.boundary.remotetest.JaxRsClient;
import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteBean;

public class IntegratedTest extends JaxRsClient {

	//@Ignore
	@Test
	public void testSuiteRun() throws Exception {
		//Given
		Form formParams = new Form();
		MultivaluedMap<String, String> mapParams = formParams.asMap();
		mapParams.putSingle(InputParamsTM.SuiteNameParam, "SmokeTest");
		mapParams.putSingle(InputParamsTM.DriverNameParam, "chrome");
		mapParams.putSingle(InputParamsTM.ChannelNameParam, "desktop");
		mapParams.putSingle(InputParamsTM.AppNameParam, "google");
		mapParams.putSingle(InputParamsTM.URLNameParam, "https://www.google.com");
		mapParams.putSingle(InputParamsTM.TCaseNameParam, "BUS100");
		mapParams.putSingle(InputParamsTM.AsyncExecParam, "true");

		//When
		Client client = getClientIgnoreCertificates();
		SuiteBean suiteData = 
			client
				.target("http://localhost:85/suiterun")
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.form(formParams), SuiteBean.class);
		
		//Then
		assertTrue(suiteData!=null);
	}
	
	
}
