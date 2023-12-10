package com.github.jorge2m.testmaker.testreports.browserstack;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import com.github.jorge2m.testmaker.boundary.remotetest.JaxRsClient;
import com.github.jorge2m.testmaker.conf.Log4jTM;

public class BrowserStackRestClient extends JaxRsClient {

	private static final String APIEndpoint = "https://api.browserstack.com/automate/";
	private final Client client;
	
	public BrowserStackRestClient(String user, String password) {
		this.client = getClient(user, password);
	}
	
	public String getUrlBuild(String idSuiteExecuted) {
		BuildAutomation build = getBuild(idSuiteExecuted);
		if (build!=null) {
			return ("https://automate.browserstack.com/dashboard/v2/builds/" + build.getHashed_id()); 
		}
		return "";
	}
	
	private Client getClient(String user, String password) {
		HttpAuthenticationFeature auth = HttpAuthenticationFeature.basic(user, password);
		try {
			return getClientIgnoreCertificates().register(auth);
		} catch (Exception e) {
			Log4jTM.getLogger().warn("Problem creating BrowserStack APIRest Client. " + e.getCause());
			return null;
		}
	}
	
	private BuildAutomation getBuild(String idSuiteExecuted) {
		List<Build> listBuilds = getBuilds(100);
		for (Build build : listBuilds) {
			BuildAutomation buildAut = build.getAutomation_build();
			if (buildAut.getName().contains(idSuiteExecuted)) {
				return buildAut;
			}
		}
		return null;
	}
	
	private List<Build> getBuilds(int limit) {
		List<Build> listBuilds = 
			client
				.target(APIEndpoint + "/builds.json")
				.queryParam("limit", limit)
				.request(MediaType.APPLICATION_JSON)
				.get(new GenericType<List<Build>> () {});
		
		return listBuilds;
	}
}
