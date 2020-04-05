package com.github.jorge2m.testmaker.testreports.stepstore;

public class StepEvidenceContent {

	private StepEvidence stepEvidence;
	private String content;
	
	public StepEvidenceContent() {}
	
	public StepEvidenceContent(StepEvidence stepEvidence, String content) {
		this.stepEvidence = stepEvidence;
		this.content = content;
	}
	
	public StepEvidence getStepEvidence() {
		return stepEvidence;
	}
	public void setStepEvidence(StepEvidence stepEvidence) {
		this.stepEvidence = stepEvidence;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
