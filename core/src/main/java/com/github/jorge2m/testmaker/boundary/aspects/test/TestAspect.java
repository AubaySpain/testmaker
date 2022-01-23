package com.github.jorge2m.testmaker.boundary.aspects.test;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.domain.ServerSubscribers;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteBean;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.github.jorge2m.testmaker.service.TestMaker;

import java.lang.reflect.Method;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


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
		InputParamsTM inputParams = testCase.getInputParamsSuite();
		if (executeTestRemote(inputParams)) {
			return executeTestRemote(joinPoint, testCase);
		} else {
			return executeTest(testCase, joinPoint);
		}
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
		Method presentMethod = ((MethodSignature)joinPoint.getSignature()).getMethod();
		if (executeTestLocal(inputParams, presentMethod)) {
			testCase.makeWebDriver();
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
	
}
