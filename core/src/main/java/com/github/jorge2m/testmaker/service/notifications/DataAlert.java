package com.github.jorge2m.testmaker.service.notifications;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;


public class DataAlert {
	
	private final String suiteName;
	private final String urlReportSuite;
	private final String testCaseName;
	private final String stepDescription;
	private final Check check;
	
	private DataAlert(Check check) {
		ChecksTM parentChecks = check.getParentChecks();
		SuiteTM suite = parentChecks.getSuiteParent();
		TestCaseTM testCase = parentChecks.getTestCaseParent();
		StepTM step = parentChecks.getStepParent();
		
		this.check = check;
    	this.suiteName = suite.getName();
    	this.urlReportSuite = suite.getDnsReportHtml();
    	this.testCaseName = testCase.getName();
    	this.stepDescription = step.getDescripcion();
	}
	
	public static DataAlert of(Check check) {
		return new DataAlert(check);
	}
	
	public String getUrlReportSuite() {
		if (isUrlPattern(urlReportSuite)) {
			return urlReportSuite;
		}
		return "";
	}
	
	static boolean isUrlPattern(String urlReportSuite) {
		String regex = "^((((https?|ftps?|gopher|telnet|nntp)://)|(mailto:|news:))" +
                "(%{2}|[-()_.!~*';/?:@&=+$, A-Za-z0-9])+)" + "([).!';/?:, ][[:blank:]])?$";
		Pattern pattern = Pattern.compile(regex); 
		Matcher matcher = pattern.matcher(urlReportSuite);
		return matcher.matches();
	}

	public String getSuiteName() {
		return suiteName;
	}

	public String getTestCaseName() {
		return testCaseName;
	}

	public String getStepDescription() {
		return stepDescription;
	}

	public Check getCheck() {
		return check;
	}
}
