package com.github.jorge2m.testmaker.testreports.stepstore;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestRunTM;

public class StepEvidencesWarehouse {
	
	private List<StepEvidenceContent> storedEvidences = new ArrayList<>();
	private StepTM step;

	public StepEvidencesWarehouse() {}
	
	public StepEvidencesWarehouse(StepTM step) {
		this.step = step;
	}
	
	public void setStep(StepTM step) {
		this.step = step;
	}
	
	public List<StepEvidenceContent> getStoredEvidences() {  
		return storedEvidences;
	}
	public void setStoredEvidences(List<StepEvidenceContent> storedEvidences) {
		this.storedEvidences = storedEvidences;
	}
	public void addEvidence(StepEvidenceContent stepEvidenceContent) {
		storedEvidences.add(stepEvidenceContent);
	}
	public String getEvidenceContent(StepEvidence evidence) {
		for (var stepEvidenceContent : storedEvidences) {
			if (stepEvidenceContent.getStepEvidence()==evidence) {
				return stepEvidenceContent.getContent();
			}
		}
		return "";
	}
	
	public void captureAndStore(Storage typeStorage) {
		if (isNecessariStorage(step)) {
			if (typeStorage.inFile()) {
				createPathForEvidencesStore(step);
			}
			for (var evidence : StepEvidence.values()) {
				if (step.isNecessaryStorage(evidence)) {
					storeEvidence(evidence, typeStorage);
				}
			}
		}
	}
	
	private void storeEvidence(StepEvidence evidence, Storage typeStorage) {
		var evidenceStorer = evidenceStorerFactory(evidence);
		if (evidenceStorer!=null) {
			if (typeStorage.inFile() && !evidenceStorer.existsFileEvidence(step)) {
				evidenceStorer.captureAndStoreContent(step);
				evidenceStorer.storeContentInFile(step);
			}
			if (typeStorage.inMemory() && !existsEvidenceInMemory(evidence)) {
				evidenceStorer.captureAndStoreContent(step);
				addEvidence(new StepEvidenceContent(evidence, evidenceStorer.getContent()));
			}
		}
	}
	
	private boolean existsEvidenceInMemory(StepEvidence evidenceType) {
		for (var stepEvidence : storedEvidences) {
			if (stepEvidence.getStepEvidence()==evidenceType) {
				return true;
			}
		}
		return false;
	}
	
	public void moveContentEvidencesToFile() {
		var listStepEvidences = getStoredEvidences();
		if (listStepEvidences.isEmpty()) {
			return;
		}
		
		createPathForEvidencesStore(step);
		for (StepEvidenceContent evidence : listStepEvidences) {
			var evidenceStorer = evidenceStorerFactory(evidence.getStepEvidence());
			if (evidenceStorer!=null) {
				evidenceStorer.saveContentEvidenceInFile(
					evidence.getContent(), 
					evidenceStorer.getPathFile(step));
			}
		}
		storedEvidences.clear();
	}
	
	private StepEvidenceStorer evidenceStorerFactory(StepEvidence evidence) {
		switch (evidence) {
		case HAR:
			return new NettrafficStorer();
		case IMAGEN:
			return new HardcopyStorer();
		case ERROR_PAGE:
			TestRunTM testRun = step.getTestRunParent();
			return testRun.getStorerErrorEvidence();
		case HTML:
			return new HtmlStorer();
		default:
			return null;
		}
	}

	private boolean isNecessariStorage(StepTM step ) {
		for (var stepEvidence : StepEvidence.values()) {
			if (step.isNecessaryStorage(stepEvidence)) {
				return true;
			}
		}
		return false;
	}

	private void createPathForEvidencesStore(StepTM step) {
		String pathEvidencias = step.getPathDirectory();
		File directorio = new File(pathEvidencias);
		if (!directorio.exists()) {
			directorio.mkdirs();
		}
	}
}
