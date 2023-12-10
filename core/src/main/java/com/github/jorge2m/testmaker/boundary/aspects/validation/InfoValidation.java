package com.github.jorge2m.testmaker.boundary.aspects.validation;

import java.lang.reflect.Method;
import java.util.NoSuchElementException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import com.github.jorge2m.testmaker.boundary.aspects.MatcherWithMethodParams;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;

public class InfoValidation {

	private enum ReturnValidation {BOOLEAN, CHECKSTM, OTHER}
	
	private final JoinPoint joinPoint;
	private final MethodSignature methodSignature;
	private final Validation valAnnotation;
	private final Object resultMethod;
	private final ChecksTM listResultValidations;
	
	private InfoValidation(JoinPoint joinPoint, Object resultMethod) {
		this.joinPoint = joinPoint;
		this.methodSignature = (MethodSignature) joinPoint.getSignature();
		this.valAnnotation = getValidationAnnotation(joinPoint);
		this.resultMethod = resultMethod;
		this.listResultValidations = getValResultFromMethodData();
	}

	
	private InfoValidation(JoinPoint joinPoint) {
		this.joinPoint = joinPoint;
		this.methodSignature = (MethodSignature) joinPoint.getSignature();
		this.valAnnotation = getValidationAnnotation(joinPoint);
		this.listResultValidations = getValResultFromMethodData();
		this.resultMethod = null;
	}
	
	public static InfoValidation from(JoinPoint joinPoint, Object resultMethod) {
		return (new InfoValidation(joinPoint, resultMethod));
	}
	
	public static InfoValidation from(JoinPoint joinPoint) {
		return (new InfoValidation(joinPoint));
	}
	
	public ChecksTM getListResultValidation() {
		return listResultValidations;
	}

	
    private Validation getValidationAnnotation(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Validation validationAnnotation = method.getAnnotation(Validation.class);
        return (validationAnnotation);
    }
	
	private ChecksTM getValResultFromMethodData() {
		ChecksTM valResult = getValidationResultFromObjectMethodReturn();
		valResult.setPathMethod(methodSignature.getDeclaringTypeName() + "." + methodSignature.getName());
		modifyValidationResultAccordingAnnotationParams(valResult);
		return valResult;
	}
	
    private ChecksTM getValidationResultFromObjectMethodReturn() {
    	TestCaseTM testCaseInThread = TestCaseTM.getTestCaseInExecution()
    			.orElseThrow(NoSuchElementException::new);
    	
    	StepTM step = testCaseInThread.getLastStep();
    	if (resultMethod!=null) {
    		return getValidationResult(step);
    	} else {
    		ChecksTM valResult = ChecksTM.getNew(step);
    		valResult.add(new Check());
    		return valResult;
    	}
    }
    
    private ChecksTM getValidationResult(StepTM step) {
    	ChecksTM valResult = ChecksTM.getNew(step);
		switch (getReturnValidation()) {
		case BOOLEAN:
        	Check validation = new Check();
        	validation.setOvercomed((Boolean)resultMethod);
        	valResult.add(validation);
        	return valResult;
		case CHECKSTM:
        	valResult = (ChecksTM)resultMethod;
        	return valResult;
        default:
	        throw (new RuntimeException(
		        	"The return of a method marked with @Validation annotation must be of type boolean or " + 
		        	ChecksTM.class.getName()));
		}
    }
    
	private ReturnValidation getReturnValidation() {
        if (resultMethod instanceof Boolean) {
        	return ReturnValidation.BOOLEAN;
        }
        if (resultMethod instanceof ChecksTM) {
        	return ReturnValidation.CHECKSTM;
        }
        return ReturnValidation.OTHER;
	}

    private void modifyValidationResultAccordingAnnotationParams(ChecksTM valResult) {
    	if (getReturnValidation()==ReturnValidation.BOOLEAN && 
	    	valResult.size()>0) {
	    	if ("".compareTo(valResult.get(0).getDescription())==0) {
	    		MatcherWithMethodParams matcher = MatcherWithMethodParams.from(joinPoint);
	    		String descripValidationMatched = matcher.match(valAnnotation.description());
	    		valResult.get(0).setDescription(descripValidationMatched);
	    	}
	    	
	    	if (valResult.get(0).getLevelResult()==State.UNDEFINED &&
	    		valAnnotation.level()!=null) {
	    		valResult.get(0).setLevelResult(valAnnotation.level());
	    	}
	    	
    		valResult.get(0).setStore(valAnnotation.store());
    		valResult.get(0).setSend(valAnnotation.send());
    	}
    }
}