package com.github.jorge2m.testmaker.boundary.listeners;

import java.net.HttpURLConnection;

import org.testng.*;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestRunTM;


public class InvokeListener extends TestListenerAdapter implements ISuiteListener {
	
	HttpURLConnection httpUrlCallBack = null;

	@Override //Start Suite 
	public void onStart(ISuite suite) {
		//Esta llamada ya se hace desde TestMaker.runInTestNgSync(SuiteTM)
		//((SuiteTM)suite.getXmlSuite()).start();
	}

	@Override //End Suite
	public void onFinish(ISuite suite) {
		//Esta llamada ya se hace desde TestMaker.runInTestNgSync(SuiteTM)
		//((SuiteTM)suite.getXmlSuite()).end();
	}

	@Override //Start TestRun
	public synchronized void onStart(ITestContext testNgContext) {
		//Inyectamos el ITestContext en el TestRun
		TestRunTM testRun = (TestRunTM)testNgContext.getCurrentXmlTest();
		testRun.setTestNgContext(testNgContext);
	}

	@Override //End TestRun
	public void onFinish(ITestContext testContext) {
		TestRunTM testRun = (TestRunTM)testContext.getCurrentXmlTest();
		testRun.end();
	}

	@Override //Start TestCase
	public synchronized void onTestStart(ITestResult result) {
		TestRunTM testRun = getTestRun(result);
		TestCaseTM testCase = new TestCaseTM(result);
		//testCase.makeWebDriver();
		testRun.addTestCase(testCase);
	}

	@Override //End TestCase Success
	public void onTestSuccess(ITestResult result) {
		TestCaseTM testCase = TestCaseTM.getTestCase(result);
		testCase.end();
	}

	@Override //End TestCase Skipped
	public void onTestSkipped(ITestResult result) {
		TestCaseTM testCase = TestCaseTM.getTestCase(result);
		if (testCase!=null) {
			testCase.end(State.Skip);
		}
	}

	@Override //End TestCase Failure
	public void onTestFailure(ITestResult result) {
		TestCaseTM testCase = TestCaseTM.getTestCase(result);
		if (testCase!=null) {
			testCase.end(State.Nok);
		}
		Log4jTM.getLogger().error("Exception for TestNG", result.getThrowable());
	}

	private TestRunTM getTestRun(ITestResult result) {
		return ((TestRunTM)result.getTestContext().getCurrentXmlTest());
	}
}