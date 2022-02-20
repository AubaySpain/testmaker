package com.github.jorge2m.testmaker.boundary.remotetest;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.SerializationUtils;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.domain.ServerSubscribers.ServerSubscriber;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteBean;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseBean;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.github.jorge2m.testmaker.service.webdriver.maker.FactoryWebdriverMaker.EmbeddedDriver;

public class RemoteTest extends JaxRsClient {
	
	private final static int MAX_RETRY_TEST = 3;
	private final ServerSubscriber server;
	
	public RemoteTest(ServerSubscriber server) {
		this.server = server;
	}
	
	public Optional<SuiteBean> execute(TestCaseTM testCase, Object testObject) 
	throws Exception {
		InputParamsTM inputParams = testCase.getInputParamsSuite();
		setIdExecSuite(inputParams, testCase);
		if (testCase.getSuiteParent().isTestFromFactory(testObject)) {
			Log4jTM.getLogger().info("Factory Test (" + testCase.getName() + ") -> Remote");
			return executeTestFromFactory(testCase, inputParams, (Serializable)testObject);
		}
		Log4jTM.getLogger().info("Standar Test -> Remote");
		return executeTestStandar(testCase, inputParams);
	}
	
	private void setIdExecSuite(InputParamsTM inputParams, TestCaseTM testCase) {
		//We need to unify idexecsuite in all testcases for send all to BrowserStack gathered under the same id
		if (inputParams.getDriver().compareTo(EmbeddedDriver.browserstack.name())==0) {
			inputParams.setIdExecSuite(testCase.getSuiteParent().getIdExecution());
		}
	}
	
	private Optional<SuiteBean> executeTestFromFactory(TestCaseTM testCase, InputParamsTM inputParams, Serializable testObject) 
	throws Exception {
		byte[] testSerialized = SerializationUtils.serialize(testObject);
		String testSerializedStrB64 = Base64.getEncoder().encodeToString(testSerialized);
		Log4jTM.getLogger().info("Object Serialized: " + testSerializedStrB64);
		Optional<SuiteBean> suiteRemote = suiteRun(
				inputParams, 
				Arrays.asList(testCase.getName()), 
				testSerializedStrB64);
		
		if (!suiteRemote.isPresent()) {
			return Optional.empty();
		}
		return Optional.of(processTestCaseRemote(testCase, suiteRemote.get())); 
	}
	
	private Optional<SuiteBean> executeTestStandar(TestCaseTM testCase, InputParamsTM inputParams) throws Exception {
		Optional<SuiteBean> suiteRemote = suiteRun(
				inputParams, 
				Arrays.asList(testCase.getName()), 
				null);
		
		if (!suiteRemote.isPresent()) {
			return Optional.empty();
		}
		return Optional.of(processTestCaseRemote(testCase, suiteRemote.get()));
	}
	
	private SuiteBean processTestCaseRemote(TestCaseTM testCase, SuiteBean suiteRemoteExecuted) {
		//Get TestCase Remote
		TestCaseBean testCaseRemote = getTestCaseRemote(suiteRemoteExecuted);
		
		//Coser TestCase
		String throwableStrB64 = testCaseRemote.getThrowable();
		Throwable throwable = (Throwable)fromStringB64(throwableStrB64);
		testCase.getResult().setThrowable(throwable);
		testCase.getResult().setStatus(testCaseRemote.getStatusTng());
		testCase.addSufixToName(testCaseRemote.getSpecificInputData());
		
		List<StepTM> listStepsRemote = testCaseRemote.getListStep();
		for (StepTM stepRemote : listStepsRemote) {
			testCase.addStep(stepRemote);
			stepRemote.setParents(testCase);
			stepRemote.getEvidencesWarehouse().setStep(stepRemote);
			stepRemote.moveContentEvidencesToFile();
			for (ChecksTM checks : stepRemote.getListChecksTM()) {
				checks.setParents(stepRemote);
			}
		}

		return suiteRemoteExecuted;
	}
	
	private TestCaseBean getTestCaseRemote(SuiteBean suiteRemote) {
		List<TestCaseBean> listTestCaseRemote = suiteRemote
				.getListTestRun().get(0)
				.getListTestCase();
		TestCaseBean testToReturn = listTestCaseRemote.get(0);
		for (TestCaseBean testCaseRemote : listTestCaseRemote) {
			if (testCaseRemote.getListStep().size() > 0) {
				testToReturn = testCaseRemote;
				break;
			}
		}
		
		ConcealerCharConversion.conceal(testToReturn);
		return testToReturn;
	}
	
	public Optional<SuiteBean> suiteRun(InputParamsTM inputParams, List<String> testCases, String testObjectSerialized) 
	throws Exception {
		Form formParams = getFormParams(inputParams.getAllParamsValues());
		MultivaluedMap<String, String> mapParams = formParams.asMap();
		if (testCases!=null) {
			mapParams.putSingle(InputParamsTM.TCaseNameParam, String.join(",", testCases));
		}
		if (testObjectSerialized!=null) {
			mapParams.putSingle(InputParamsTM.TestObjectParam, testObjectSerialized);
		}
		mapParams.putSingle(InputParamsTM.AsyncExecParam, "false");
		mapParams.putSingle(InputParamsTM.RemoteParam, "true");

		Client client = getClientIgnoreCertificates();
		return execRemoteSuiteRun(client, formParams, MAX_RETRY_TEST);
	}
	
	private Optional<SuiteBean> execRemoteSuiteRun(Client client, Form formParams, int numRetries) throws Exception {
		for (int i=1; i<=numRetries; i++) {
			try {
				return Optional.of(execRemoteSuiteRun(client, formParams));
			}
			catch (Exception e) {
				Log4jTM.getLogger().warn(
						"Exception in Remote Test execution (retry " + i + "), retry in 10 seconds...", e);
				Thread.sleep(10000);
			}
		}
		return Optional.empty();
	}
	
	private SuiteBean execRemoteSuiteRun(Client client, Form formParams) {
		SuiteBean suiteData = 
			client
				.target(server.getUrl() + "/suiterun")
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.form(formParams), SuiteBean.class);
		return suiteData;
	}
	
	private Form getFormParams(Map<String,String> params) {
		Form formParams = new Form();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			formParams.param(entry.getKey(), entry.getValue());
		}
		return formParams;
	}
	
	/** Read the object from Base64 string. */
	private static Object fromStringB64(String s) {
		try {
			byte [] data = Base64.getDecoder().decode( s );
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
			Object o  = ois.readObject();
			ois.close();
			return o;
		}
		catch (Exception e) {
			return null;
		}
	}
}
