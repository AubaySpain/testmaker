package com.github.jorge2m.testmaker.testreports.stepstore;

import com.github.jorge2m.testmaker.domain.suitetree.StepTM;

public class HtmlStorer extends EvidenceStorer {

	public HtmlStorer() {
		super(StepEvidence.Html);
	}
	
	@Override
	protected String captureContent(StepTM step) {
		return step.getDriver().getPageSource();
	}
}
