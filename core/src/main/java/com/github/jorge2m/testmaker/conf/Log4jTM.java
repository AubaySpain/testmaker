package com.github.jorge2m.testmaker.conf;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.status.StatusLogger;

import com.github.jorge2m.testmaker.domain.suitetree.SuiteBean;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;

public class Log4jTM {
	
	private static Logger loggerGlobal;
	
	public static Logger getLogger() {
		SuiteTM suite = SuiteTM.getSuiteCreatedInPresentThread();
		if (suite==null) {
			TestCaseTM testCase = TestCaseTM.getTestCaseInExecution();
			suite = testCase.getSuiteParent();
		}
		if (suite!=null) {
			return suite.getLogger();
		} else {
			return Log4jTM.getGlobal();
		}
	}
	
	public static Logger getGlobal() {
		if (loggerGlobal==null) {
			 loggerGlobal = createLogger("Global", SuiteTM.getPathDirectoryOutputTests());
		}
		return loggerGlobal;
	}
	
	public static Logger getSuiteLogger(String suiteIdExec, String pathLogFile) {
		LoggerContext ctx = (LoggerContext)LogManager.getContext(false);
		if (ctx.hasLogger(getLoggerName(suiteIdExec))) {
			return ctx.getLogger(getLoggerName(suiteIdExec));
		}
		return createLogger(suiteIdExec, pathLogFile);
	}
	public static Logger getSuiteLogger(SuiteBean suite) {
		return getSuiteLogger(suite.getIdExecSuite(), suite.getPathReportHtml());
	}
	public static void removeSuiteLogger(String suiteIdExec) {
		LoggerContext ctx = (LoggerContext)LogManager.getContext(false);
		Configuration config = ctx.getConfiguration();
		config.getAppender(getAppenderName(suiteIdExec)).stop();
		config.removeLogger(getLoggerName(suiteIdExec));
	}
	
	private static Logger createLogger(String id, String pathLogFile) {
		StatusLogger.getLogger().setLevel(Level.OFF);
		LoggerContext ctx = (LoggerContext)LogManager.getContext(false);
		Configuration config = ctx.getConfiguration();

		PatternLayout layout = PatternLayout.newBuilder()
			.withConfiguration(config)
			.withPattern("%d{HH:mm:ss.SSS} %level %msg%n")
			.build();

		String log4jFileAppender = getAppenderName(id);
		Appender appender = FileAppender.newBuilder()
			.setConfiguration(config)
			.setName(getAppenderName(id))
			.setLayout(layout)
			.withFileName(pathLogFile)
			.build();

		//Define a logger using the LoggerConfig class, associate the appender to it, and update the configuration:
		appender.start();
		config.addAppender(appender);

		AppenderRef ref = AppenderRef.createAppenderRef(log4jFileAppender, null, null);
		AppenderRef[] refs = new AppenderRef[] { ref };

		String log4jLogger = getLoggerName(id);
		LoggerConfig loggerConfig = LoggerConfig
			.createLogger(false, Level.INFO, log4jLogger, "true", refs, null, config, null);
		loggerConfig.addAppender(appender, null, null);
		config.addLogger(log4jLogger, loggerConfig);
		ctx.updateLoggers();
		return LogManager.getLogger(log4jLogger);
	}
	
	private static String getAppenderName(String id) {
		return id + "FileAppender";
	}
	private static String getLoggerName(String id) {
		return id + "Logger";
	}
}
