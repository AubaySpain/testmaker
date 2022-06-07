package com.github.jorge2m.testmaker.service.notifications;

import static org.junit.Assert.*;

import org.junit.Test;


public class DataAlertTest {

	@Test
	public void testIsUrlPattern() {
		String url = "http://localhost:80/output-library/SmokeTest/220607_170305221/ReportTSuite.html";
		assertTrue(DataAlert.isUrlPattern(url)); 
	}
	
	@Test
	public void testIsNotUrlPattern() {
		String url = "://localhost:80/output-library/SmokeTest/220607_170305221/ReportTSuite.html";
		assertTrue(!DataAlert.isUrlPattern(url)); 
	}

}
