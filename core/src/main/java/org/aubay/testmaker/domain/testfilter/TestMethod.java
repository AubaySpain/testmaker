package org.jorge2m.testmaker.domain.testfilter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.testng.annotations.Factory;
import org.testng.annotations.Test;

public class TestMethod {
	//final Annotation annotation;
	final String description;
	final String[] groups;
	final TestMethodData methodData = new TestMethodData();
	//Method method;

	public TestMethod(Test annotationTest, Method method) {
		//this.annotation = annotationTest;
		description = annotationTest.description();
		groups = annotationTest.groups();
		methodData.setTestCaseName(method.getName());
		methodData.setTestCaseDescription(getDescription());
	}
	public TestMethod(Factory annotationFactory, Method method) {
		//this.annotation = annotationFactory;
		description = annotationFactory.description();
		groups = annotationFactory.groups();
		methodData.setTestCaseName(method.getName());
		methodData.setTestCaseDescription(getDescription());
	}
	public TestMethod(String nameMethod) {
		//this.annotation = annotationTest;
		description = "";
		groups = new String[] {"Canal:all_App:all"};
		methodData.setTestCaseName(nameMethod);
	}

	public String getDescription() {
		return description;
//		if (annotation instanceof Test) {
//			return ((Test)annotation).description();
//		} else {
//			return ((Factory)annotation).description();
//		}
	}
	public String[] getGroups() {
		return groups;
//		if (annotation instanceof Test) {
//			return ((Test)annotation).groups();
//		} else {
//			return ((Factory)annotation).groups();
//		}
	}

//	public Method getMethod() {
//		return this.method;
//	}

	public TestMethodData getData() {
		return methodData;
//		TestMethodData testData = new TestMethodData();
//		testData.setTestCaseName(getMethod().getName());
//		testData.setTestCaseDescription(getDescription());
//		return testData;
	}
}
