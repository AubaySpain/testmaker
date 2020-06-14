package com.mng.example_test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.github.jorge2m.example_test.access.ServerRest;
import com.github.jorge2m.example_test.test.factory.SearchFactory;
import com.github.jorge2m.testmaker.boundary.remotetest.JaxRsClient;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.domain.StateExecution;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteBean;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseBean;

public class RestApiIT extends JaxRsClient {

	private String serverSlaveTmIp;
	private String serverSlaveTmPort;
	private boolean localServerSlave;
	private final String serverSlaveLocalPort = "85";
	
	private String serverHubTmIp;
	private String serverHubTmPort;
	
	@Before
	public void setUp() throws Exception {
		setupServerSlave();
		setupServerHub();
	}
	
	@Test
	public void testStandarTestCase() throws Exception {
		//Given-When
		SuiteBean suiteData = executeTestsAgainstServerRetry("BUS100{2-2}", serverSlaveTmIp, serverSlaveTmPort);
		
		//Then...
		checkResultStandarTestCase(suiteData);
	}
	
	@Test
	public void testStandarTestCaseHub() throws Exception {
		if (isServerHub()) {
			//Given-When
			SuiteBean suiteData = executeTestsAgainstServerRetry("BUS100{2-2}", serverHubTmIp, serverHubTmPort);
			
			//Then...
			checkResultStandarTestCase(suiteData);
		}
	}
	
	private void checkResultStandarTestCase(SuiteBean suiteData) {

		assertTrue(suiteData!=null);
		assertTrue(suiteData.getResult()==State.Ok || suiteData.getResult()==State.Info);
		assertEquals(suiteData.getStateExecution(), StateExecution.Finished);
		assertEquals(suiteData.getListTestRun().size(), 1);
		
		//Check TestCases
		List<TestCaseBean> listTestCases = suiteData.getListTestRun().get(0).getListTestCase();
		assertEquals(listTestCases.size(), 2);
		TestCaseBean testCase = listTestCases.get(0);
		assertTrue(testCase.getResult()==State.Ok || testCase.getResult()==State.Info);
		assertEquals(testCase.getListStep().size(), 3);
		
		//Check Step1
		StepTM step1 = testCase.getListStep().get(0);
		assertTrue(
			"Check descripción Step1 (\"" + step1.getDescripcion() + "\")",
			"Introducimos el texto <b>Mario Maker 2</b> y clickamos el botón \"Buscar con Google\""
			.compareTo(step1.getDescripcion())==0);
		assertTrue(step1.getResultSteps()==State.Ok || step1.getResultSteps()==State.Info);
		assertEquals(step1.getListChecksTM().size(), 1);
		
		//Check Validation1 (from Step1)
		ChecksTM checkGroup1 = step1.getListChecksTM().get(0);
		assertTrue(checkGroup1.getStateValidation()==State.Ok || checkGroup1.getStateValidation()==State.Info);
		assertEquals(checkGroup1.getListChecks().size(), 3);
		Check check1 = checkGroup1.getListChecks().get(0);
		assertTrue(
			"Check descripción Step1 (\"" + check1.getDescription() + "\")",
			check1.getDescription().contains("1) Aparece alguna entrada de resultado"));
		assertTrue(check1.getStateResult()==State.Ok || check1.getStateResult()==State.Info);
		
		if (localServerSlave) {
			checkReporsSuiteExists(suiteData);
		
			//Check hardcopy Step-1 exists
			String pathEvidences = getPathEvidences(suiteData, testCase);
			File step1png = new File(pathEvidences + "/Step-1.png");
			assertTrue(step1png.exists());
		}
	}
	
	@Test
	public void testFactoryTestCase() throws Exception {
		//Given-When
		SuiteBean suiteData = executeTestsAgainstServerRetry("FAC001", serverSlaveTmIp, serverSlaveTmPort);
		
		//Then...
		checkResultFactoryTestCase(suiteData);
	}
	
	@Test
	public void testFactoryTestCaseHub() throws Exception {
		if (isServerHub()) {
			//Given-When
			SuiteBean suiteData = executeTestsAgainstServerRetry("FAC001", serverHubTmIp, serverHubTmPort);
			
			//Then...
			checkResultFactoryTestCase(suiteData);
		}
	}
	
	private void checkResultFactoryTestCase(SuiteBean suiteData) throws Exception {
		assertTrue(suiteData!=null);
		assertEquals(suiteData.getResult(), State.Warn);
		assertEquals(suiteData.getStateExecution(), StateExecution.Finished);
		assertEquals(suiteData.getListTestRun().size(), 1);
		
		//Check TestCases
		List<String> valuesSearched = SearchFactory.searchValues;
		List<TestCaseBean> listTestCases = suiteData.getListTestRun().get(0).getListTestCase();
		assertTrue(listTestCases.size()==valuesSearched.size());
		for (String valueSearched : valuesSearched) {
			assertTrue(
				"Check existe el test para la búsqueda: " + valueSearched, 
				checkExistsTestCase(valueSearched, listTestCases));
		}
		TestCaseBean testCase = listTestCases.get(0);
		assertTrue(testCase.getResult()==State.Ok || testCase.getResult()==State.Info || testCase.getResult()==State.Warn);
		assertEquals(testCase.getListStep().size(), 3);
		
		//Check Step3
		StepTM step3 = testCase.getListStep().get(2);
		assertTrue(
			"Check descripción Step3 (\"" + step3.getDescripcion() + "\")",
			step3.getDescripcion().contains("pulsamos ENTER"));
		assertTrue(step3.getResultSteps()==State.Ok || step3.getResultSteps()==State.Info || step3.getResultSteps()==State.Warn);
		assertEquals(step3.getListChecksTM().size(), 2);
		
		//Check Validation2 (from Step3)
		ChecksTM checkGroup2 = step3.getListChecksTM().get(1);
		assertTrue(checkGroup2.getStateValidation()==State.Ok || checkGroup2.getStateValidation()==State.Info || checkGroup2.getStateValidation()==State.Warn);
		assertEquals(checkGroup2.getListChecks().size(), 1);
		Check check1 = checkGroup2.getListChecks().get(0);
		assertTrue(
			"Check descripción Check1(checkGroup2) (\"" + check1.getDescription() + "\")",
			check1.getDescription().contains("Aparecen más resultados en Google"));
		assertTrue(check1.getStateResult()==State.Ok || check1.getStateResult()==State.Info || check1.getStateResult()==State.Warn);
		
		if (localServerSlave) {
			checkReporsSuiteExists(suiteData);
		
			//Check Evidences Step-3 exists
			String pathEvidences = getPathEvidences(suiteData, testCase);
			File step3png = new File(pathEvidences + "/Step-3.png");
			File step3html = new File(pathEvidences + "/Step-3.html");
			assertTrue(step3png.exists());
			assertTrue(step3html.exists());
		}
	}
	
	private void setupServerSlave() throws Exception {
		//From surefire-plugin in pom.xml
		String serverIpParam = System.getProperty("server_slave.ip");
		String serverPortParam = System.getProperty("server_slave.port");
		Client client = getClientIgnoreCertificates();
		if (serverIpParam!=null && "".compareTo(serverIpParam)!=0 &&
			serverPortParam!=null && "".compareTo(serverPortParam)!=0) {
			serverSlaveTmIp = serverIpParam;
			serverSlaveTmPort = serverPortParam;
			localServerSlave = false;
		} else {
			serverSlaveTmIp = "localhost";
			serverSlaveTmPort = serverSlaveLocalPort;
			startLocalSeverIfNotYet(client, serverSlaveTmIp, serverSlaveTmPort);
			localServerSlave = true;
		}
		System.out.println("Server Slave: " + serverSlaveTmIp + ":" + serverSlaveTmPort);
		checkServerAvailability(client, serverSlaveTmIp, serverSlaveTmPort, 10);
	}
	
	private void setupServerHub() throws Exception {
		Client client = getClientIgnoreCertificates();
		if (isServerHub()) {
			//From surefire-plugin in pom.xml
			serverHubTmIp = System.getProperty("server_hub.ip");;
			serverHubTmPort = System.getProperty("server_hub.port");;

			System.out.println("Server Hub: " + serverHubTmIp + ":" + serverHubTmPort);
			checkServerAvailability(client, serverHubTmIp, serverHubTmPort, 10);
			
			subscribeSlaveToHub(client);
		}
	}
	
	private boolean isServerHub() {
		String serverIpParam = System.getProperty("server_hub.ip");
		String serverPortParam = System.getProperty("server_hub.port");
		return (serverIpParam!=null && "".compareTo(serverIpParam)!=0 &&
				serverPortParam!=null && "".compareTo(serverPortParam)!=0);
	}
	
	private void startLocalSeverIfNotYet(Client client, String serverIp, String serverPort) throws Exception {
		if (!checkServerAvailability(client, serverIp, serverPort)) {
			startLocalServer(serverPort);
		}
	}
	
	private boolean checkServerAvailability(Client client, String serverIp, String serverPort, int maxTimes) 
	throws Exception {
		for (int i=0; i<maxTimes; i++) {
			if (checkServerAvailability(client, serverIp, serverPort)) {
				return true;
			}
			Thread.sleep(1000);
		}
		return false;
	}
	private boolean checkServerAvailability(Client client, String serverIp, String serverPort) {
		try {
			client
				.target("http://" + serverIp + ":" + serverPort + "/testserver")
				.request(MediaType.APPLICATION_JSON)
				.get();
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
	
	private void subscribeSlaveToHub(Client client) {
		System.out.println("Subscribing Slave to Hub...");
		client
			.target("http://" + serverHubTmIp + ":" + serverHubTmPort + "/subscription")
			.queryParam("urlslave", "http://" + serverSlaveTmIp + ":" + serverSlaveTmPort)
			.request(MediaType.APPLICATION_JSON)
			.get();
		System.out.println("Subscription completed OK");
	}
	
	private void startLocalServer(String serverPort) {
		String[] args = {"-port", serverPort};
		CompletableFuture.runAsync(() -> {
			try {
				ServerRest.main(args);
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		});
	}
	
	private boolean checkExistsTestCase(String valueSearched, List<TestCaseBean> listTestCases) {
		for (TestCaseBean testCase : listTestCases) {
			if (testCase.getName().contains(valueSearched)) {
				return true;
			}
		}
		return false;
	}
	
	private void checkReporsSuiteExists(SuiteBean suite) {
		String pathReports = getPathReports(suite);
		File emailable = new File(pathReports + "/emailable-report.html");
		File logReport = new File(pathReports + "/emailable-report.html");
		File reportHtml = new File(pathReports + "/ReportTSuite.html");
		assertTrue(emailable.exists());
		assertTrue(logReport.exists());
		assertTrue(reportHtml.exists());
	}
	private String getPathReports(SuiteBean suite) {
		return 
			"./output-library/" +
			suite.getName() + "/" +
			suite.getIdExecSuite();
	}
	private String getPathEvidences(SuiteBean suite, TestCaseBean testCase) {
		return (
			getPathReports(suite) + "/" +
			testCase.getTestRunName() + "/" + 
			testCase.getNameUnique());
	}
	
	private SuiteBean executeTestsAgainstServerRetry(String testCases, String serverIp, String serverPort) 
	throws Exception {
		try {
			return executeTestsAgainstServer(testCases, serverIp, serverPort);
		}
		catch (NotFoundException e) {
			Thread.sleep(5000);
			return executeTestsAgainstServer(testCases, serverIp, serverPort);
		}
	}
	
	private SuiteBean executeTestsAgainstServer(String testCases, String serverIp, String serverPort) throws Exception {
		//Given
		Form formParams = new Form();
		MultivaluedMap<String, String> mapParams = formParams.asMap();
		mapParams.putSingle(InputParamsTM.SuiteNameParam, "SmokeTest");
		mapParams.putSingle(InputParamsTM.DriverNameParam, "chrome");
		mapParams.putSingle(InputParamsTM.ChannelNameParam, "desktop");
		mapParams.putSingle(InputParamsTM.AppNameParam, "google");
		mapParams.putSingle(InputParamsTM.URLNameParam, "https://www.google.com");
		mapParams.putSingle(InputParamsTM.TCaseNameParam, testCases);
		mapParams.putSingle(InputParamsTM.AsyncExecParam, "false");
	
		//When
		Client client = getClientIgnoreCertificates();
		SuiteBean suiteData = 
			client
				.target("http://" + serverIp + ":" + serverPort + "/suiterun")
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.form(formParams), SuiteBean.class);
		
		return suiteData;
	}
}
