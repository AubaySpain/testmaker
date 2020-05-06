package com.github.jorge2m.testmaker.domain.suitetree;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.SuitesExecuted;
import com.github.jorge2m.testmaker.domain.util.ParsePathClass;

public class ChecksTM {
	
	private List<Check> listChecks = new ArrayList<>();
	private State stateValidation = State.Ok;
	private boolean avoidEvidences;
	private String pathMethod;

//	private SuiteTM suiteParent;
//	private TestRunTM testRunParent;
//	private TestCaseTM testCaseParent;
	//private StepTM stepParent;
	private String idExecSuite;
	private String suiteName;
	private String testRunName;
	private String testCaseName;
	private String testCaseNameUnique;
	private int stepNumber;

	public ChecksTM() {
		TestCaseTM testCaseParent = TestCaseTM.getTestCaseInExecution();
		if (testCaseParent!=null) {
			//this.stepParent = testCaseParent.getLastStep();
			this.idExecSuite = testCaseParent.getSuiteParent().getIdExecution();
			this.suiteName = testCaseParent.getSuiteParent().getName();
			this.testRunName = testCaseParent.getTestRunParent().getName();
			this.testCaseName = testCaseParent.getName();
			this.testCaseNameUnique = testCaseParent.getNameUnique();
			this.stepNumber = testCaseParent.getLastStep().getNumber();
			//this.testRunParent = testCaseParent.getTestRunParent();
			//this.suiteParent = testCaseParent.getSuiteParent();
		}
		else {
			//this.stepParent = null;		
		}
	}

	private ChecksTM(StepTM stepParent) {
		setParents(stepParent);
	}
	
	public static ChecksTM getNew(StepTM stepParent) {
		return (new ChecksTM(stepParent));
	}
	
	public static ChecksTM getNew() {
		return (new ChecksTM());
	}
	
	public static ChecksTM of(Check resultValidation, StepTM step) {
		ChecksTM listChecks = new ChecksTM(step);
		listChecks.add(resultValidation);
		return listChecks;
	}
	
	public void setParents(StepTM step) {
		//this.stepParent = step;
		TestCaseTM testCaseParent = step.getTestCaseParent();
		this.idExecSuite = testCaseParent.getSuiteParent().getIdExecution();
		this.suiteName = testCaseParent.getSuiteParent().getName();
		this.testRunName = testCaseParent.getTestRunParent().getName();
		this.testCaseNameUnique = testCaseParent.getNameUnique();
		this.testCaseName = testCaseParent.getName();
		this.stepNumber = step.getNumber();
	}
	
//	public SuiteTM getSuiteParent() {
//		return this.suiteParent;
//	}
//	public TestRunTM getTestRunParent() {
//		return this.testRunParent;
//	}
//	public TestCaseTM getTestCaseParent() {
//		return this.testCaseParent;
//	}
	public StepTM getStepParent() {
		for (SuiteTM suite : SuitesExecuted.getSuitesExecuted()) {
			if (this.idExecSuite.compareTo(suite.getIdExecution())==0) {
				for (TestRunTM testRun : suite.getListTestRuns()) {
					if (this.testRunName.compareTo(testRun.getName())==0) {
						for (TestCaseTM testCase : testRun.getListTestCases()) {
							if (this.testCaseNameUnique.compareTo(testCase.getNameUnique())==0) {
								for (StepTM step : testCase.getListStep()) {
									if (this.stepNumber == step.getNumber()) {
										return step;
									}
								}
							}
						}
					}
				}
			}
		}
		return null;
	}
	public String getIdExecSuite() {
		return idExecSuite;
	}
	public String getSuiteName() {
		return suiteName;
	}
	public String getTestRunName() {
		return testRunName;
	}
	public String getTestCaseNameUnique() {
		return testCaseNameUnique;
	}
	public String getTestCaseName() {
		return testCaseName;
	}
	public int getStepNumber() {
		return this.stepNumber;
	}
	
	public State getStateValidation() {
		return stateValidation;
	}
	public void setStateValidation(State stateValidation) {
		this.stateValidation = stateValidation;
	}
	public boolean isAvoidEvidences() {
		return avoidEvidences;
	}
	public void setAvoidEvidences(boolean avoidEvidences) {
		this.avoidEvidences = avoidEvidences;
	}
	public String getPathMethod() {
		return pathMethod;
	}
	public void setPathMethod(String pathMethod) {
		this.pathMethod = pathMethod;
	}
	public List<Check> getListChecks() {
		return listChecks;
	}
	public void setListChecks(List<Check> listChecks) {
		this.listChecks = listChecks;
	}
	
	public String getPathClass() {
		return ParsePathClass.getPathClass(getPathMethod());
	}
	public String getNameClass() {
		return ParsePathClass.getNameClass(getPathClass());
	}
	public String getNameMethod() {
		return ParsePathClass.getNameMethod(getPathMethod());
	}
	public int size() {
		return listChecks.size();
	}
	public Check get(int index) {
		return (listChecks.get(index));
	}
	public void add(Check check) {
		listChecks.add(check);
	}
	public void add(String description, boolean overcomed, State levelResult) {
		add(description, overcomed, levelResult, false);
	}
	public void add(String description, boolean overcomed, State levelResult, boolean avoidEvidences) {
		int id = listChecks.size() + 1;
		Check resultValidation = Check.of(id, id + ") " + description, overcomed, levelResult, avoidEvidences);
		add(resultValidation);
	}
	public void add(int id, State levelResult) {
		Check resultValidation = Check.of(id, levelResult);
		add(resultValidation);
	}
	public boolean calculateAvoidEvidences() {
		for (Check check : listChecks) {
			if (!check.isOvercomed() &&
				check.getLevelResult()!=State.Ok &&
				!check.isAvoidEvidences()) {
				return false;
			}
		}
		
		return true;
	}
	
	public List<String> getTextValidations() {
		List<String> textValidations = new ArrayList<>();
		for (Check resultValidation : listChecks) {
			textValidations.add(resultValidation.getDescription());
		}
		return textValidations;
	}
	
	public String getTextValidationsBrSeparated() {
		List<String> textValidations = getTextValidations();
		return (textValidations.stream().collect(Collectors.joining("<br>")));
	}
	public String getHtmlValidationsBrSeparated() {
		List<String> textValidations = new ArrayList<>();
		for (Check resultValidation : listChecks) {
			String htmlValidation = 
				"<validac style=\"color:" + resultValidation.getStateResult().getColorCss() + "\">" + 
				resultValidation.getDescription() + 
				"</validac>";
			textValidations.add(htmlValidation);
		}

		return (textValidations.stream().collect(Collectors.joining("<br>")));
	}

	public int getPositionInStep() {
		List<ChecksTM> listChecksResultInStep = getStepParent().getListChecksTM();
		for (int i=0; i<listChecksResultInStep.size(); i++) {
			ChecksTM checksResult = listChecksResultInStep.get(i);
			if (checksResult==this) {
				return i+1;
			}
		}
		return -1;
	}
	
	public State calculateStateValidation() {
		State stateToReturn;
		if (isStepFinishedWithException()) {
			return State.Nok;
		}
		
		stateToReturn = State.Ok;
		for (Check check : listChecks) {
			if (!check.isOvercomed()) {
				//sound();
				State criticityValidation = check.getLevelResult();
				if (criticityValidation.isMoreCriticThan(stateToReturn)) {
					stateToReturn = criticityValidation;
				}
			}
		}
		
		return stateToReturn;
	}

	public void checkValidations() {
		checkAndSetStateValidation();
		setDatosStepAfterCheckValidation();
	}

	private void checkAndSetStateValidation() {
		setStateValidation(calculateStateValidation());
		setAvoidEvidences(calculateAvoidEvidences());
	}

	private void setDatosStepAfterCheckValidation() {
		State stateValidation = getStateValidation();
		if (stateValidation.isMoreCriticThan(getStepParent().getResultSteps()) || !getStepParent().isStateUpdated()) {
			getStepParent().setResultSteps(stateValidation);
		}
	}
	private boolean isStepFinishedWithException() {
		return (getStepParent().getHoraFin()!=null && getStepParent().isExcepExists());
	}

	/**
	 * @return la lista ordenada de resultado de las validaciones que se pueda almacenar en BD
	 */
	public List<State> getListStateValidations() {
		List<State> listCodes = new ArrayList<>();
		int lastValidation = getIndexLastValidation();
		for (int i=0; i<lastValidation; i++) {
			listCodes.add(State.Ok);
		}
		for (Check resultValidation : listChecks) {
			if (!resultValidation.isOvercomed()) {
				listCodes.set(resultValidation.getId()-1, resultValidation.getLevelResult());
			}
		}
		
		return listCodes;
	}
	
	public List<Boolean> getListOvercomedValidations() {
		List<Boolean> listOvercomed = new ArrayList<>();
		for (Check resultValidation : listChecks) {
			listOvercomed.add(resultValidation.isOvercomed());
		}
		return listOvercomed;
	}

	private int getIndexLastValidation() {
		int maxIndexValidation = 0;
		for (Check resultValidation : listChecks) {
			if (resultValidation.getId() > maxIndexValidation) {
				maxIndexValidation = resultValidation.getId();
			}
		}
		
		return maxIndexValidation;
	}
}
