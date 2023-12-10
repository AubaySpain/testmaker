package com.github.jorge2m.testmaker.testreports.testcasestore;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;

public class ExceptionStorer extends TestCaseEvidenceStorer {

	public ExceptionStorer() {
		super(TestCaseEvidence.EXCEPTION);
	}
	
	@Override
	protected String captureContent(TestCaseTM testcase) {
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
