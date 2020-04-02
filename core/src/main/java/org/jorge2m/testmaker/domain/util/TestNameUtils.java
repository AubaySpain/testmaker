package org.jorge2m.testmaker.domain.util;

import java.util.List;

public class TestNameUtils {

	public static boolean isMethodNameInTestCaseList(String methodName, List<String> listTestCases) {
		for (String testCase : listTestCases) {
			if (isMethodNameTestCase(methodName, testCase)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isMethodNameTestCase(String methodName, String testCaseId) {
		return (
			testCaseId.compareTo(methodName)==0 ||  
			methodName.indexOf(getCodeFromTestCase(testCaseId))==0);
	}
	
	public static String getCodeFromTestCase(String testCase) {
		int posUnderscore = testCase.indexOf("_");
		if (posUnderscore<0) {
			return testCase;
		}
		return testCase.substring(0, posUnderscore);
	}
}
