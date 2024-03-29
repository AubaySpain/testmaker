package com.github.jorge2m.testmaker.boundary.aspects.step;

import java.util.NoSuchElementException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import com.github.jorge2m.testmaker.boundary.aspects.MatcherWithMethodParams;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.github.jorge2m.testmaker.service.TestMaker;


@Aspect
public class StepAspect {

	@Pointcut("@annotation(Step)")
	public void annotationStepPointcut() {}

	@Pointcut("execution(* *(..))")
	public void atExecution(){}

	@Before("annotationStepPointcut() && atExecution()")
	public void before(JoinPoint joinPoint) {
		InfoStep infoStep = InfoStep.from(joinPoint);
		TestCaseTM testCase = TestCaseTM.getTestCaseInExecution()
				.orElseThrow(() -> new NoSuchElementException());
		
		TestMaker.skipTestsIfSuiteEnded(testCase.getSuiteParent());
		StepTM step = infoStep.getDatosStep();
		testCase.addStep(step);
		setInitDataStep(infoStep, joinPoint, step);
	}

	private void setInitDataStep(InfoStep infoStep, JoinPoint joinPoint, StepTM datosStep) {
		var matcher = MatcherWithMethodParams.from(joinPoint);
		String stepDescription = infoStep.getStepAnnotation().description();
		String stepResExpected = infoStep.getStepAnnotation().expected();
		datosStep.setDescripcion(matcher.match(stepDescription)); 
		datosStep.setResExpected(matcher.match(stepResExpected));
		datosStep.setTimeInicio(System.currentTimeMillis());
	}

	@AfterThrowing(
		pointcut="annotationStepPointcut() && atExecution()", 
		throwing="ex")
	public void doRecoveryActions(JoinPoint joinPoint, Throwable ex) {
		TestCaseTM testCase = TestCaseTM.getTestCaseInExecution()
				.orElseThrow(() -> new NoSuchElementException());
		
		StepTM currentStep = testCase.getCurrentStepInExecution();
		if (currentStep!=null) {
			if (!testCase.isLastStep(currentStep)) {
				//In the case of anidated Steps...
				//If isn't the last step then the exception is generated in other deeper step
				currentStep.end(false);
			} else {
				currentStep.end(true);
			}
		}
	}

	@AfterReturning(
		pointcut="annotationStepPointcut() && atExecution()")
	public void grabValidationAfter(JoinPoint joinPoint) throws Throwable {
		TestCaseTM testCase = TestCaseTM.getTestCaseInExecution()
				.orElseThrow(() -> new NoSuchElementException());
		
		StepTM currentStep = testCase.getCurrentStepInExecution();
		currentStep.end(false);
	}
}
