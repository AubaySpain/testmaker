package com.github.jorge2m.testmaker.domain;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class InputParamsTMTest {

	@Test
	public void testGetListTestCasesData() {
		//Given
		String listTestCaseItems = "FIC001,BOR001{1},COM001_Compra{10-5}";
		InputParamsTM inputParams = new InputParamsBasic();
		inputParams.setListTestCaseItems(Arrays.asList(listTestCaseItems.split(",")));
		
		//When
		List<TestCaseParams> listTestCaseData = inputParams.getListTestCasesData();
		
		//Then
		assertTrue(listTestCaseData.size()==2); //TODO change to 3
		assertTrue(listTestCaseData.get(0).getName().compareTo("FIC001")==0);
		assertTrue(listTestCaseData.get(0).getInvocationCount()==null);
		assertTrue(listTestCaseData.get(0).getThreadPoolSize()==null);
		
		assertTrue(listTestCaseData.get(1).getName().compareTo("BOR001")==0);
		assertTrue(listTestCaseData.get(1).getInvocationCount()==1);
		assertTrue(listTestCaseData.get(1).getThreadPoolSize()==null);
		
		assertTrue(listTestCaseData.get(2).getName().compareTo("COM001_Compra")==0);
		assertTrue(listTestCaseData.get(2).getInvocationCount()==10);
		assertTrue(listTestCaseData.get(2).getThreadPoolSize()==5);
	}
}
