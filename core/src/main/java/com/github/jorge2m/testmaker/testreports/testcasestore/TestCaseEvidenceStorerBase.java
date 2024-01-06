package com.github.jorge2m.testmaker.testreports.testcasestore;

import java.io.File;

import com.github.jorge2m.testmaker.domain.suitetree.TestCaseBean;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.github.jorge2m.testmaker.testreports.stepstore.StepEvidenceStorer;

public abstract class TestCaseEvidenceStorerBase {

	private final TestCaseEvidence evidenceType;
	protected final TestCaseTM testcase;

	public static TestCaseEvidenceStorerBase evidenceStorerFactory(TestCaseEvidence evidence, TestCaseTM testcase) {
		if (evidence==TestCaseEvidence.VIDEO) {
			return new VideoStorer(testcase);
		}
		
		if (evidence==TestCaseEvidence.EXCEPTION) {
			return new ExceptionStorer(testcase); 
		}
		if (evidence==TestCaseEvidence.LOGS) {
			return new LogsStorer(testcase);
		}
		return null;
	}
	
	protected abstract void store();

	protected TestCaseEvidenceStorerBase(TestCaseEvidence evidenceType, TestCaseTM testcase) {
		this.evidenceType = evidenceType;
		this.testcase = testcase;
	}
	
	protected boolean existsFileEvidence() {
		String pathFile = getPathFile();
		return new File(pathFile).exists();
	}
	
	protected void storeInFile(String content) {
		String pathFile = getPathFile();
		if (!new File(pathFile).exists()) {
			saveContentEvidenceInFile(content, pathFile);
		}
	}
	
	protected String getPathFile() {
		return evidenceType.getPathFile(testcase);
	}
	protected String getPathFile(TestCaseBean testcase) {
		return evidenceType.getPathFile(testcase);
	}	
	
	protected void saveContentEvidenceInFile(String content, String pathFile) {
		StepEvidenceStorer.saveContentEvidence(content, pathFile);
	}
	
	protected boolean isRemote() {
		return testcase.getInputParamsSuite().isTestExecutingInRemote();
	}
	
}
