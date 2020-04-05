package org.jorge2m.testmaker.testreports.stepstore;

import java.io.File;
import java.io.FileWriter;

import org.jorge2m.testmaker.conf.Log4jConfig;
import org.jorge2m.testmaker.domain.suitetree.StepTM;

public abstract class EvidenceStorer {
	
	final StepEvidence evidenceType;
	String content;

	abstract protected String captureContent(StepTM step);
	
	public void captureAndStoreContent(StepTM step) {
		this.content = captureContent(step);
	}
	
	public EvidenceStorer(StepEvidence evidenceType) {
		this.evidenceType = evidenceType;
	}
	
	public String getContent() {
		return content;
	}
	
	public void recoveryContent(StepTM step) {
		this.content = step.getEvidencesWarehouse().getEvidenceContent(evidenceType);
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
		try {
			File file = new File(pathFile);
			try (FileWriter fw = new FileWriter(file)) {
				fw.write(content);
			}
		} 
		catch (Exception e) {
			Log4jConfig.pLogger.warn("Problem saving File " + pathFile, e);
		}
	}
	
}
