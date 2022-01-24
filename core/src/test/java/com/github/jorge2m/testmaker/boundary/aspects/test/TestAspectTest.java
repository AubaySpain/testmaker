package com.github.jorge2m.testmaker.boundary.aspects.test;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.mockito.Mockito;

import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.domain.StateExecution;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteTM;

public class TestAspectTest {

	@Test
	public void testIsNeededWaitForExecTest() {
		//Given
		SuiteTM suiteMock = getMockSuiteTest(10, 60, 7, 30);
		
		//When
		TestAspect testAspect = new TestAspect();
		assertTrue(testAspect.isNeededWaitForExecTest(suiteMock));
	}
	
	@Test
	public void testIsNotNeededWaitForExecTest() {
		//Given
		SuiteTM suiteMock = getMockSuiteTest(10, 60, 3, 50);
		
		//Then
		TestAspect testAspect = new TestAspect();
		assertTrue(!testAspect.isNeededWaitForExecTest(suiteMock));
	}	
	
	@Test
	public void testLimitIsNeededWaitForExecTest() {
		//Given
		SuiteTM suiteMock = getMockSuiteTest(3, 120, 1, 59);
		
		//When
		TestAspect testAspect = new TestAspect();
		assertTrue(testAspect.isNeededWaitForExecTest(suiteMock));
	}
	
	@Test
	public void testLimitIsNotNeededWaitForExecTest() {
		//Given
		SuiteTM suiteMock = getMockSuiteTest(3, 120, 1, 61);
		
		//Then
		TestAspect testAspect = new TestAspect();
		assertTrue(!testAspect.isNeededWaitForExecTest(suiteMock));
	}	
	
	@Test
	public void testRampZero() {
		//Given
		SuiteTM suiteMock = getMockSuiteTest(10, 0, 6, 5);
		
		//When
		TestAspect testAspect = new TestAspect();
		assertTrue(!testAspect.isNeededWaitForExecTest(suiteMock));
	}	
	
	@Test
	public void testTestsRunningZero() {
		//Given
		SuiteTM suiteMock = getMockSuiteTest(10, 60, 0, 30);
		
		//When
		TestAspect testAspect = new TestAspect();
		assertTrue(!testAspect.isNeededWaitForExecTest(suiteMock));
	}	
	
	@Test
	public void testInitGreatherThanRamp() {
		//Given
		SuiteTM suiteMock = getMockSuiteTest(10, 80, 20, 100);
		
		//When
		TestAspect testAspect = new TestAspect();
		assertTrue(!testAspect.isNeededWaitForExecTest(suiteMock));
	}	
	
	private SuiteTM getMockSuiteTest(
			int maxNumThreads, 
			int rampSeconds, 
			int numTestCasesRunning, 
			long timeFromInit) {
		
		InputParamsTM inputParams = Mockito.mock(InputParamsTM.class); 
		Mockito.when(inputParams.getThreadsRampNum()).thenReturn(rampSeconds);
		
		SuiteTM suite = Mockito.mock(SuiteTM.class);
		Mockito.when(suite.getThreadCount()).thenReturn(maxNumThreads);
		Mockito.when(suite.getInputParams()).thenReturn(inputParams);
		Mockito.when(suite.getNumberTestCases(StateExecution.Running)).thenReturn(numTestCasesRunning);
		Mockito.when(suite.getTimeFromInit(TimeUnit.SECONDS)).thenReturn(timeFromInit);
		
		return suite;
	}

}
