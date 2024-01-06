package com.github.jorge2m.testmaker.testreports.testcasestore;

import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.github.jorge2m.testmaker.domain.suitetree.VideoRecorder;

public class VideoStorer extends TestCaseEvidenceStorerBase {

	public VideoStorer(TestCaseTM testcase) {
		super(TestCaseEvidence.VIDEO, testcase);
	}
	
	@Override
	protected void store() {
		if (testcase.getInputParamsSuite().isAvailableRecord() &&
			testcase.getDriver()!=null) {
			VideoRecorder.make(testcase.getDriver()).stop();
		}
	}
    
}
