package com.github.jorge2m.testmaker.testreports.html;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteBean;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseBean;
import com.github.jorge2m.testmaker.domain.suitetree.TestRunBean;
import com.github.jorge2m.testmaker.testreports.html.GenerateReports;

public class GenerateReportsTest {

	@Test
	public void testGetMapTree() {
		//Given
		SuiteBean suite = Mockito.mock(SuiteBean.class);
		TestRunBean testRun1 = Mockito.mock(TestRunBean.class);
		TestCaseBean testCase1 = Mockito.mock(TestCaseBean.class);
		StepTM step1 = Mockito.mock(StepTM.class);
		StepTM step2 = Mockito.mock(StepTM.class);
		ChecksTM val1 = Mockito.mock(ChecksTM.class);
		ChecksTM val2 = Mockito.mock(ChecksTM.class);
		TestCaseBean testCase2 = Mockito.mock(TestCaseBean.class);
		StepTM step3 = Mockito.mock(StepTM.class);
		ChecksTM val3 = Mockito.mock(ChecksTM.class);
		
		when(suite.getListTestRun()).thenReturn(Arrays.asList(testRun1));
		when(testRun1.getListTestCase()).thenReturn(Arrays.asList(testCase1,testCase2));
		when(testCase1.getListStep()).thenReturn(Arrays.asList(step1,step2));
		when(step2.getListChecksTM()).thenReturn(Arrays.asList(val1,val2));
		when(testCase2.getListStep()).thenReturn(Arrays.asList(step3));
		when(step3.getListChecksTM()).thenReturn(Arrays.asList(val3));
		
		//When
		List<Integer> mapTree = GenerateReports.getMapTree(suite);
		
		//Then
		String arrayStringExpected = "[0, 1, 2, 2, 4, 4, 1, 7, 8]";
		String arrayStringObtained = mapTree.toString();
		assertTrue(
			"The array string obtained " + arrayStringObtained + " is equals to the expected " + arrayStringExpected,
			arrayStringObtained.compareTo(arrayStringExpected)==0);
	}

}
