package com.github.jorge2m.testmaker.domain.testfilter;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TestMethodData {
	private String testCaseName;
	private String testCaseDescription;
	
	public String getTestCaseName() {
		return testCaseName;
	}
	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}
	public String getTestCaseDescription() {
		return testCaseDescription;
	}
	public void setTestCaseDescription(String testCaseDescription) {
		this.testCaseDescription = testCaseDescription;
	}
}
