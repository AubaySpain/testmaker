package com.github.jorge2m.testmaker.domain.suitetree;

import java.util.Date;
import java.util.List;

import com.github.jorge2m.testmaker.conf.State;

public class TestCaseBean {

	private List<StepTM> listStep;
	private String idExecSuite;
	private String suiteName;
	private String testRunName;
	private String code;
	private String name;
	private String specificInputData;
	private String nameUnique;
	private String description;
	private int indexInTestRun;
	private State result;
	private int statusTng;
	private Date inicioDate;
	private Date finDate; 
	private float durationMillis;
	private int numberSteps;
	private String testPathDirectory;
	private String classSignature;
	private String throwable;
	private String logs;
	private String video;

	public List<StepTM> getListStep() {
		return listStep;
	}
	public void setListStep(List<StepTM> listStep) {
		this.listStep = listStep;
	}
	public String getIdExecSuite() {
		return idExecSuite;
	}
	public void setIdExecSuite(String idExecSuite) {
		this.idExecSuite = idExecSuite;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSuiteName() {
		return suiteName;
	}
	public void setSuiteName(String suiteName) {
		this.suiteName = suiteName;
	}
	public String getTestRunName() {
		return testRunName;
	}
	public void setTestRunName(String testRunName) {
		this.testRunName = testRunName;
	}
	public String getName() {
		return name;
	}
	public String getSpecificInputData() {
		return specificInputData;
	}
	public void setSpecificInputData(String specificInputData) {
		this.specificInputData = specificInputData;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNameUnique() {
		return nameUnique;
	}
	public String getNameUniqueNormalized() {
		return getNormalized(getNameUnique());
	}
	public void setNameUnique(String nameUnique) {
		this.nameUnique = nameUnique;
	}
	public int getIndexInTestRun() {
		return indexInTestRun;
	}
	public void setIndexInTestRun(int indexInTestRun) {
		this.indexInTestRun = indexInTestRun;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public State getResult() {
		return result;
	}
	public void setResult(State result) {
		this.result = result;
	}
	public int getStatusTng() {
		return statusTng;
	}
	public void setStatusTng(int statusTng) {
		this.statusTng = statusTng;
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
	public String getTestPathDirectory() {
		return this.testPathDirectory;
	}
	public void setTestPathDirectory(String testPathDirectory) {
		this.testPathDirectory = testPathDirectory;
	}
	
	public int getNumberSteps() {
		return numberSteps;
	}
	public void setNumberSteps(int numberSteps) {
		this.numberSteps = numberSteps;
	}
	public String getClassSignature() {
		return classSignature;
	}
	public void setClassSignature(String classSignature) {
		this.classSignature = classSignature;
	}
	public String getThrowable() {
		return throwable;
	}
	public void setThrowable(String throwable) {
		this.throwable = throwable;
	}
	public String getLogs() {
		return logs;
	}
	public void setLogs(String logs) {
		this.logs = logs;
	}	
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}	
	
	public static String getNormalized(String text) {
	    StringBuilder result = new StringBuilder();
	    String allowedChars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz ()[]-_";
	    
	    for (int i = 0; i < text.length(); i++) {
	        char currentChar = text.charAt(i);

	        // Permitir números, letras, espacio, y los caracteres específicos
	        if ((currentChar >= 48 && currentChar <= 57) ||  // Números
	            (currentChar >= 65 && currentChar <= 90) ||  // Letras mayúsculas
	            (currentChar >= 97 && currentChar <= 122) || // Letras minúsculas
	            currentChar == 32 ||                         // Espacio
	            currentChar == 40 || currentChar == 41 ||    // ( )
	            currentChar == 91 || currentChar == 93 ||    // [ ]
	            currentChar == 45 ||                         // -
	            currentChar == 95) {                         // _
	            result.append(currentChar);
	        } else {
	            int index = currentChar % allowedChars.length();
	            result.append(allowedChars.charAt(index));
	        }
	    }
	    
	    return result.toString().replace(" ", "");
	}

}
