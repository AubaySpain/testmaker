package com.github.jorge2m.testmaker.testreports.stepstore;

import java.io.File;

import com.github.jorge2m.testmaker.domain.suitetree.StepTM;

public enum StepEvidence {
	IMAGEN("png", "icon-hardcopy.png", "Page Hardcopy"), 
	HTML("html", "icon-html.png", "Page Html"), 
	ERROR_PAGE("-error.html", "icon-error.png", "Information Error"), 
	HAR("har", "icon-nettraffic-har.png", "Http Nettraffic Har format (JSON)"), 
	HARP("harp", "icon-nettraffic-harp.png", "Http Nettraffic Harp format");
	
	private String fileExtension;
	private String nameIcon;
	private String tagInfo;
	
	private StepEvidence(String fileExtension, String nameIcon, String tagInfo) {
		this.fileExtension = fileExtension;
		this.nameIcon = nameIcon;
		this.tagInfo = tagInfo;
	}
	
	public String getPathFile(StepTM step) {
		String fileName = getNameFileEvidence(step);
		return (step.getPathDirectory() + File.separator + fileName);
	}
	
	public boolean fileExists(StepTM step) {
		String pathFileStep = getPathFile(step);
		File indexFile = new File(pathFileStep);
		return indexFile.exists();
	}
	
	public String getNameFileEvidence(StepTM step) {
		String extension = "." + fileExtension;
		return ("Step-" + Integer.toString(step.getNumber()) + extension);
	}
	
	public String getFileExtension() {
		return fileExtension;
	}
	
	public String getNameIcon() {
		return nameIcon;
	}
	
	public String getTagInfo() {
		return tagInfo;
	}
	
}
