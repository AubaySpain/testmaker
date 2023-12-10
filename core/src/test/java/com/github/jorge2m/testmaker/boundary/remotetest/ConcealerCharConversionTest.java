package com.github.jorge2m.testmaker.boundary.remotetest;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseBean;

public class ConcealerCharConversionTest {

	private static final String TEST_CASE_DESCR = "DescripciÃ³n del TestCase";
	private static final String STEP_DESCR = "DescripciÃ³n step Ãmbitos";
	private static final String STEP_RES_EXPECTED = "Codificación correcta";
	private static final String CHECK1_DESCR = "DescripciÃ³n check1 MÃ©ritos";
	private static final String CHECK2_DESCR = "Descripción check2 correcta";
	
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
		assertEquals("Descripción del TestCase", testCase.getDescription());
		assertEquals("Descripción step Ámbitos", step.getDescripcion());
		assertEquals(STEP_RES_EXPECTED, step.getResExpected());
		assertEquals("Descripción check1 Méritos", check1.getDescription());
		assertEquals(CHECK2_DESCR, check2.getDescription());
	}

	private TestCaseBean makeTestCase() {
		TestCaseBean testCase = new TestCaseBean();
		testCase.setDescription(TEST_CASE_DESCR);
		
		StepTM step = new StepTM();
		step.setDescripcion(STEP_DESCR);
		step.setResExpected(STEP_RES_EXPECTED);
		
		ChecksTM checks = new ChecksTM();
		Check check1 = new Check();
		Check check2 = new Check();
		check1.setDescription(CHECK1_DESCR);
		check2.setDescription(CHECK2_DESCR);
		
		testCase.setListStep(Arrays.asList(step));
		step.setListChecksTM(Arrays.asList(checks));
		checks.setListChecks(Arrays.asList(check1, check2));
		
		return testCase;
	}
}
