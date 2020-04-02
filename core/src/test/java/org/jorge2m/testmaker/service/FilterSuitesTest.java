package org.jorge2m.testmaker.service;

import static org.jorge2m.testmaker.conf.Channel.*;
import static org.jorge2m.testmaker.domain.StateExecution.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.jorge2m.testmaker.conf.Channel;
import org.jorge2m.testmaker.domain.RepositoryI;
import org.jorge2m.testmaker.domain.StateExecution;
import org.jorge2m.testmaker.domain.suitetree.SuiteBean;
import org.jorge2m.testmaker.service.FilterSuites;
import org.jorge2m.testmaker.service.FilterSuites.SetSuiteRun;
import org.junit.Test;
import org.mockito.Mockito;


public class FilterSuitesTest {

	private LocalDate fechaHoy = LocalDate.now();
	private SuiteBean suite1 = getNew("Suite1", Finished, "SmokeTest", desktop, "shop", fechaHoy);
	private SuiteBean suite2 = getNew("Suite2", Started, "SmokeTest", movil_web, "shop", fechaHoy);
	private SuiteBean suite3 = getNew("Suite3", Stopping, "MenusPais", desktop, "outlet", fechaHoy.minusDays(3));
	private SuiteBean suite4 = getNew("Suite4", Stopped, "MenusPais", movil_web, "shop", fechaHoy.minusDays(1));
	private SuiteBean suite5 = getNew("Suite5", Finished, "SmokeTest", desktop, "outlet", fechaHoy.minusDays(5));
	
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
		FilterSuites filterSuitesSpy = createFilterSuitesSpy(FilterSuites.getNew(null, null, null, SetSuiteRun.running, null));
		
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
		FilterSuites filterSuitesSpy = createFilterSuitesSpy(FilterSuites.getNew("SmokeTest", null, null, null, null));
		
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
		LocalDate fechaDesde = fechaHoy.minusDays(2);
		FilterSuites filterSuitesSpy = createFilterSuitesSpy(FilterSuites.getNew(null, null, null, null, getDate(fechaDesde)));
		
		//When
		List<SuiteBean> listSuites = filterSuitesSpy.getListSuites();
		
		//Then
		assertTrue(listSuites.contains(suite1));
		assertTrue(listSuites.contains(suite2));
		assertTrue(listSuites.contains(suite4));
		assertTrue(listSuites.size()==3);
	}
	
	private FilterSuites createFilterSuitesSpy(FilterSuites filterSuites) throws Exception {
		FilterSuites filterSuitesSpy = Mockito.spy(filterSuites);
		when(filterSuitesSpy.getListSuitesInMemory()).thenReturn(listSuitesInMemory);
		
		RepositoryI repository = Mockito.mock(RepositoryI.class);
		filterSuitesSpy.setRepository(repository);
		when(repository.getListSuites()).thenReturn(listSuitesInRepository);
		when(repository.getListSuitesAfter(Mockito.any(Date.class))).thenReturn(listSuitesInRepositoryDesde);
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
