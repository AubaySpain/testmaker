package org.jorge2m.testmaker.boundary.aspects.beforeafter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;


@Aspect
public class AfterMethodAspect {

	@Pointcut("@annotation(org.testng.annotations.AfterMethod)")
	public void annotationAfterMethodPointcut() {}

	@Pointcut("execution(* *(..))")
	public void atExecution(){}

	@Around("annotationAfterMethodPointcut() && atExecution()")
	public Object aroundAfterMethod(ProceedingJoinPoint joinPoint) throws Throwable {
		return manageAroundAfterMethod(joinPoint);
	}
	
	private Object manageAroundAfterMethod(ProceedingJoinPoint joinPoint) throws Throwable {
		return BeforeMethodAspect.manageAroundBeforeAfterMethods(joinPoint);
	}
}
