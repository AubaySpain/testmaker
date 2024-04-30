package com.github.jorge2m.testmaker.domain.testfilter;

import java.lang.reflect.Method;

import org.testng.annotations.Factory;
import org.testng.annotations.Test;

public class TestMethod {

	final String description;
	final String[] groups;
	final TestMethodData methodData = new TestMethodData();

	public TestMethod(Test annotationTest, Method method) {
		description = annotationTest.description();
		groups = annotationTest.groups();
		methodData.setTestCaseName(method.getName());
		methodData.setTestCaseDescription(getDescription());
	}
	public TestMethod(Factory annotationFactory, Method method) {
		description = annotationFactory.description();
		groups = annotationFactory.groups();
		methodData.setTestCaseName(method.getName());
		methodData.setTestCaseDescription(getDescription());
	}
	public TestMethod(String nameMethod) {
		description = "";
		groups = new String[] {"Canal:all_App:all"};
		methodData.setTestCaseName(nameMethod);
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
