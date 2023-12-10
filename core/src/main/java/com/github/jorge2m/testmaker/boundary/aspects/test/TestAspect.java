package com.github.jorge2m.testmaker.boundary.aspects.test;

import org.apache.commons.lang3.tuple.Pair;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.testng.annotations.Test;

import com.github.jorge2m.testmaker.conf.SendType;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.Alarm;
import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.domain.ServerSubscribers;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.github.jorge2m.testmaker.service.TestMaker;

import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import static com.github.jorge2m.testmaker.domain.StateExecution.*;

@Aspect
public class TestAspect {
	
	@Pointcut("@annotation(org.testng.annotations.Test)")
	public void annotationTestPointcut() {}

	@Pointcut("execution(* *(..))")
	public void atExecution(){}
	
	@Around("annotationTestPointcut() && atExecution()")
	public Object aroundTest(ProceedingJoinPoint joinPoint) throws Throwable {
		return manageAroundTest(joinPoint);
	}
	
	private Object manageAroundTest(ProceedingJoinPoint joinPoint) throws Throwable {
		var testCase = TestCaseTM.getTestCaseInExecution()
				.orElseThrow(NoSuchElementException::new);
		
		TestMaker.skipTestsIfSuiteEnded(testCase.getSuiteParent());
		return manageTestExecution(testCase, joinPoint);
	}
	
	private Object manageTestExecution(TestCaseTM testCase, ProceedingJoinPoint joinPoint) 
			throws Throwable {
		Object testResult = null;
		Test test = getTestAnnotation(joinPoint); 
		try {
			testResult = executeTest(joinPoint, testCase);
			testCase.setExceptionInExecution(false);
		}
		finally {
			boolean isRetryNeeded = isRetryNeeded(testCase, test, 1);
			if (isRetryNeeded) {
				testResult = retriesTestCase(testCase, joinPoint);
			} else {
				sendNotifications(testCase);
			}
		}
		return testResult;		
	}
	
	private boolean isRetryNeeded(TestCaseTM testCase, Test test, int numRetry) {
		var suite = testCase.getSuiteParent();
		var inputParams = suite.getInputParams();
		var retryParamsOpt = inputParams.getRetryParams();
		return 
			retryParamsOpt.isPresent() &&
			test.retry() &&
			numRetry<=retryParamsOpt.get().getRetriesTestCase() &&
			suite.getNumTestCaseRetries() < retryParamsOpt.get().getRetriesMaxSuite() &&
			!isTestExecutingInRemote(testCase) &&
			isTestCaseError(testCase);
	}
	
	private Object retriesTestCase(TestCaseTM testCaseActual, ProceedingJoinPoint joinPoint) 
			throws Throwable {
		Object result = null;
		for (int retry=1; retry<=getRetryTimes(testCaseActual); retry++) {
			var testCaseNewPair = manageRetryTestCase(retry, testCaseActual, joinPoint);
			testCaseActual = testCaseNewPair.getLeft();
			result = testCaseNewPair.getRight();
			if (!isTestCaseError(testCaseActual)) {
				break;
			}
		}
		return result; 
	}
	
	private int getRetryTimes(TestCaseTM testCase) {
		var inputParams = testCase.getSuiteParent().getInputParams();
		var retryParamsOpt = inputParams.getRetryParams();
		if (!retryParamsOpt.isEmpty()) {
			return retryParamsOpt.get().getRetriesTestCase();
		}
		return 0;
	}
	
	private Pair<TestCaseTM, Object> manageRetryTestCase(int retry, TestCaseTM testCaseActual, ProceedingJoinPoint joinPoint) 
			throws Throwable {
		testCaseActual.end(State.RETRY);
		var testCaseNew = new TestCaseTM(testCaseActual.getResult());
		TestMaker.skipTestsIfSuiteEnded(testCaseNew.getSuiteParent());
		testCaseActual.getTestRunParent().addTestCase(testCaseNew);
		
		Object test = retryTestCase(retry, joinPoint, testCaseNew);
		return Pair.of(testCaseNew, test);
	}

	private Object retryTestCase(int retry, ProceedingJoinPoint joinPoint, TestCaseTM testCaseNew)
			throws Throwable {
		Object testReturn = null;
		Test test = getTestAnnotation(joinPoint);
		try {
			testCaseNew.getSuiteParent().incrementTestCaseRetries();
			testReturn = executeTest(joinPoint, testCaseNew);
			testCaseNew.setExceptionInExecution(false);
		} catch (Exception e) {
			if (!isRetryNeeded(testCaseNew, test, retry+1)) {
				throw e;
			}
		}
		finally {
			if (!isRetryNeeded(testCaseNew, test, retry+1)) {			
				sendNotifications(testCaseNew);
			}
		}
		return testReturn;
	}
	
	private boolean isTestCaseError(TestCaseTM testCase) {
		return 
			testCase.getStateFromSteps()==State.DEFECT || 
			testCase.getStateFromSteps()==State.KO ||
			testCase.isExceptionInExecution();
	}
	
	private Object executeTest(ProceedingJoinPoint joinPoint, TestCaseTM testCase) throws Throwable {
		fitTestToRamp(testCase);
		testCase.setStateRun(RUNNING);
		if (executeTestRemote(testCase.getSuiteParent().getInputParams())) {
			return executeTestRemote(joinPoint, testCase);
		} else {
			return executeTest(testCase, joinPoint);
		}
	}

	private static final int MAX_SECONDS_DELAY_TEST = 300; 
	
	private void fitTestToRamp(TestCaseTM testCase) {
		for (int i=0; i<MAX_SECONDS_DELAY_TEST; i++) {
			if (!isNeededWaitForExecTest(testCase.getSuiteParent())) {
				return;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				throw new RuntimeException("Unexpected interrupt", e);
			}
		}
	}

	synchronized boolean isNeededWaitForExecTest(SuiteTM suiteParent) {
		var inputParams = suiteParent.getInputParams();
		int rampSeconds = inputParams.getThreadsRampNum();
		if (rampSeconds == 0) {
			return false;
		}
		
		int numTestCasesRunning = getTestCasesRunning(suiteParent);
		if (numTestCasesRunning == 0) {
			return false;
		}
		
		long secondsFromInitSuite = suiteParent.getTimeFromInit(TimeUnit.SECONDS);
		if (secondsFromInitSuite >= rampSeconds) {
			return false;   
		}
		
		int maxTestsInParallel = suiteParent.getThreadCount();
		int secondsBetweenTests = rampSeconds / (maxTestsInParallel - 1);
		int secondMustInitNewTestCase = numTestCasesRunning * secondsBetweenTests;
		
		return (secondsFromInitSuite < secondMustInitNewTestCase);
	}
	
	private int getTestCasesRunning(SuiteTM suiteTM) {
		return suiteTM.getNumberTestCases(RUNNING);
	}

	private Object executeTestRemote(ProceedingJoinPoint joinPoint, TestCaseTM testCase) 
			throws ExecuteRemoteTestException {
		try {
			var suiteBeanOpt = ServerSubscribers.sendTestToRemoteServer(testCase, joinPoint.getTarget());
			if (!suiteBeanOpt.isPresent()) {
				throw new ExecuteRemoteTestException("Problem executing test Remote");
			}
		} catch (Exception e) {
		    throw new ExecuteRemoteTestException("Problem executing test Remote", e);
		}
	
		//TODO si un @Test retorna un valor <> de void tendremos problemas. Se debería serializar el objeto de respuesta
		return null;
	}
	
	private Object executeTest(TestCaseTM testCase, ProceedingJoinPoint joinPoint) throws Throwable {
		var inputParams = testCase.getInputParamsSuite();
		var methodSignature = (MethodSignature)joinPoint.getSignature();
		if (executeTestLocal(inputParams, methodSignature.getMethod())) {
			Test testAnnotation = getTestAnnotation(joinPoint);
			testCase.makeInitObjects(testAnnotation.create());
			return joinPoint.proceed();
		}
		return null;
	}
	
	private Test getTestAnnotation(ProceedingJoinPoint joinPoint) {
		var methodSignature = (MethodSignature)joinPoint.getSignature();
		return methodSignature.getMethod().getAnnotation(Test.class); 
	}
	
	public static boolean executeTestRemote(InputParamsTM inputParams) {
		return (
			!inputParams.isTestExecutingInRemote() && 
			ServerSubscribers.isSome());
	}
	public static boolean executeTestLocal(InputParamsTM inputParams, Method presentTestCaseMethod) {
		if (executeTestRemote(inputParams)) {
			return false;
		}
		var listTestCaseFilter = inputParams.getListTestCasesName();
		if (!inputParams.isTestExecutingInRemote() || 
			listTestCaseFilter.isEmpty()) {
			return true;
		}
		return (presentTestCaseMethod.getName().compareTo(listTestCaseFilter.get(0))==0);
	}

	private boolean isTestExecutingInRemote(TestCaseTM testCase) {
		return testCase.getSuiteParent().getInputParams().isTestExecutingInRemote();
	}
	
	private void sendNotifications(TestCaseTM testCase) {
		for (var step : testCase.getListStep()) {
			for (var checks : step.getListChecksTM()) {
				for (var check : checks.getListChecks()) {
					sendNotificationIfNeeded(check, checks, testCase.getSuiteParent());
				}
			}
		}
	}
	
    private void sendNotificationIfNeeded(Check check, ChecksTM checksParent, SuiteTM suiteParent) {
    	if (check.getSend()==SendType.ALERT &&
    		!check.isOvercomed()) {
    		var inputParams = suiteParent.getInputParams();
    		if (inputParams.isAlarm() &&
	    		mustCheckSendAlarm(check, inputParams)) {
	            var alarm = new Alarm(check, checksParent);
	            alarm.send();
    		}
    	}
    }
    
    boolean mustCheckSendAlarm(Check check, InputParamsTM inputParams) {
		var listCodesAlarm = inputParams.getAlarmsToCheck();
		if (listCodesAlarm.isEmpty()) {
			return true;
		}
    	return listCodesAlarm.contains(check.getCode());
    }
	
}
