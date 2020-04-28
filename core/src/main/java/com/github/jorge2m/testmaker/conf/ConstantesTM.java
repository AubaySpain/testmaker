package com.github.jorge2m.testmaker.conf;

import java.io.File;

public class ConstantesTM {
	
	public static final String directoryOutputTests = "output-library";
	public static final String nameDirectoryStatics = "static";
	public static final String pathStatics = directoryOutputTests + File.separator + nameDirectoryStatics;
	public static final String nameReportHTMLTSuite = "ReportTSuite.html";
	public static final String nameLogFileSuite = "TMakerSuite.log";
	public static final String SQLiteFileAutomaticTestingSchema = "EXECUTION_TESTS.sqlite";
	public static final String URL_SOFTWAREISHARD = "http://www.softwareishard.com/har/viewer/?inputUrl=";
	public static final int CONST_HTML = 0;
	public static final String URL_Pattern = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
}
