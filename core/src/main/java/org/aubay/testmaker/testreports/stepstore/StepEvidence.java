package org.aubay.testmaker.testreports.stepstore;

import java.io.File;

import org.aubay.testmaker.domain.suitetree.StepTM;

public enum StepEvidence {
	imagen("png"), 
	html("html"), 
	errorpage("-error.html"), 
	har("har"), 
	harp("harp");
	
	public String fileExtension;
	private StepEvidence(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	
	public String getPathFile(StepTM step) {
		String fileName = getNameFileEvidence(step);
		return (step.getPathDirectory() + File.separator + fileName);
	}
	public String getNameFileEvidence(StepTM step) {
		String extension = "." + fileExtension;
		return ("Step-" + Integer.toString(step.getNumber()) + extension);
	}
}
