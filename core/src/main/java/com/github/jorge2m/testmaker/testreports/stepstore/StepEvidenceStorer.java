package com.github.jorge2m.testmaker.testreports.stepstore;

import java.io.File;
import java.io.FileWriter;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;

public abstract class StepEvidenceStorer {
	
	private final StepEvidence evidenceType;
	private String content;

	protected abstract String captureContent(StepTM step);
	
	public void captureAndStoreContent(StepTM step) {
		this.content = captureContent(step);
	}
	
	protected StepEvidenceStorer(StepEvidence evidenceType) {
		this.evidenceType = evidenceType;
	}
	
	public String getContent() {
		return content;
	}
	
	public boolean existsFileEvidence(StepTM step) {
		String pathFile = getPathFile(step);
		return new File(pathFile).exists();
	}
	
	public void storeContentInFile(StepTM step) {
		String pathFile = getPathFile(step);
		if (!new File(pathFile).exists()) {
			saveContentEvidenceInFile(content, pathFile);
		}
	}
	
	public String getPathFile(StepTM step) {
		return evidenceType.getPathFile(step);
	}
	
	public void saveContentEvidenceInFile(String content, String pathFile) {
		saveContentEvidence(content, pathFile);
	}
	
	public static void saveContentEvidence(String content, String pathFile) {
		File file = new File(pathFile);
		try (FileWriter fw = new FileWriter(file)) {
			fw.write(content);
		}
		catch (Exception e) {
			Log4jTM.getLogger().warn("Problem saving File {}", pathFile, e);
		}
	}
	
}
