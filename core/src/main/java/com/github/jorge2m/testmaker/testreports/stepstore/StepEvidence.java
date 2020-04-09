package com.github.jorge2m.testmaker.testreports.stepstore;

import java.io.File;

import com.github.jorge2m.testmaker.domain.suitetree.StepTM;

public enum StepEvidence {
	Imagen("png", "icon-hardcopy.png", "Page Hardcopy"), 
	Html("html", "icon-html.png", "Page Html"), 
	ErrorPage("-error.html", "icon-error.png", "Information Error"), 
	Har("har", "icon-nettraffic-har", "Http Nettraffic Har format (JSON)"), 
	Harp("harp", "icon-nettraffic-harp", "Http Nettraffic Harp format");
	
	public String fileExtension;
	public String nameIcon;
	public String tagInfo;
	private StepEvidence(String fileExtension, String nameIcon, String tagInfo) {
		this.fileExtension = fileExtension;
		this.nameIcon = nameIcon;
		this.tagInfo = tagInfo;
	}
	
	public String getPathFile(StepTM step) {
		String fileName = getNameFileEvidence(step);
		return (step.getPathDirectory() + File.separator + fileName);
	}
	public String getNameFileEvidence(StepTM step) {
		String extension = "." + fileExtension;
		return ("Step-" + Integer.toString(step.getNumber()) + extension);
	}
	public String getNameIcon() {
		return nameIcon;
	}
	public String getTagInfo() {
		return tagInfo;
	}
}
