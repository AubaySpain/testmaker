package org.jorge2m.testmaker.boundary.aspects.validation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.jorge2m.testmaker.domain.suitetree.ChecksTM;
import org.jorge2m.testmaker.domain.suitetree.StepTM;
import org.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import org.jorge2m.testmaker.service.TestMaker;


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
    	TestCaseTM testCase = TestCaseTM.getTestCaseInExecution();
    	return (testCase.getLastStep());
    }
    
    private void finishValidation(InfoValidation infoValidation, StepTM step, boolean exceptionThrown) {
    	ChecksTM checksResult = infoValidation.getListResultValidation();
    	step.addChecksTM(checksResult);
    	if (exceptionThrown) {
    		checksResult.getStepParent().setNOKstateByDefault();
    	}
    	checksResult.checkValidations();
    	//step.storeEvidencies();
    }
}
