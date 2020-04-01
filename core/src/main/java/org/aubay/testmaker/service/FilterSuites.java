package org.aubay.testmaker.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.aubay.testmaker.conf.Channel;
import org.aubay.testmaker.domain.RepositoryI;
import org.aubay.testmaker.domain.SuitesExecuted;
import org.aubay.testmaker.domain.suitetree.SuiteBean;
import org.aubay.testmaker.domain.suitetree.SuiteTM;

public class FilterSuites {

	public enum SetSuiteRun {running, finished, all}
	
	private final String suite;
	private final Channel channel; 
	private final String application; 
	private final SetSuiteRun state; 
	private final Date desde;
	
	private RepositoryI repository = TestMaker.getRepository();
	
	private FilterSuites (String suite, Channel channel, String application, SetSuiteRun state, Date fechaDesde) {
		this.suite = suite;
		this.application = application;
		this.channel = channel;
		this.state = state;
		this.desde = fechaDesde;
	}
	
	public static FilterSuites getNew() {
		return (getNew(null, null, null, null, null));
	}
	
	public static FilterSuites getNew(String suite, Channel channel, String application, SetSuiteRun state, Date fechaDesde) {
		return new FilterSuites(suite, channel, application, state, fechaDesde);
	}
	
	
	public List<SuiteBean> getListSuites() throws Exception {
		List<SuiteBean> listSuitesInMemory;
		List<SuiteBean> listSuitesStored;
		if (desde==null) {
			listSuitesInMemory = filter(getListSuitesInMemory());
			listSuitesStored = filter(repository.getListSuites());
		} else {
			listSuitesInMemory = filter(getListSuitesInMemoryAfter(desde));
			listSuitesStored = filter(repository.getListSuitesAfter(desde));
		}

		List<SuiteBean> listToReturn = new ArrayList<>();
		listToReturn.addAll(listSuitesInMemory);
		for (SuiteBean suiteStored : listSuitesStored) {
			if (!listSuitesInMemory.contains(suiteStored)) {
				listToReturn.add(suiteStored);
			}
		}
		return listToReturn;
	}
	
	private List<SuiteBean> filter(List<SuiteBean> listToFilter) {
		List<SuiteBean> listFiltered = new ArrayList<>();
		for (SuiteBean suiteData : listToFilter) {
			if (filterMatches(suiteData)) {
				listFiltered.add(suiteData);
			}
		}
		return listFiltered;
	}
	
	private boolean filterMatches(SuiteBean suiteData) {
		if (suite!=null) {
			if (suite.compareTo(suiteData.getName())!=0) {
				return false;
			}
		}
		if (channel!=null) {
			if (channel!=suiteData.getChannel()) {
				return false;
			}
		}
		if (application!=null) {
			if (application.compareTo(suiteData.getApp())!=0) {
				return false;
			}
		}
		if (state!=null && state!=SetSuiteRun.all) {
			if (state==SetSuiteRun.running && suiteData.getStateExecution().isFinished()) {
				return false;
			}
			if (state==SetSuiteRun.finished && !suiteData.getStateExecution().isFinished()) {
				return false;
			}
		}
		return true;
	}
	
	void setRepository(RepositoryI repository) {
		this.repository = repository;
	}
	
	List<SuiteBean> getListSuitesInMemory() {
		List<SuiteBean> listSuitesToReturn = new ArrayList<>();
		for (SuiteTM suite : SuitesExecuted.getSuitesExecuted()) {
			listSuitesToReturn.add(suite.getSuiteBean());
		}
		listSuitesToReturn.sort(Comparator.comparing(SuiteBean::getIdExecSuite).reversed());
		return listSuitesToReturn;
	}
	
	List<SuiteBean> getListSuitesInMemoryAfter(Date fechaDesde) {
		List<SuiteBean> listSuitesReturn = new ArrayList<>();
		List<SuiteBean> listSuites = getListSuitesInMemory();
		for (SuiteBean suiteData : listSuites) {
			if (suiteData.getInicioDate().after(fechaDesde)) {
				listSuitesReturn.add(suiteData);
			}
		}
		return listSuitesReturn;
	}
	
}
