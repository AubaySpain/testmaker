package com.github.jorge2m.testmaker.domain.suitetree;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.StateExecution;
import com.github.jorge2m.testmaker.domain.SuitesExecuted;
import com.github.jorge2m.testmaker.domain.util.ParsePathClass;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.testreports.stepstore.EvidencesWarehouse;
import com.github.jorge2m.testmaker.testreports.stepstore.NettrafficStorer;
import com.github.jorge2m.testmaker.testreports.stepstore.StepEvidence;
import com.github.jorge2m.testmaker.testreports.stepstore.Storage;

public class StepTM {

//	private TestCaseTM testCase;
//	private TestRunTM testRun;
//	private SuiteTM suite;
	private String idExecSuite;
	private String testRunName;
	private String testCaseNameUnique;
	
	private List<ChecksTM> listChecksTM = new ArrayList<>(); 
	private String descripcion; 
	private String res_expected; 
	private SaveWhen saveImagePage = SaveWhen.IfProblem;
	private SaveWhen saveErrorPage = SaveWhen.IfProblem;
	private SaveWhen saveHtmlPage = SaveWhen.Never;
	private SaveWhen saveNettraffic= SaveWhen.Never;
	private EvidencesWarehouse evidencesWarehouse;

	private String pathMethod;
	private int type_page; 
	private long timeInicio = 0;
	private long timeFin = 0;
	private State result_steps = State.Ok;
	private boolean excepExists = true;
	private StateExecution state = StateExecution.Started;
	private boolean isStateUpdated = false;
	
	public StepTM() {
		setParents(TestMaker.getTestCase());
		evidencesWarehouse = new EvidencesWarehouse(this);
	}
	
	public void setParents(TestCaseTM testCaseParent) {
		this.idExecSuite = testCaseParent.getSuiteParent().getIdExecution();
		//this.suiteName = testCaseParent.getSuiteParent().getName();
		this.testRunName = testCaseParent.getTestRunParent().getName();
		//this.testCaseName = testCaseParent.getName();
		this.testCaseNameUnique = testCaseParent.getNameUnique();
	}
	
	public TestCaseTM getTestCaseParent() {
		for (SuiteTM suite : SuitesExecuted.getSuitesExecuted()) {
			if (this.idExecSuite.compareTo(suite.getIdExecution())==0) {
				for (TestRunTM testRun : suite.getListTestRuns()) {
					if (this.testRunName.compareTo(testRun.getName())==0) {
						for (TestCaseTM testCase : testRun.getListTestCases()) {
							if (this.testCaseNameUnique.compareTo(testCase.getNameUnique())==0) {
								return testCase;
							}
						}
					}
				}
			}
		}
		return null;
	}
	
	public TestRunTM getTestRunParent() {
		return getTestCaseParent().getTestRunParent();
	}
	public SuiteTM getSuiteParent() {
		return getTestRunParent().getSuiteParent();
	}
	public WebDriver getDriver() {
		return getTestCaseParent().getDriver();
	}
	public String getOutputDirectorySuite() {
		return getTestRunParent().getTestNgContext().getOutputDirectory();
	}
	
	public int getNumber() {
		List<StepTM> listSteps = getTestCaseParent().getListStep();
		for (int i=0; i<listSteps.size(); i++) {
			if (listSteps.get(i)==this) {
				return i+1;
			}
		}
		return -1;
	}
	
	public void end(boolean exceptionReceived) {
		setExcepExists(exceptionReceived); 
		if (exceptionReceived) {
			setResultSteps(State.Nok);
		}
		captureAndStoreEvidences();
		setTimeFin(System.currentTimeMillis());
		setState(StateExecution.Finished);
	}
	
	public String getPathDirectory() {
		return getTestCaseParent().getTestPathDirectory();
	}
	
	public void captureAndStoreEvidences() {
		if (getSuiteParent().getInputParams().isTestExecutingInRemote()) {
			evidencesWarehouse.captureAndStore(Storage.Memory);
		} else {
			evidencesWarehouse.captureAndStore(Storage.File);
		}
	}
	public void moveContentEvidencesToFile() {
		evidencesWarehouse.moveContentEvidencesToFile();
	}

	public List<ChecksTM> getListChecksTM() {
		return listChecksTM;
	}
	public void setListChecksTM(List<ChecksTM> listChecksTM) {
		this.listChecksTM = listChecksTM;
	}
	public int getNumChecksTM() {
		return getListChecksTM().size();
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getResExpected() {
		return res_expected;
	}
	public void setResExpected(String res_expected) {
		this.res_expected = res_expected;
	}
	public SaveWhen getSaveImagePage() {
		return saveImagePage;
	}
	public void setSaveImagePage(SaveWhen saveImagePage) {
		this.saveImagePage = saveImagePage;
	}
	public SaveWhen getSaveErrorPage() {
		return saveErrorPage;
	}
	public void setSaveErrorPage(SaveWhen saveErrorPage) {
		this.saveErrorPage = saveErrorPage;
	}
	public SaveWhen getSaveHtmlPage() {
		return saveHtmlPage;
	}
	public void setSaveHtmlPage(SaveWhen saveHtmlPage) {
		this.saveHtmlPage = saveHtmlPage;
	}
	public SaveWhen getSaveNettraffic() {
		return saveNettraffic;
	}
	public void setSaveNettrafic(SaveWhen saveNettraffic) {
		if (getSuiteParent().getInputParams().isNetAnalysis()) {
			this.saveNettraffic = saveNettraffic;
			NettrafficStorer netTraffic = new NettrafficStorer();
			netTraffic.resetAndStartNetTraffic();
		}
	}

	public EvidencesWarehouse getEvidencesWarehouse() {
		return evidencesWarehouse;
	}
	public void setEvidencesWarehouse(EvidencesWarehouse evidencesWarehouse) {
		this.evidencesWarehouse = evidencesWarehouse;
	}

	public String getPathMethod() {
		return pathMethod;
	}
	public void setPathMethod(String pathMethod) {
		this.pathMethod = pathMethod;
	}
	public int getTypePage() {
		return type_page;
	}
	public void setTypePage(int type_page) {
		this.type_page = type_page;
	}
	public Date getHoraInicio() {
		return new Date(getTimeInicio());
	}
	public Date getHoraFin() {
		return new Date(getTimeFin());
	}
	public long getTimeInicio() {
		return timeInicio;
	}
	public void setTimeInicio(long timeInicio) {
		this.timeInicio = timeInicio;
	}
	public long getTimeFin() {
		return timeFin;
	}
	public void setTimeFin(long timeFin) {
		this.timeFin = timeFin;
	}

	public State getResultSteps() {
		return result_steps;
	}
	public void setResultSteps(State c_result_steps) {
		this.result_steps = c_result_steps; 
		this.isStateUpdated = true;
	}
	public boolean isExcepExists() {
		return excepExists;
	}
	public void setExcepExists(boolean excepExists) {
		this.excepExists = excepExists;
	}
	public StateExecution getState() {
		return state;
	}
	public void setState(StateExecution state) {
		this.state = state;
	}
	public boolean isStateUpdated() {
		return isStateUpdated;
	}
	public void setStateUpdated(boolean isStateUpdated) {
		this.isStateUpdated = isStateUpdated;
	}
	
	public void replaceInDescription(String oldChar, String newChar) {
		this.descripcion = this.descripcion.replace(oldChar, newChar);
	}
	public void addDescriptionText(String text) {
		this.descripcion+=text;
	}
	public void addRightDescriptionText(String text) {
		this.descripcion = text + this.descripcion;
	}
	public void addExpectedText(String text) {
		this.res_expected+=text;
	}
	public void replaceInExpected(String oldChar, String newChar) {
		this.res_expected = this.res_expected.replace(oldChar, newChar);
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
	public void setNOKstateByDefault() {
		setExcepExists(true); 
	}
	public void addChecksTM(ChecksTM checksTM) {
		setExcepExists(false);
		this.listChecksTM.add(checksTM);
	}
	public SaveWhen getWhenSave(StepEvidence evidencia) {
		switch (evidencia) {
		case Html:
			return saveHtmlPage;
		case ErrorPage:
			return saveErrorPage;
		case Har:
		case Harp:
			return saveNettraffic;
		case Imagen:
		default:
			return saveImagePage;
		}
	}
	public boolean isNecessaryStorage(StepEvidence evidencia) {
		SaveWhen saveEvidenceWhen = getWhenSave(evidencia);
		switch (saveEvidenceWhen) {
		case Always:
			return true;
		case Never:
			return false;
		case IfProblem:
			if (getResultSteps()!=State.Ok &&
				!isAllValidationsWithAvoidEvidences()) {
				return true;
			}
		}
		return false;
	}

	public boolean isAllValidationsWithAvoidEvidences() {
		if (listChecksTM==null || listChecksTM.isEmpty()) {
			return false;
		}
		for (ChecksTM check : listChecksTM) {
			if (!check.isAvoidEvidences()) {
				return false;
			}
		}
		return true;
	}
}
