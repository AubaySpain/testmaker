package com.github.jorge2m.testmaker.testreports.testcasestore;

import java.io.File;

import com.github.jorge2m.testmaker.domain.suitetree.TestCaseBean;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;

public enum TestCaseEvidence {
	EXCEPTION("Exception.txt", "exception.gif", "Exception info"),
	LOGS("Logs.txt", "logs.gif", "Logs");
	
	private String fileName;
	private String nameIcon;
	private String tagInfo;
	
	private TestCaseEvidence(String fileName, String nameIcon, String tagInfo) {
		this.fileName = fileName;
		this.nameIcon = nameIcon;
		this.tagInfo = tagInfo;
	}
	
	public String getPathFile(TestCaseTM testcase) {
		return testcase.getTestPathDirectory() + File.separator + getNameFileEvidence();
	}
	public String getPathFile(TestCaseBean testcase) {
		return testcase.getTestPathDirectory() + File.separator + getNameFileEvidence();
	}	
	
	public boolean fileExists(TestCaseTM testcase) {
		String pathFile = getPathFile(testcase);
		File indexFile = new File(pathFile);
		return indexFile.exists();
	}
	public boolean fileExists(TestCaseBean testcase) {
		String pathFile = getPathFile(testcase);
		File indexFile = new File(pathFile);
		return indexFile.exists();
	}
	
	public String getNameFileEvidence() {
		return fileName;
	}
	
	public String getNameIcon() {
		return nameIcon;
	}
	
	public String getTagInfo() {
		return tagInfo;
	}

}
