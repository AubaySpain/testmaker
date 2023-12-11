package com.github.jorge2m.testmaker.testreports.testcasestore;

import java.io.File;

import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;

public class TestCaseEvidencesStorer {

	private TestCaseTM testcase;

	public TestCaseEvidencesStorer() {}
	
	public TestCaseEvidencesStorer(TestCaseTM testcase) {
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
		var evidenceStorer = TestCaseEvidenceStorerBase.evidenceStorerFactory(evidence, testcase);
		if (evidenceStorer!=null && !evidenceStorer.existsFileEvidence()) {
			evidenceStorer.store();
		}
	}
		
}
