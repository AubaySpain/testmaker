package com.github.jorge2m.testmaker.testreports.stepstore;

import com.github.jorge2m.testmaker.domain.suitetree.StepTM;

public class HtmlStorer extends StepEvidenceStorer {

	public HtmlStorer() {
		super(StepEvidence.HTML);
	}
	
	@Override
	protected String captureContent(StepTM step) {
		String html = "";
		try {
			html = step.getTestCaseParent().getDriver().getPageSource();
		}
		catch (Exception e) {
			step.getSuiteParent().getLogger().warn("Problem capturing html page", e);
		}
		return html;
	}
}
