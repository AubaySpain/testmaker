package com.github.jorge2m.testmaker.boundary.listeners;

import org.testng.*;
import org.testng.annotations.*;

import com.github.jorge2m.testmaker.domain.TestCaseParams;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteTM;

import java.lang.reflect.*;


public class MyTransformer implements IAnnotationTransformer {

	@SuppressWarnings("rawtypes")
	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		SuiteTM suite = SuiteTM.getSuiteCreatedInPresentThread();
		TestCaseParams testCaseData = suite.getInputParams().getTestCaseParams(testMethod.getName());
		if (testCaseData!=null) {
			Integer invocationCount = testCaseData.getInvocationCount(); 
			if (invocationCount!=null) {
				annotation.setInvocationCount(invocationCount);	
			}
			Integer threadPoolSize = testCaseData.getThreadPoolSize();
			if (threadPoolSize!=null) {
				annotation.setThreadPoolSize(threadPoolSize);
			}
		}	
	}

}
