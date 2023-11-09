package com.github.jorge2m.testmaker.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.TestNG;
import org.testng.annotations.InitObject;
import org.testng.xml.XmlSuite;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.conf.defaultstorer.RepositorySQLite;
import com.github.jorge2m.testmaker.domain.CreatorSuiteRun;
import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.domain.RepositoryI;
import com.github.jorge2m.testmaker.domain.SuitesExecuted;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteBean;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestRunTM;
import com.github.jorge2m.testmaker.service.FilterSuites.SetSuiteRun;
import com.github.jorge2m.testmaker.testreports.html.HtmlStatsSuitesBuilder;
import com.github.jorge2m.testmaker.domain.StateExecution;

import static com.github.jorge2m.testmaker.domain.StateExecution.*;

public class TestMaker {
	
	private static RepositoryI repository = new RepositorySQLite(); 
	
	public static void run(SuiteTM suite, boolean async) {
		File path = new File(suite.getPathDirectory());
		path.mkdir();
		logIniTestSuite(suite);
		if (async) {
			runInTestNgAsync(suite);
		} else {
			runInTestNgSync(suite);
		}
		logFinTestSuite(suite);
	}
	
	private static void logIniTestSuite(SuiteTM suite) {
		String message = "Init TestSuite " + suite.getIdExecution();
		suite.getLogger().info(message);
		System.out.println(message);
	}
	private static void logFinTestSuite(SuiteTM suite) {
		String message = "End TestSuite " + suite.getIdExecution();
		suite.getLogger().info(message);
		System.out.println(message);
	}

	public static void finishSuite(String idExecution) {
		finish(getSuiteExecuted(idExecution));
	}

	public static void finish(SuiteTM suite) {
		suite.end();
	}

	public static SuiteBean getSuite(String idExecution) throws Exception {
		var suite = getSuiteExecuted(idExecution);
		if (suite!=null) {
			return suite.getSuiteBean();
		}
		var suiteData = getSuiteStored(idExecution);
		if (suiteData!=null &&
		   !suiteData.getStateExecution().isFinished()) {
				suiteData.setStateExecution(Aborted);
		}
		return suiteData;
	}
	
	public static List<SuiteBean> getListSuites() throws Exception {
		return FilterSuites.getNew().getListSuites();
	}
	
	public static List<SuiteBean> getListSuites(String suite, String channel, String application, String setSuite, Date fechaDesde, Date fechaHasta) 
	throws Exception {
		var channelEnum = channel!=null ? Channel.valueOf(channel) : null;
		var setSuiteEnum = setSuite!=null ? SetSuiteRun.valueOf(setSuite) : null;
		return getListSuites(suite, channelEnum, application, setSuiteEnum, fechaDesde, fechaHasta);
	}
	
	public static List<SuiteBean> getListSuites(
			String suite, Channel channel, String application, SetSuiteRun setSuite, Date fechaDesde, Date fechaHasta) 
	throws Exception {
		var filterSuites = FilterSuites.getNew(suite, channel, application, setSuite, fechaDesde, fechaHasta);
		return filterSuites.getListSuites();
	}
	
	public static SuiteTM getSuiteExecuted(String idExecution) {
		return SuitesExecuted.getSuite(idExecution);
	}
	
	public static SuiteTM getSuiteExecuted(ITestContext ctx) {
		return (SuiteTM)ctx.getSuite().getXmlSuite();
	}
	
	private static SuiteBean getSuiteStored(String idExecution) throws Exception {
		return (repository.getSuite(idExecution));
	}
	
	public static SuiteTM execSuite(CreatorSuiteRun executorSuite, boolean async) throws Exception {
		return (executorSuite.execTestSuite(async));
	}
	
	public static void stopSuite(String idExecSuite) {
		stopSuite(getSuiteExecuted(idExecSuite));
	}
	public static void stopSuite(SuiteTM suite) {
		boolean stopOk = neatStop(suite);
		if (!stopOk) {
			suite.end();
		}
	}
	public static void purgeSuite(SuiteBean suite) {
		repository.delete(suite.getIdExecSuite());
	}
	public static boolean removeBD() {
		return repository.removeBD();
	}
	
	private static boolean neatStop(SuiteTM suite) {
		suite.setStateExecution(Stopping);
		var validStates = Arrays.asList(Stopped, Finished);
		return (waitForSuiteInState(suite, validStates, 15));
	}
	private static boolean waitForSuiteInState(SuiteTM suite, List<StateExecution> validStates, int maxSeconds) {
		for (int i=0; i<maxSeconds; i++) {
			var suiteState = suite.getStateExecution();
			if (validStates.contains(suiteState)) {
				return true;
			}
			sleep(1000);
		}
		return false;
	}
	
	public static InputParamsTM getInputParamsSuite(ITestContext ctx) {
		return getSuiteExecuted(ctx).getInputParams();
	}
	
	public static TestRunTM getTestRun(ITestContext ctx) {
		return (TestRunTM)ctx.getCurrentXmlTest();
	}
	
	public static Optional<TestCaseTM> getTestCase() {
		return TestCaseTM.getTestCaseInExecution();
	}
	
	public static SuiteTM getSuite() {
		return TestCaseTM.getTestCaseInExecution()
				.orElseThrow(NoSuchElementException::new)
				.getSuiteParent();
	}
	
	public static InputParamsTM getInputParamsSuite() {
		return TestCaseTM.getTestCaseInExecution()
				.orElseThrow(NoSuchElementException::new)
				.getInputParamsSuite();
	}
	
	public static WebDriver getDriverTestCase() {
		Optional<TestCaseTM> testCaseOp = getTestCase();
		if (testCaseOp.isPresent()) {
			return testCaseOp.get().getDriver();
		}
		return null;
	}
	
	public static WebDriver renewDriverTestCase() {
		var driver = getDriverTestCase();
		if (driver!=null) {
			driver.quit();
		}
		getTestCase()
			.orElseThrow(NoSuchElementException::new)
			.makeInitObjects(InitObject.WebDriver);
		
	    return getDriverTestCase();
	}

	public static StepTM getCurrentStepInExecution() {
		return getTestCase()
				.orElseThrow(NoSuchElementException::new)
				.getCurrentStepInExecution();
	}

	public static StepTM getLastStep() {
		return getTestCase()
				.orElseThrow(NoSuchElementException::new)
				.getLastStep();
	}

	public static String getParamTestRun(String idParam, ITestContext ctx) {
		return ctx.getCurrentXmlTest().getParameter(idParam);
	}
	
	public static RepositoryI getRepository() {
		return TestMaker.repository;
	}
	public static void setRepository(RepositoryI repository) {
		TestMaker.repository = repository;
	}

	public static void skipTestsIfSuiteStopped() {
		var testCaseOp = getTestCase(); 
		if (testCaseOp.isPresent()) {
			skipTestsIfSuiteEnded(testCaseOp.get().getSuiteParent());
		}
	}

	public static void skipTestsIfSuiteEnded(SuiteTM suite) {
		var statesSuiteEnded = Arrays.asList(Stopping, Stopped,	Finished);
		if (statesSuiteEnded.contains(suite.getStateExecution())) {
			throw new SkipException("Suite " + suite.getName() + " in state " + suite.getStateExecution());
		}
	}
	
	public static String getHtmlStatsSuites(List<SuiteBean> listSuites) 
			throws Exception {
		return getHtmlStatsSuites(listSuites, null, null);
	}
	
	public static String getHtmlStatsSuites(List<SuiteBean> listSuitesNew, List<SuiteBean> listSuitesOld, String hostTestMaker) 
			throws Exception {
		var getterHtmlSuites = new HtmlStatsSuitesBuilder(listSuitesNew, listSuitesOld, hostTestMaker);
		return getterHtmlSuites.getHtml();
	}


	private static void runInTestNgSync(SuiteTM suite) {
		TestNG tng = makeTestNG(suite);
		suite.start();
		tng.run();
		suite.sendAlarmsIfNeeded();
		suite.end();
	}

	private static void runInTestNgAsync(SuiteTM suite) {
		CompletableFuture.runAsync(() -> 
			runInTestNgSync(suite)
		);
	}

	private static TestNG makeTestNG(SuiteTM suite) {
		List<XmlSuite> suites = new ArrayList<>();
		suites.add(suite);    
		TestNG tng = new TestNG();
		tng.setXmlSuites(suites);
		tng.setUseDefaultListeners(false);
		return tng;
	}

	private static void sleep(int millis) {
		try {
			Thread.sleep(millis);
		}
		catch (InterruptedException e) {
			Log4jTM.getLogger().warn("Problem in Thread.sleep", e);
		}
	}

}
