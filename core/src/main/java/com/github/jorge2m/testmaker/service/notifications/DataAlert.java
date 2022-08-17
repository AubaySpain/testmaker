package com.github.jorge2m.testmaker.service.notifications;

import static com.github.jorge2m.testmaker.repository.jdbc.dao.Utils.DateFormat.ToMillis;

import java.text.ParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteBean;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.github.jorge2m.testmaker.repository.jdbc.dao.Utils;


public class DataAlert {
	
	private final String idExecSuite;
	private final String suiteName;
	private final String testCaseName;
	private final int stepNumber;
	private final int validationNumber;
	private final Boolean resultado;
	private final State levelCheck;
	private final String stepDescription;
	private final String checkDescription;
	private final String infoExecution;
	private final String methodValidation;
	private final Date fecha;
	private final String urlReportSuite;
	
	public DataAlert(String idExecSuite, String suiteName, String testCaseName, int stepNumber, int validationNumber,
			Boolean resultado, State levelCheck, String stepDescription, String checkDescription, String infoExecution,
			String methodValidation, Date fecha, String urlReportSuite) {
		super();
		this.idExecSuite = idExecSuite;
		this.suiteName = suiteName;
		this.testCaseName = testCaseName;
		this.stepNumber = stepNumber;
		this.validationNumber = validationNumber;
		this.resultado = resultado;
		this.levelCheck = levelCheck;
		this.stepDescription = stepDescription;
		this.checkDescription = checkDescription;
		this.infoExecution = infoExecution;
		this.methodValidation = methodValidation;
		this.fecha = fecha;
		this.urlReportSuite = urlReportSuite;
	}

	private DataAlert(Check check, ChecksTM parentChecks) {
		super();
		SuiteBean suite = parentChecks.getSuiteParent().getSuiteBean(); 
		TestCaseTM testCase = parentChecks.getTestCaseParent();
		StepTM step = parentChecks.getStepParent();
		
		this.idExecSuite = suite.getIdExecSuite();
    	this.suiteName = suite.getName();
    	this.testCaseName = testCase.getNameUnique();
    	this.stepNumber = step.getNumber();
    	this.validationNumber = getPositionValidationInStep(parentChecks);
    	this.resultado = check.isOvercomed();
    	this.levelCheck = check.getLevelResult();
    	this.stepDescription = step.getDescripcion();
    	this.checkDescription = check.getDescription();
    	this.infoExecution = check.getInfo();
    	this.methodValidation = parentChecks.getPathMethod();
    	this.fecha = new Date();
    	this.urlReportSuite = suite.getUrlReportHtml();
	}
	
	public static DataAlert of(Check check, ChecksTM checksParent) {
		return new DataAlert(check, checksParent);
	}
	
	public String getIdExecSuite() {
		return idExecSuite;
	}

	public String getSuiteName() {
		return suiteName;
	}

	public String getTestCaseName() {
		return testCaseName;
	}

	public int getStepNumber() {
		return stepNumber;
	}

	public int getValidationNumber() {
		return validationNumber;
	}

	public Boolean isResultado() {
		return resultado;
	}

	public State getLevelCheck() {
		return levelCheck;
	}

	public String getStepDescription() {
		return stepDescription;
	}

	public String getCheckDescription() {
		return checkDescription;
	}

	public String getInfoExecution() {
		return infoExecution;
	}

	public String getMethodValidation() {
		return methodValidation;
	}

	public Date getFecha() {
		return fecha;
	}
	public String getFechaFormatted() {
		return Utils.getDateFormat(ToMillis).format(getFecha());
	}
	public static Date getFechaParsed(String fecha) throws ParseException {
		return Utils.getDateFormat(ToMillis).parse(fecha);
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
	
	public int getPositionValidationInStep(ChecksTM parentChecks) {
		int position = parentChecks.getPositionInStep();
		if (position < 1) {
			position = parentChecks.getStepParent().getListChecksTM().size() + 1;
		}
		return position;
	}	
}
