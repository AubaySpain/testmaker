package com.github.jorge2m.testmaker.testreports.html;

import java.util.ArrayList;
import java.util.List;

import com.github.jorge2m.testmaker.domain.suitetree.SuiteBean;


public class HtmlStatsSuitesBuilder {

	private final List<SuiteBean> listSuitesNew;
	private final List<SuiteBean> listSuitesOld;
	private final String hostTestMaker;
	
	public HtmlStatsSuitesBuilder(
			List<SuiteBean> listSuitesNew, List<SuiteBean> listSuitesOld, String hostTestMaker) {
		this.listSuitesNew = listSuitesNew;
		this.listSuitesOld = listSuitesOld;
		this.hostTestMaker = hostTestMaker == null ? "" : hostTestMaker;
	}
	
	public String getHtml() throws Exception {
		List<SuiteTestCasesData> testCasesDataNew = getTestCasesData(listSuitesNew); 
		List<SuiteTestCasesData> testCasesDataOld =	getTestCasesData(listSuitesOld);
		HtmlEmailBuilder htmlEmailBuilder = new HtmlEmailBuilder(testCasesDataNew, testCasesDataOld, hostTestMaker); 
		return htmlEmailBuilder.getHtml();
	}
	
	private List<SuiteTestCasesData> getTestCasesData(List<SuiteBean> listSuites) throws Exception {
		if (listSuites==null) {
			return null;
		}
		List<SuiteTestCasesData> listSuitesTestCases = new ArrayList<>();
		if (listSuites!=null) {
			for (SuiteBean suite : listSuites) {
				SuiteTestCasesData suiteTestCases = new SuiteTestCasesData(suite);
				listSuitesTestCases.add(suiteTestCases);
			}
		}
		return listSuitesTestCases;
	}
	
}
