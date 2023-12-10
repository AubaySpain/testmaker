package com.github.jorge2m.testmaker.domain;

import static org.testng.Assert.assertEquals;

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
		assertEquals(listTestCaseData.size(), 3);
		assertEquals(listTestCaseData.get(0).getName(), "FIC001");
		assertEquals(listTestCaseData.get(0).getInvocationCount(), null);
		assertEquals(listTestCaseData.get(0).getThreadPoolSize(), null);
		
		assertEquals(listTestCaseData.get(1).getName(), "BOR001");
		assertEquals(listTestCaseData.get(1).getInvocationCount(), Integer.valueOf(1));
		assertEquals(listTestCaseData.get(1).getThreadPoolSize(), null);
		
		assertEquals(listTestCaseData.get(2).getName(), "COM001_Compra");
		assertEquals(listTestCaseData.get(2).getInvocationCount(), Integer.valueOf(10));
		assertEquals(listTestCaseData.get(2).getThreadPoolSize(), Integer.valueOf(5));
	}
}
