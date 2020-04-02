package org.jorge2m.testmaker.testreports.stepstore;

import org.jorge2m.testmaker.domain.suitetree.StepTM;

public class HtmlStorer extends EvidenceStorer {

	public HtmlStorer() {
		super(StepEvidence.html);
	}
	
	@Override
	protected String captureContent(StepTM step) {
		return step.getDriver().getPageSource();
	}
}
