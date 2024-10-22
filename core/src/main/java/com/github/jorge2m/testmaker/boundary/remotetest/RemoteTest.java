package com.github.jorge2m.testmaker.boundary.remotetest;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.client.Invocation;

import org.apache.commons.lang3.SerializationUtils;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.domain.ServerSubscribers.ServerSubscriber;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteBean;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseBean;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.github.jorge2m.testmaker.service.webdriver.maker.FactoryWebdriverMaker.EmbeddedDriver;
import com.github.jorge2m.testmaker.testreports.testcasestore.VideoStorer;

public class RemoteTest extends JaxRsClient {
	
	private static final int TIMEOUT_CONNECTION = 15 * 1000; // 15 seconds
	private static final int TIMEOUT_RECEIVE = 15 * 60 * 1000; // 15 minutes
	private static final int MAX_RETRY_TEST = 3;
	private final ServerSubscriber server;
	
	public RemoteTest(ServerSubscriber server) {
		this.server = server;
	}
	
	public Optional<SuiteBean> execute(TestCaseTM testCase, Object testObject) 
			throws Exception {
		var inputParams = testCase.getInputParamsSuite();
		setIdExecSuite(inputParams, testCase); 
		if (testCase.getSuiteParent().isTestFromFactory(testObject)) {
			Log4jTM.getLogger().info("Factory Test (%s) -> Remote", testCase.getCode());
			return executeTestFromFactory(testCase, inputParams, (Serializable)testObject);
		}
		Log4jTM.getLogger().info("Standar Test -> Remote");
		return executeTestStandar(testCase, inputParams);
	}
	
	private void setIdExecSuite(InputParamsTM inputParams, TestCaseTM testCase) {
		inputParams.setIdExecSuiteParent(testCase.getSuiteParent().getIdExecution());
		if (inputParams.getDriver().compareTo(EmbeddedDriver.browserstack.name())==0) {
			//We need to unify idexecsuite in all testcases for send all to BrowserStack gathered under the same id
			//Don't activate for cases where the slave nodes are permanent because will generate SQL constraint violation
			inputParams.setIdExecSuite(testCase.getSuiteParent().getIdExecution());
		}
	}
	
	private Optional<SuiteBean> executeTestFromFactory(TestCaseTM testCase, InputParamsTM inputParams, Serializable testObject) 
			throws Exception {
		byte[] testSerialized = SerializationUtils.serialize(testObject);
		String testSerializedStrB64 = Base64.getEncoder().encodeToString(testSerialized);
		Log4jTM.getLogger().info("Object Serialized: %s", testSerializedStrB64);
		var suiteRemote = suiteRun(
				inputParams, 
				String.valueOf(testCase.isRetried()),
				Arrays.asList(testCase.getName()), 
				testSerializedStrB64);
		
		if (!suiteRemote.isPresent()) {
			return Optional.empty();
		}
		return Optional.of(processTestCaseRemote(testCase, suiteRemote.get())); 
	}
	
	private Optional<SuiteBean> executeTestStandar(TestCaseTM testCase, InputParamsTM inputParams) throws Exception {
		inputParams.setTestCaseNameParent(testCase.getNameUnique());
		var suiteRemote = suiteRun(
				inputParams, 
				String.valueOf(testCase.isRetried()),
				Arrays.asList(testCase.getCode()), 
				null);
		
		if (!suiteRemote.isPresent()) {
			return Optional.empty();
		}
		return Optional.of(processTestCaseRemote(testCase, suiteRemote.get()));
	}
	
	private SuiteBean processTestCaseRemote(TestCaseTM testCase, SuiteBean suiteRemoteExecuted) {
		//Get TestCase Remote
		var testCaseRemote = getTestCaseRemote(suiteRemoteExecuted);
		
		//Coser TestCase
		String throwableStrB64 = testCaseRemote.getThrowable();
		var throwable = (Throwable)fromStringB64(throwableStrB64);
		testCase.getResult().setThrowable(throwable);
		testCase.getResult().setStatus(testCaseRemote.getStatusTng());
		testCase.addSufixToName(testCaseRemote.getSpecificInputData());
		testCase.setLogs(testCaseRemote.getLogs());
		if (testCaseRemote.getVideo()!=null) {
			new VideoStorer(testCase).storeInFile(testCaseRemote.getVideo());
		}
		
		var listStepsRemote = testCaseRemote.getListStep();
		for (var stepRemote : listStepsRemote) {
			testCase.addStep(stepRemote);
			stepRemote.setParents(testCase);
			stepRemote.getEvidencesWarehouse().setStep(stepRemote);
			stepRemote.moveContentEvidencesToFile();
			for (var checks : stepRemote.getListChecksTM()) {
				checks.setParents(stepRemote);
			}
		}

		return suiteRemoteExecuted;
	}
	
	private TestCaseBean getTestCaseRemote(SuiteBean suiteRemote) {
		var listTestCaseRemote = suiteRemote
				.getListTestRun().get(0)
				.getListTestCase();
		var testToReturn = listTestCaseRemote.get(0);
		for (var testCaseRemote : listTestCaseRemote) {
			if (!testCaseRemote.getListStep().isEmpty()) {
				testToReturn = testCaseRemote;
				break;
			}
		}
		
		ConcealerCharConversion.conceal(testToReturn);
		return testToReturn;
	}
	
	public Optional<SuiteBean> suiteRun(InputParamsTM inputParams, String retried, List<String> testCases, String testObjectSerialized) 
			throws Exception {
		
		Form formParams = getFormParams(inputParams.getAllParamsValues());
		var mapParams = formParams.asMap();
		if (testCases!=null) {
			mapParams.putSingle(InputParamsTM.TCASE_NAME_PARAM, String.join(",", testCases));
		}
		if (testObjectSerialized!=null) {
			mapParams.putSingle(InputParamsTM.TEST_OBJECT_PARAM, testObjectSerialized);
		}
		mapParams.putSingle(InputParamsTM.ASYNC_EXEC_PARAM, "false");
		mapParams.putSingle(InputParamsTM.REMOTE_PARAM, "true");
		mapParams.putSingle(InputParamsTM.NOTIFICATION_PARAM, "false");
		mapParams.putSingle(InputParamsTM.RETRIED_PARAM, retried);

		var client = getClientIgnoreCertificates();
		return execRemoteSuiteRun(client, formParams, MAX_RETRY_TEST);
	}
	
	private Optional<SuiteBean> execRemoteSuiteRun(Client client, Form formParams, int numRetries) throws Exception {
		for (int i=1; i<=numRetries; i++) {
			try {
				return Optional.of(execRemoteSuiteRun(client, formParams));
        	} catch (ProcessingException pe) {
        		if (pe.getCause() instanceof SocketTimeoutException) {
        			Log4jTM.getLogger().warn("SocketTimeoutException in Remote Test execution (retry " + i + "), not retry", pe);
        			throw pe;
        		} else {
    				Log4jTM.getLogger().warn("ProcessingException in Remote Test execution (retry " + i + "), retry in 10 seconds...", pe);
        			Thread.sleep(10000);
        		}
        	}
			catch (Exception e) {
				Log4jTM.getLogger().warn("Exception in Remote Test execution (retry " + i + "), retry in 10 seconds...", e);
				Thread.sleep(10000);
			}
		}
		return Optional.empty();
	}
	
	private SuiteBean execRemoteSuiteRun(Client client, Form formParams) {
		Invocation.Builder builder = client
				.target(server.getUrl() + "/suiterun")
				.request(MediaType.APPLICATION_JSON)
				.property("jersey.config.client.readTimeout", TIMEOUT_RECEIVE)
                .property("jersey.config.client.connectTimeout", TIMEOUT_CONNECTION);
		
		return builder.post(Entity.form(formParams), SuiteBean.class);
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
			var ois = new ObjectInputStream(new ByteArrayInputStream(data));
			Object o  = ois.readObject();
			ois.close();
			return o;
		}
		catch (Exception e) {
			return null;
		}
	}
}
