package com.github.jorge2m.testmaker.testreports.html;

import java.util.List;

import org.testng.ISuite;
import org.testng.reporters.EmailableReporter2;
import org.testng.xml.XmlSuite;

import com.github.jorge2m.testmaker.domain.suitetree.SuiteTM;

public class GenerateReports extends EmailableReporter2 {
	
	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		super.generateReport(xmlSuites, suites, outputDirectory);
		var suiteTM = ((SuiteTM)xmlSuites.get(0));
		try {
			new GenerateReportTM(suiteTM, outputDirectory).generate();
		} 
		catch (Exception e) {
			suiteTM.getLogger().fatal("Problem generating ReportHTML", e);
		}
	}
	
}
