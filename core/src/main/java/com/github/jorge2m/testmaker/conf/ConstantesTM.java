package com.github.jorge2m.testmaker.conf;

import java.io.File;

public class ConstantesTM {
	
	private ConstantesTM() {}
	
	public static final String DIRECTORY_OUTPUT_TESTS = "output-library";
	public static final String NAME_DIRECTORY_STATICS = "static";
	public static final String PATH_STATICS = DIRECTORY_OUTPUT_TESTS + File.separator + NAME_DIRECTORY_STATICS;
	public static final String NAME_REPORT_HTML_TSUITE = "ReportTSuite.html";
	public static final String MAME_LOG_FILE_SUITE = "TMakerSuite.log";
	public static final String SQLITE_FILE_AUTOMATIC_TESTING_SCHEMA = "EXECUTION_TESTS.sqlite";
	public static final String URL_SOFTWAREISHARD = "http://www.softwareishard.com/har/viewer/?inputUrl=";
	public static final int CONST_HTML = 0;
	public static final String URL_PATTERN = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
}
