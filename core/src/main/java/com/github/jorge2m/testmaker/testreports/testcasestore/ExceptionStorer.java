package com.github.jorge2m.testmaker.testreports.testcasestore;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;

public class ExceptionStorer extends TestCaseEvidenceStorerBase {

	public ExceptionStorer(TestCaseTM testcase) {
		super(TestCaseEvidence.EXCEPTION, testcase);
	}
	
	@Override
	protected void store() {
		String content = getContent();
		if ("".compareTo(content)!=0) {
			storeInFile(content);
		}
	}
	
	private String getContent() {
		Throwable exception = testcase.getResult().getThrowable();
		if (exception!=null) {
			return getStackTraceString(exception);
		}
		return "";
	}
	
    private static String getStackTraceString(Throwable throwable) {
        var stringWriter = new StringWriter();
        var printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);
        return stringWriter.toString();
    }
    
}
