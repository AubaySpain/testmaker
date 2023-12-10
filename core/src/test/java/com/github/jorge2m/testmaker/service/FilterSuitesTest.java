package com.github.jorge2m.testmaker.service;

import static com.github.jorge2m.testmaker.conf.Channel.*;
import static com.github.jorge2m.testmaker.domain.StateExecution.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.domain.RepositoryI;
import com.github.jorge2m.testmaker.domain.StateExecution;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteBean;
import com.github.jorge2m.testmaker.service.FilterSuites.SetSuiteRun;


public class FilterSuitesTest {

	private LocalDate fechaHoy = LocalDate.now();
	private SuiteBean suite1 = getNew("Suite1", FINISHED, "SmokeTest", desktop, "shop", fechaHoy);
	private SuiteBean suite2 = getNew("Suite2", STARTED, "SmokeTest", mobile, "shop", fechaHoy);
	private SuiteBean suite3 = getNew("Suite3", STOPPING, "MenusPais", desktop, "outlet", fechaHoy.minusDays(3));
	private SuiteBean suite4 = getNew("Suite4", STOPPED, "MenusPais", mobile, "shop", fechaHoy.minusDays(1));
	private SuiteBean suite5 = getNew("Suite5", FINISHED, "SmokeTest", desktop, "outlet", fechaHoy.minusDays(5));
	
	private List<SuiteBean> listSuitesInMemory = Arrays.asList(suite1, suite2, suite3);
	private List<SuiteBean> listSuitesInRepository = Arrays.asList(suite3, suite4, suite5);
	private List<SuiteBean> listSuitesInRepositoryDesde = Arrays.asList(suite4);
	
	
	@Test
	public void testGetAllSuites() throws Exception {
		//Given
		FilterSuites filterSuitesSpy = createFilterSuitesSpy(FilterSuites.getNew());
		
		//When
		List<SuiteBean> listSuites = filterSuitesSpy.getListSuites();
		
		//Then
		assertTrue(listSuites.contains(suite1));
		assertTrue(listSuites.contains(suite2));
		assertTrue(listSuites.contains(suite3));
		assertTrue(listSuites.contains(suite4));
		assertTrue(listSuites.contains(suite5));
		assertTrue(listSuites.size()==5);
	}
	
	@Test
	public void testGetRunningSuites() throws Exception {
		//Given
		FilterSuites filterSuitesSpy = createFilterSuitesSpy(FilterSuites.getNew(null, null, null, SetSuiteRun.running, null, null));
		
		//When
		List<SuiteBean> listSuites = filterSuitesSpy.getListSuites();
		
		//Then
		assertTrue(listSuites.contains(suite2));
		assertTrue(listSuites.contains(suite3));
		assertTrue(listSuites.size()==2);
	}
	
	@Test
	public void testGetSuitesMemoryAndRepository() throws Exception {
		//Given
		FilterSuites filterSuitesSpy = createFilterSuitesSpy(FilterSuites.getNew("SmokeTest", null, null, null, null, null));
		
		//When
		List<SuiteBean> listSuites = filterSuitesSpy.getListSuites();
		
		//Then
		assertTrue(listSuites.contains(suite1));
		assertTrue(listSuites.contains(suite2));
		assertTrue(listSuites.contains(suite5));
		assertTrue(listSuites.size()==3);
	}
	
	@Test
	public void testGetSuitesDesde() throws Exception {
		//Given
		LocalDate fechaDesde = fechaHoy.minusDays(3);
		LocalDate fechaHasta = fechaHoy.minusDays(1);
		FilterSuites filterSuitesSpy = createFilterSuitesSpy(FilterSuites.getNew(null, null, null, null, getDate(fechaDesde), getDate(fechaHasta)));
		
		//When
		List<SuiteBean> listSuites = filterSuitesSpy.getListSuites();
		
		//Then
		assertTrue(listSuites.contains(suite3));
		assertTrue(listSuites.contains(suite4));
		assertTrue(listSuites.size()==2);
	}
	
	private FilterSuites createFilterSuitesSpy(FilterSuites filterSuites) throws Exception {
		FilterSuites filterSuitesSpy = Mockito.spy(filterSuites);
		when(filterSuitesSpy.getListSuitesInMemory()).thenReturn(listSuitesInMemory);
		
		RepositoryI repository = Mockito.mock(RepositoryI.class);
		filterSuitesSpy.setRepository(repository);
		when(repository.getListSuites()).thenReturn(listSuitesInRepository);
		when(repository.getListSuitesBetween(Mockito.any(Date.class), Mockito.any(Date.class))).thenReturn(listSuitesInRepositoryDesde);
		return filterSuitesSpy;
	}

	private SuiteBean getNew(String idExecSuite, 
							 StateExecution stateExecution, 
							 String nameSuite, 
							 Channel channel, 
							 String app, 
							 LocalDate iniFecha) {
		SuiteBean suiteData = new SuiteBean();
		suiteData.setIdExecSuite(idExecSuite);
		suiteData.setStateExecution(stateExecution);
		suiteData.setName(nameSuite);
		suiteData.setChannel(channel);
		suiteData.setApp(app);
		suiteData.setInicioDate(getDate(iniFecha));
		return suiteData;
	}
	
	private Date getDate(LocalDate localDate) {
		return (Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
	}

}
