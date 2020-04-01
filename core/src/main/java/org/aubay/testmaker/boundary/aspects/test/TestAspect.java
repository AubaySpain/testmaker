package org.aubay.testmaker.boundary.aspects.test;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.aubay.testmaker.domain.InputParamsTM;
import org.aubay.testmaker.domain.ServerSubscribers;
import org.aubay.testmaker.domain.suitetree.TestCaseTM;
import org.aubay.testmaker.service.TestMaker;

import java.lang.reflect.Method;
import java.util.List;


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
		TestCaseTM testCase = TestCaseTM.getTestCaseInExecution();
		if (testCase!=null) {
			TestMaker.skipTestsIfSuiteEnded(testCase.getSuiteParent());
		}
		
		InputParamsTM inputParams = testCase.getInputParamsSuite();
		if (executeTestRemote(inputParams)) {
			ServerSubscribers.sendTestToRemoteServer(testCase, joinPoint.getTarget());
			//TODO si un @Test retorna un valor <> de void tendremos problemas. Se debería serializar el objeto de respuesta
			return null;
		} else {
			return executeTest(testCase, joinPoint);
		}
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
