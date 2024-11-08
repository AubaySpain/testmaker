package com.github.jorge2m.testmaker.domain.suitetree;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.jorge2m.testmaker.conf.StoreType;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.util.ParsePathClass;

public class ChecksTM {
	
	private List<Check> listChecks = new ArrayList<>();
	private State stateValidation = State.OK;
	private String title="";
	private boolean avoidEvidences;
	private String pathMethod;
	private Integer positionInStep = null;
	private String nameClass = null;
	private String nameMethod = null;

	@JsonIgnore
	private SuiteTM suiteParent;
	@JsonIgnore
	private TestRunTM testRunParent;
	@JsonIgnore
	private TestCaseTM testCaseParent;
	@JsonIgnore
	private StepTM stepParent; 

	public ChecksTM() {
		Optional<TestCaseTM> testCaseOp = TestCaseTM.getTestCaseInExecution();
		if (testCaseOp.isPresent()) {
			this.testCaseParent = testCaseOp.get();
			this.stepParent = testCaseParent.getLastStep();
			this.testRunParent = testCaseParent.getTestRunParent();
			this.suiteParent = testCaseParent.getSuiteParent();
		}
		else {
			this.testCaseParent = null;
			this.stepParent = null;
			this.testRunParent = null;
			this.suiteParent = null;			
		}
	}

	private ChecksTM(StepTM stepParent) {
		this.stepParent = stepParent;
		this.testCaseParent = stepParent.getTestCaseParent();
		this.testRunParent = stepParent.getTestRunParent();
		this.suiteParent = stepParent.getSuiteParent();
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
		this.stepParent = step;
		this.testCaseParent = stepParent.getTestCaseParent();
		this.testRunParent = testCaseParent.getTestRunParent();
		this.suiteParent = testRunParent.getSuiteParent();
	}
	
	public SuiteTM getSuiteParent() {
		return this.suiteParent;
	}
	public TestRunTM getTestRunParent() {
		return this.testRunParent;
	}
	public TestCaseTM getTestCaseParent() {
		return this.testCaseParent;
	}
	public StepTM getStepParent() {
		return this.stepParent;
	}
	
	public State getStateValidation() {
		return stateValidation;
	}
	public void setStateValidation(State stateValidation) {
		this.stateValidation = stateValidation;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean isTitle() {
		return (title!=null && "".compareTo(title)!=0);
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
		if (nameClass!=null) {
			return nameClass;
		}
		return ParsePathClass.getNameClass(getPathClass());
	}
	public void setNameClass(String nameClass) {
		this.nameClass = nameClass;
	}
	public String getNameMethod() {
		if (nameMethod!=null) {
			return nameMethod;
		}
		return ParsePathClass.getNameMethod(getPathMethod());
	}
	public void setNameMethod(String nameMethod) {
		this.nameMethod = nameMethod;
	}
	public int size() {
		return listChecks.size();
	}
	public Check get(int index) {
		return (listChecks.get(index));
	}
	public void add(Check check) {
		//check.setParentChecks(this);
		listChecks.add(check);
	}
	public void add(String description, boolean overcomed) {
		add(description, overcomed, State.DEFECT);
	}
	public void add(String description, boolean overcomed, State levelResult) {
		add(Check.make(description, overcomed, levelResult).build());
	}

	public boolean areAllChecksOvercomed() {
		for (Check check : listChecks) {
			if (!check.isOvercomed()) {
				return false;
			}
		}
		return true;
	}
	public boolean calculateAvoidEvidences() {
		for (Check check : listChecks) {
			if (!check.isOvercomed() &&
				check.getLevelResult()!=State.OK &&
				check.getStore()!=StoreType.NONE) {
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
		boolean manyChecks = listChecks.size()>1;
		if (isTitle()) {
			textValidations.add(getHtmlTitle());
		}
		for (int i=0; i<listChecks.size(); i++) {
			textValidations.add(getHtmlCheck(manyChecks, i));
		}

		return (textValidations.stream().collect(Collectors.joining("<br>")));
	}
	
	private String getHtmlTitle() {
		return "<validac style=\"color:purple\">" + getTitle() + "</validac><br>"; 
	}

	private String getHtmlCheck(boolean manyChecks, int i) {
		Check resultValidation = listChecks.get(i);
		String numCheck = "";
		if (manyChecks) {
			numCheck = String.valueOf(i+1) + ") ";
		}
		String htmlValidation = 
			"<validac style=\"color:" + resultValidation.getStateResult().getColorCss() + "\">" + 
			numCheck + resultValidation.getDescription();
		
		String info = resultValidation.getInfo();
		if (info!=null && info.compareTo("")!=0 ) {
			htmlValidation+=
			    "<div style=\"border:1px solid;border-radius:5px;margin:5px;\">" + 
			    resultValidation.getInfo() +
			    "</div>";
		}
		
		htmlValidation+="</validac>";
		return htmlValidation;
	}

	public int getPositionInStep() {
		if (positionInStep!=null) {
			return positionInStep;
		}
		
		List<ChecksTM> listChecksResultInStep = stepParent.getListChecksTM();
		for (int i=0; i<listChecksResultInStep.size(); i++) {
			ChecksTM checksResult = listChecksResultInStep.get(i);
			if (checksResult==this) {
				return i+1;
			}
		}
		return -1;
	}
	
	public void setPositionInStep(int positionInStep) {
		this.positionInStep = positionInStep;
	}
	
	public State calculateStateValidation() {
		State stateToReturn;
		if (isStepFinishedWithException()) {
			return State.KO;
		}
		
		stateToReturn = State.OK;
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
		if (stateValidation.isMoreCriticThan(stepParent.getResultSteps()) || !stepParent.isStateUpdated()) {
			stepParent.setResultSteps(stateValidation);
		}
	}
	private boolean isStepFinishedWithException() {
		return (stepParent.getHoraFin()!=null && stepParent.isExcepExists());
	}

	/**
	 * @return la lista ordenada de resultado de las validaciones que se pueda almacenar en BD
	 */
	public List<State> getListStateValidations() {
		List<State> listCodes = new ArrayList<>();
		int lastValidation = getIndexLastValidation();
		for (int i=0; i<lastValidation; i++) {
			listCodes.add(State.OK);
		}
		for (int i=0; i<listChecks.size(); i++) {
			Check resultValidation = listChecks.get(i);
			if (!resultValidation.isOvercomed()) {
				listCodes.set(i, resultValidation.getLevelResult());
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
		return listChecks.size();
	}
}
