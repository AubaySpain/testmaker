package com.github.jorge2m.testmaker.boundary.aspects.validation;

import java.util.NoSuchElementException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import com.github.jorge2m.testmaker.domain.StateExecution;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.github.jorge2m.testmaker.service.TestMaker;


@Aspect
public class ValidationAspect {
	
    @Pointcut("@annotation(Validation)")
    public void annotationValidationPointcut() {}
    
    @Pointcut("execution(* *(..))")
    public void atExecution(){}
    
    @Before("annotationValidationPointcut()")
    public void before(JoinPoint joinPoint) {
    	TestMaker.skipTestsIfSuiteStopped();
    }
    
    @AfterThrowing(
    	pointcut="annotationValidationPointcut() && atExecution()", 
    	throwing="ex")
    public void doRecoveryActions(JoinPoint joinPoint, Throwable ex) {
    	StepTM step = getLastStep();
    	InfoValidation infoValidation = InfoValidation.from(joinPoint);
    	finishValidation(infoValidation, step, true);
    }
    
    @AfterReturning(
    	pointcut="annotationValidationPointcut() && atExecution()", 
    	returning="resultMethod")
    public void grabValidationAfter(JoinPoint joinPoint, Object resultMethod) throws Throwable {
    	StepTM step = getLastStep();
    	InfoValidation infoValidation = InfoValidation.from(joinPoint, resultMethod);
    	finishValidation(infoValidation, step, false);
    }
    
    private StepTM getLastStep() {
    	TestCaseTM testCase = TestCaseTM.getTestCaseInExecution()
    			.orElseThrow(() -> new NoSuchElementException());
    	return (testCase.getLastStep());
    }
    
    private void finishValidation(InfoValidation infoValidation, StepTM step, boolean exceptionThrown) {
    	ChecksTM checksResult = infoValidation.getListResultValidation();
    	step.addChecksTM(checksResult);
    	if (exceptionThrown) {
    		checksResult.getStepParent().setKOstateByDefault();
    	}
    	checksResult.checkValidations();
    	if (step.getState()==StateExecution.FINISHED) {
    		//Nos encontramos en una Validación asociada a un @Step pero posterior a él
    		boolean exceptionInStep = step.isExcepExists() || exceptionThrown;
    		step.end(exceptionInStep);
    	}
    }

}
