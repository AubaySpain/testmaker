package com.github.jorge2m.testmaker.testreports.testcasestore;

import java.io.File;

import com.github.jorge2m.testmaker.domain.suitetree.TestCaseBean;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.github.jorge2m.testmaker.testreports.stepstore.StepEvidenceStorer;

public abstract class TestCaseEvidenceStorer {

	private final TestCaseEvidence evidenceType;
	private String content;

	protected abstract String captureContent(TestCaseTM testcase);
	
	public void captureAndStoreContent(TestCaseTM testcase) {
		this.content = captureContent(testcase);
	}
	
	protected TestCaseEvidenceStorer(TestCaseEvidence evidenceType) {
		this.evidenceType = evidenceType;
	}
	
	public String getContent() {
		return content;
	}
	
	public boolean existsFileEvidence(TestCaseTM testcase) {
		String pathFile = getPathFile(testcase);
		return new File(pathFile).exists();
	}
	
	public boolean existsFileEvidence(TestCaseBean testcase) {
		String pathFile = getPathFile(testcase);
		return new File(pathFile).exists();
	}	
	
	public void storeContentInFile(TestCaseTM testcase) {
		String pathFile = getPathFile(testcase);
		if (!new File(pathFile).exists()) {
			saveContentEvidenceInFile(content, pathFile);
		}
	}
	
	public String getPathFile(TestCaseTM testcase) {
		return evidenceType.getPathFile(testcase);
	}
	public String getPathFile(TestCaseBean testcase) {
		return evidenceType.getPathFile(testcase);
	}	
	
	public void saveContentEvidenceInFile(String content, String pathFile) {
		StepEvidenceStorer.saveContentEvidence(content, pathFile);
	}
	
}
