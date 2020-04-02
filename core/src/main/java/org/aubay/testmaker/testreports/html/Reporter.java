package org.jorge2m.testmaker.testreports.html;

import java.util.*;

import org.jorge2m.testmaker.domain.suitetree.SuiteTM;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.xml.XmlSuite;

public class Reporter implements IReporter {

	public static final String TESTNG_FAILED_XML = "testng-failed.xml";

	@SuppressWarnings("unused")
	private XmlSuite m_xmlSuite;

	public Reporter() {}

	public Reporter(XmlSuite xmlSuite) {
		this.m_xmlSuite = xmlSuite;	
	}

	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		SuiteTM suite = (SuiteTM)xmlSuites.get(0);
		GenerateReports report = new GenerateReports();
		report.generateReport(xmlSuites, suites, suite.getPathDirectory());
	}
}