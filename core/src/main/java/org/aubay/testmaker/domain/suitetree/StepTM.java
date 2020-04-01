package org.aubay.testmaker.domain.suitetree;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.aubay.testmaker.boundary.aspects.step.SaveWhen;
import org.aubay.testmaker.conf.State;
import org.aubay.testmaker.domain.StateExecution;
import org.aubay.testmaker.domain.util.ParsePathClass;
import org.aubay.testmaker.service.TestMaker;
import org.aubay.testmaker.testreports.stepstore.EvidencesWarehouse;
import org.aubay.testmaker.testreports.stepstore.NettrafficStorer;
import org.aubay.testmaker.testreports.stepstore.StepEvidence;
import org.aubay.testmaker.testreports.stepstore.Storage;
import org.openqa.selenium.WebDriver;

public class StepTM {

	private TestCaseTM testCase;
	private TestRunTM testRun;
	private SuiteTM suite;
	
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
		testCase = TestMaker.getTestCase();
		evidencesWarehouse = new EvidencesWarehouse(this);
		if (testCase!=null) {
			testRun = testCase.getTestRunParent();
			suite = testRun.getSuiteParent();
		} else {
			testRun = null;
			suite = null;
		}
	}
	
	public TestCaseTM getTestCaseParent() {
		return testCase;
	}
	public void setParents(TestCaseTM testCase) {
		this.testCase = testCase;
		this.testRun = testCase.getTestRunParent();
		this.suite = testRun.getSuiteParent();
	}
	public TestRunTM getTestRunParent() {
		return testRun;
	}
	public SuiteTM getSuiteParent() {
		return suite;
	}
	public WebDriver getDriver() {
		return testCase.getDriver();
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
		return testCase.getTestPathDirectory();
	}
	
	public void captureAndStoreEvidences() {
		if (suite.getInputParams().isTestExecutingInRemote()) {
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
		if (suite.getInputParams().isNetAnalysis()) {
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
		case html:
			return saveHtmlPage;
		case errorpage:
			return saveErrorPage;
		case har:
		case harp:
			return saveNettraffic;
		case imagen:
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
