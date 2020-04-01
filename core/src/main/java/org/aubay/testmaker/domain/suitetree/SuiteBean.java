package org.aubay.testmaker.domain.suitetree;

import java.util.Date;
import java.util.List;

import org.aubay.testmaker.conf.Channel;
import org.aubay.testmaker.conf.State;
import org.aubay.testmaker.domain.StateExecution;
import org.aubay.testmaker.service.webdriver.maker.FactoryWebdriverMaker.WebDriverType;

public class SuiteBean {

	private List<TestRunBean> listTestRun;
	private String idExecSuite;
	private State result;
	private StateExecution stateExecution;
	private String name;
	private String version;
	private Channel channel;
	private String app;
	private WebDriverType webDriverType;
	private Date inicioDate;
	private Date finDate;
	private float durationMillis;
	private int numberTestCases;
	private String moreInfo;
	private String urlBase;
	private String pathReportHtml;
	private String urlReportHtml;
	
	public List<TestRunBean> getListTestRun() {
		return listTestRun;
	}
	public void setListTestRun(List<TestRunBean> listTestRun) {
		this.listTestRun = listTestRun;
	}
	public String getIdExecSuite() {
		return idExecSuite;
	}
	public String getName() {
		return name;
	}
	public String getVersion() {
		return version;
	}
	public Channel getChannel() {
		return channel;
	}
	public String getApp() {
		return app;
	}
	public WebDriverType getWebDriverType() {
		return webDriverType;
	}
	public State getResult() {
		return result;
	}
	public Date getInicioDate() {
		return inicioDate;
	}
	public Date getFinDate() {
		return finDate;
	}
	public float getDurationMillis() {
		return durationMillis;
	}
	public int getNumberTestCases() {
		return numberTestCases;
	}
	public String getMoreInfo() {
		return moreInfo;
	}
	public String getUrlBase() {
		return urlBase;
	}
	public String getPathReportHtml() {
		return pathReportHtml;
	}
	public String getUrlReportHtml() {
		return urlReportHtml;
	}
	public StateExecution getStateExecution() {
		return stateExecution;
	}
	
	public void setIdExecSuite(String idExecSuite) {
		this.idExecSuite = idExecSuite;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	public void setApp(String app) {
		this.app = app;
	}
	public void setWebDriverType(WebDriverType webDriverType) {
		this.webDriverType = webDriverType;
	}
	public void setResult(State result) {
		this.result = result;
	}
	public void setInicioDate(Date inicioDate) {
		this.inicioDate = inicioDate;
	}
	public void setFinDate(Date finDate) {
		this.finDate = finDate;
	}
	public void setDurationMillis(float durationMillis) {
		this.durationMillis = durationMillis;
	}
	public void setNumberTestCases(int numTestCases) {
		this.numberTestCases = numTestCases;
	}
	public void setMoreInfo(String moreInfo) {
		this.moreInfo = moreInfo;
	}
	public void setUrlBase(String urlBase) {
		this.urlBase = urlBase;
	}
	public void setPathReportHtml(String pathReportHtml) {
		this.pathReportHtml = pathReportHtml;
	}
	public void setUrlReportHtml(String urlReportHtml) {
		this.urlReportHtml = urlReportHtml;
	}
	public void setStateExecution(StateExecution stateExecution) {
		this.stateExecution = stateExecution;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this) { 
			return true; 
		} 
		if (!(o instanceof SuiteBean)) { 
			return false; 
		} 
		
		SuiteBean c = (SuiteBean) o; 
		return (c.getIdExecSuite().compareTo(getIdExecSuite())==0); 
	}
}