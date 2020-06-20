package com.github.jorge2m.testmaker.boundary.remotetest;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseBean;

public class ConcealerCharConversionTest {

	private final static String TestCaseDescr = "DescripciÃ³n del TestCase";
	private final static String StepDescr = "DescripciÃ³n step Ãmbitos";
	private final static String StepResExpected = "Codificación correcta";
	private final static String Check1Descr = "DescripciÃ³n check1 MÃ©ritos";
	private final static String Check2Descr = "Descripción check2 correcta";
	
	@Test
	public void testConceal() {
		//Given
		TestCaseBean testCase = makeTestCase();
		
		//When
		ConcealerCharConversion.conceal(testCase);
		
		StepTM step = testCase.getListStep().get(0);
		ChecksTM checks = step.getListChecksTM().get(0);
		Check check1 = checks.get(0);
		Check check2 = checks.get(1);
		
		//Then
		assertTrue("Descripción del TestCase".compareTo(testCase.getDescription())==0);
		assertTrue("Descripción step Ámbitos".compareTo(step.getDescripcion())==0);
		assertTrue(StepResExpected.compareTo(step.getResExpected())==0);
		assertTrue("Descripción check1 Méritos".compareTo(check1.getDescription())==0);
		assertTrue(Check2Descr.compareTo(check2.getDescription())==0);
	}

	private TestCaseBean makeTestCase() {
		TestCaseBean testCase = new TestCaseBean();
		testCase.setDescription(TestCaseDescr);
		
		StepTM step = new StepTM();
		step.setDescripcion(StepDescr);
		step.setResExpected(StepResExpected);
		
		ChecksTM checks = new ChecksTM();
		Check check1 = new Check();
		Check check2 = new Check();
		check1.setDescription(Check1Descr);
		check2.setDescription(Check2Descr);
		
		testCase.setListStep(Arrays.asList(step));
		step.setListChecksTM(Arrays.asList(checks));
		checks.setListChecks(Arrays.asList(check1, check2));
		
		return testCase;
	}
}
