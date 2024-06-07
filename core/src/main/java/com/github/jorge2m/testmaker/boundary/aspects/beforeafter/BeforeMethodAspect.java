package com.github.jorge2m.testmaker.boundary.aspects.beforeafter;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.testng.ITestNGMethod;

import com.github.jorge2m.testmaker.boundary.aspects.test.TestAspect;
import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.domain.SuitesExecuted;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestRunTM;


@Aspect
public class BeforeMethodAspect {

	@Pointcut("@annotation(org.testng.annotations.BeforeMethod)")
	public void annotationBeforeMethodPointcut() {}

	@Pointcut("execution(* *(..))")
	public void atExecution(){}

	@Around("annotationBeforeMethodPointcut() && atExecution()")
	public Object aroundBeforeMethod(ProceedingJoinPoint joinPoint) throws Throwable {
		return manageAroundBeforeMethod(joinPoint);
	}
	
	private Object manageAroundBeforeMethod(ProceedingJoinPoint joinPoint) throws Throwable {
		return manageAroundBeforeAfterMethods(joinPoint);
	}
	
	public static Object manageAroundBeforeAfterMethods(ProceedingJoinPoint joinPoint) throws Throwable {
		Method methodParam = getMethodParam(joinPoint);
		if (methodParam==null) {
			throw new IllegalArgumentException("Is necessary a " + Method.class + " argument in the @BeforeMethod/@AfeterMethod method");
		}

		TestRunTM testRun = getTestRun(methodParam);
		InputParamsTM inputParams = testRun.getSuiteParent().getInputParams();
		boolean executeTest = TestAspect.executeTestLocal(inputParams, joinPoint);
		if (executeTest) {
			return joinPoint.proceed();
		}
		return null;
	}
	
	private static Method getMethodParam(ProceedingJoinPoint joinPoint) {
		for (Object argument : joinPoint.getArgs()) {
			if (argument instanceof Method) {
				return (Method)argument;
			}
		}
		return null;
	}
	
	private static TestRunTM getTestRun(Method method) {
		for (SuiteTM suite : SuitesExecuted.getSuitesExecuted()) {
			for (TestRunTM testRun : suite.getListTestRuns()) {
				for (ITestNGMethod testMethod : testRun.getTestNgContext().getAllTestMethods()) {
					if (testMethod.getConstructorOrMethod().getMethod()==method) {
						return testRun;
					}
				}
			}
		}
		return null;
	}
}
