package com.github.jorge2m.testmaker.conf;

import java.io.File;

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

public class Log4jConfig {

    final public static String log4jFileAppender = "robotestFileAppender";
    final public static String log4jLogger = "robotestLogger";
    final public static String log4jFileName = "robottest.log";
	
    final public static Logger pLogger = LogManager.getLogger(log4jLogger);
    
    public static void configLog4java(String outputDirectorySuite) {
        //Configure a layout and appender programatically
        StatusLogger.getLogger().setLevel(Level.OFF);
        LoggerContext ctx = (LoggerContext)LogManager.getContext(false);
        Configuration config = ctx.getConfiguration();

        PatternLayout layout = PatternLayout.newBuilder()
          .withConfiguration(config)
          .withPattern("%d{HH:mm:ss.SSS} %level %msg%n")
          .build();

        Appender appender = FileAppender.newBuilder()
          .setConfiguration(config)
          .withName(log4jFileAppender)
          .withLayout(layout)
          .withFileName(outputDirectorySuite + File.separator + log4jFileName)
          .build();

        //Define a logger using the LoggerConfig class, associate the appender to it, and update the configuration:
        appender.start();
        config.addAppender(appender);

        AppenderRef ref = AppenderRef.createAppenderRef(log4jFileAppender, null, null);
        AppenderRef[] refs = new AppenderRef[] { ref };

        LoggerConfig loggerConfig = LoggerConfig
          .createLogger(false, Level.INFO, log4jLogger, "true", refs, null, config, null);
        loggerConfig.addAppender(appender, null, null);
        config.addLogger(log4jLogger, loggerConfig);
        ctx.updateLoggers();
    }
	
}
