package org.jorge2m.testmaker.domain.suitetree;

import java.util.Date;
import java.util.List;

import org.jorge2m.testmaker.conf.State;
import org.jorge2m.testmaker.service.webdriver.maker.FactoryWebdriverMaker.WebDriverType;

public class TestRunBean {

	private List<TestCaseBean> listTestCase;
	private String idExecSuite;
	private String suiteName;
	private String name;
	private State result;
	private String device;
	private Date inicioDate;
	private Date finDate;
	private float durationMillis;
	private int numberTestCases;
	private WebDriverType webDriverType;
	
	public String getIdExecSuite() {
		return idExecSuite;
	}
	public void setIdExecSuite(String idExecSuite) {
		this.idExecSuite = idExecSuite;
	}
	public String getSuiteName() {
		return suiteName;
	}
	public void setSuiteName(String suiteName) {
		this.suiteName = suiteName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public State getResult() {
		return result;
	}
	public void setResult(State result) {
		this.result = result;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public Date getInicioDate() {
		return inicioDate;
	}
	public void setInicioDate(Date inicioDate) {
		this.inicioDate = inicioDate;
	}
	public Date getFinDate() {
		return finDate;
	}
	public void setFinDate(Date finDate) {
		this.finDate = finDate;
	}
	public float getDurationMillis() {
		return durationMillis;
	}
	public void setDurationMillis(float durationMillis) {
		this.durationMillis = durationMillis;
	}
	public int getNumberTestCases() {
		return numberTestCases;
	}
	public void setNumberTestCases(int numberTestCases) {
		this.numberTestCases = numberTestCases;
	}
	public WebDriverType getWebDriverType() {
		return webDriverType;
	}
	public void setWebDriverType(WebDriverType webDriverType) {
		this.webDriverType = webDriverType;
	}
	public List<TestCaseBean> getListTestCase() {
		return listTestCase;
	}
	public void setListTestCase(List<TestCaseBean> listTestCase) {
		this.listTestCase = listTestCase;
	}
}
