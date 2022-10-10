package com.github.jorge2m.testmaker.boundary.aspects.test;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.testng.annotations.Test;

import com.github.jorge2m.testmaker.conf.SendType;
import com.github.jorge2m.testmaker.domain.Alarm;
import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.domain.ServerSubscribers;
import com.github.jorge2m.testmaker.domain.StateExecution;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteBean;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.github.jorge2m.testmaker.service.TestMaker;

import java.lang.reflect.Method;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


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
		TestCaseTM testCase = TestCaseTM.getTestCaseInExecution()
				.orElseThrow(() -> new NoSuchElementException());
		
		TestMaker.skipTestsIfSuiteEnded(testCase.getSuiteParent());
		Object test = executeTest(joinPoint, testCase);
		if (!isTestExecutingInRemote(testCase)) {			
			sendNotifications(testCase);
		}
		return test;
	}
	
	private Object executeTest(ProceedingJoinPoint joinPoint, TestCaseTM testCase) throws Throwable {
		fitTestToRamp(testCase);
		testCase.setStateRun(StateExecution.Running);
		if (executeTestRemote(testCase.getSuiteParent().getInputParams())) {
			return executeTestRemote(joinPoint, testCase);
		} else {
			return executeTest(testCase, joinPoint);
		}
	}

	private final static int MAX_SECONDS_DELAY_TEST = 300; 
	
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
		InputParamsTM inputParams = suiteParent.getInputParams();
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
		if (secondsFromInitSuite >= secondMustInitNewTestCase) {
			return false;
		}
		
		return true;
	}
	
	private int getTestCasesRunning(SuiteTM suiteTM) {
		return suiteTM.getNumberTestCases(StateExecution.Running);
	}

	private Object executeTestRemote(ProceedingJoinPoint joinPoint, TestCaseTM testCase) 
			throws ExecuteRemoteTestException {
		try {
			Optional<SuiteBean> suiteBean = ServerSubscribers.sendTestToRemoteServer(testCase, joinPoint.getTarget());
			if (!suiteBean.isPresent()) {
				throw new ExecuteRemoteTestException("Problem executing test Remote");
			}
		} catch (Exception e) {
		    throw new ExecuteRemoteTestException("Problem executing test Remote", e);
		}
	
		//TODO si un @Test retorna un valor <> de void tendremos problemas. Se debería serializar el objeto de respuesta
		return null;
	}
	
	private Object executeTest(TestCaseTM testCase, ProceedingJoinPoint joinPoint) throws Throwable {
		InputParamsTM inputParams = testCase.getInputParamsSuite();
		MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
		if (executeTestLocal(inputParams, methodSignature.getMethod())) {
			Test testAnnotation = methodSignature.getMethod().getAnnotation(Test.class);
			testCase.makeInitObjects(testAnnotation.create());
			return joinPoint.proceed();
		}
		return null;
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
		List<String> listTestCaseFilter = inputParams.getListTestCasesName();
		if (!inputParams.isTestExecutingInRemote() || 
			listTestCaseFilter.size()==0) {
			return true;
		}
		return (presentTestCaseMethod.getName().compareTo(listTestCaseFilter.get(0))==0);
	}

	private boolean isTestExecutingInRemote(TestCaseTM testCase) {
		return testCase.getSuiteParent().getInputParams().isTestExecutingInRemote();
	}
	
	private void sendNotifications(TestCaseTM testCase) {
		for (StepTM step : testCase.getListStep()) {
			for (ChecksTM checks : step.getListChecksTM()) {
				for (Check check : checks.getListChecks()) {
					sendNotificationIfNeeded(check, checks, testCase.getSuiteParent());
				}
			}
		}
	}
	
    private void sendNotificationIfNeeded(Check check, ChecksTM checksParent, SuiteTM suiteParent) {
    	if (check.getSend()==SendType.Alert &&
    		!check.isOvercomed()) {
  	        InputParamsTM inputParams = suiteParent.getInputParams();
	        if (inputParams.isAlarm()) {
	            Alarm alarm = new Alarm(check, checksParent);
	            alarm.send();
	        }
    	}
    }
	
}
