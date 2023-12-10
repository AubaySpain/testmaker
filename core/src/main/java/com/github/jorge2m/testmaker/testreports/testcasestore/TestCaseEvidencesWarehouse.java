package com.github.jorge2m.testmaker.testreports.testcasestore;

import java.io.File;

import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;

public class TestCaseEvidencesWarehouse {

	private TestCaseTM testcase;

	public TestCaseEvidencesWarehouse() {}
	
	public TestCaseEvidencesWarehouse(TestCaseTM testcase) {
		this.testcase = testcase;
	}
	
	public void captureAndStore() {
		createPathForEvidencesStore();
		for (var evidence : TestCaseEvidence.values()) {
			storeEvidence(evidence);
		}
	}	
	
	private void createPathForEvidencesStore() {
		File directorio = new File(testcase.getTestPathDirectory());
		if (!directorio.exists()) {
			directorio.mkdirs();
		}
	}
	
	private void storeEvidence(TestCaseEvidence evidence) {
		var evidenceStorer = evidenceStorerFactory(evidence);
		if (evidenceStorer!=null && !evidenceStorer.existsFileEvidence(testcase)) {
			evidenceStorer.captureAndStoreContent(testcase);
			evidenceStorer.storeContentInFile(testcase);
		}
	}
	
	private TestCaseEvidenceStorer evidenceStorerFactory(TestCaseEvidence evidence) {
		if (evidence==TestCaseEvidence.EXCEPTION) {
			return new ExceptionStorer(); 
		}
		return null;
	}
		
}
