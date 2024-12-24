package com.github.jorge2m.testmaker.domain;

import java.util.Date;

public class ComparedSuite {

	private String idExecSuite1;
	private String idExecSuite2;
	private String pathReport;
	private String urlReport;
	private Date date;
	
	public String getIdExecSuite1() {
		return idExecSuite1;
	}
	public void setIdExecSuite1(String idExecSuite1) {
		this.idExecSuite1 = idExecSuite1;
	}
	public String getIdExecSuite2() {
		return idExecSuite2;
	}
	public void setIdExecSuite2(String idExecSuite2) {
		this.idExecSuite2 = idExecSuite2;
	}
	public String getPathReport() {
		return pathReport;
	}
	public void setPathReport(String pathReport) {
		this.pathReport = pathReport;
	}
	public String getUrlReport() {
		return urlReport;
	}
	public void setUrlReport(String urlReport) {
		this.urlReport = urlReport;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

}
