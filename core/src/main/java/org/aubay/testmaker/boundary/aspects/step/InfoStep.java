package org.jorge2m.testmaker.boundary.aspects.step;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.jorge2m.testmaker.domain.suitetree.StepTM;

public class InfoStep {

	private final MethodSignature methodSignature;
	private final Step stepAnnotation;
	private final StepTM step;
	
	private InfoStep(JoinPoint joinPoint) {
		this.methodSignature = (MethodSignature)joinPoint.getSignature();
		this.stepAnnotation = methodSignature.getMethod().getAnnotation(Step.class);
		this.step = getDatosStepFromStepAnnotation();
	}
	
	public static InfoStep from(JoinPoint joinPoint) {
		return (new InfoStep(joinPoint));
	}
	
	public Step getStepAnnotation() {
		return stepAnnotation;
	}
	
	public StepTM getDatosStep() {
		return step;
	}

	private StepTM getDatosStepFromStepAnnotation() {
		StepTM step = new StepTM();
		step.setDescripcion(stepAnnotation.description());
		step.setResExpected(stepAnnotation.expected());
		step.setSaveImagePage(stepAnnotation.saveImagePage());
		step.setSaveErrorPage(stepAnnotation.saveErrorData());
		step.setSaveHtmlPage(stepAnnotation.saveHtmlPage());
		step.setSaveNettrafic(stepAnnotation.saveNettraffic());
		step.setPathMethod(methodSignature.getDeclaringTypeName() + "." + methodSignature.getName());
		return step;
	}
}