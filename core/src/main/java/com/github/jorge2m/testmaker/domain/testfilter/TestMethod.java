package com.github.jorge2m.testmaker.domain.testfilter;

import java.lang.reflect.Method;

import org.testng.annotations.Factory;
import org.testng.annotations.Test;

public class TestMethod {

	final String testName;
	final String description;
	final String[] groups;
	final TestMethodData methodData = new TestMethodData();

	public TestMethod(Test annotationTest, Method method) {
		testName = annotationTest.testName();
		description = annotationTest.description();
		groups = annotationTest.groups();
		methodData.setTestCaseName(method.getName());
		methodData.setTestCaseDescription(getDescription());
	}
	public TestMethod(Factory annotationFactory, Method method) {
		testName = annotationFactory.testName();
		description = annotationFactory.description();
		groups = annotationFactory.groups();
		methodData.setTestCaseName(method.getName());
		methodData.setTestCaseDescription(getDescription());
	}
	public TestMethod(String nameMethod) {
		testName = "";
		description = "";
		groups = new String[] {"Canal:all_App:all"};
		methodData.setTestCaseName(nameMethod);
	}

	public String getTestName() {
		return testName;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String[] getGroups() {
		return groups;
	}

	public TestMethodData getData() {
		return methodData;
	}
	
}
