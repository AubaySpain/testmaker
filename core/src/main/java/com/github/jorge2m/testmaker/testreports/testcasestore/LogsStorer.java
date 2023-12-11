package com.github.jorge2m.testmaker.testreports.testcasestore;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.conf.State;

public class LogsStorer extends TestCaseEvidenceStorerBase {

	public LogsStorer(TestCaseTM testcase) {
		super(TestCaseEvidence.LOGS, testcase);
	}
	
	@Override
	protected void store() {
		if (isRemote()) {
			String content = getContentFromLogSuite();
			testcase.setLogs(content);
		} else {
			String content = testcase.getLogs();
			if (isNeededStoreInFile(content)) {
				storeInFile(content);
			}
		}
	}
		
	private String getContentFromLogSuite() {
		var pathLogFile = testcase.getSuiteParent().getPathLogFile();
        Path path = Paths.get(pathLogFile);
        if (Files.exists(path)) {
        	return readFile(path, 2);
        } else {
            return "";
        }		
	}
	
	private String readFile(Path path, int retries) {
		int tried = 0;
		do  {
            try {
                return Files.readString(path);
            } catch (IOException e) {
            	PageObjTM.waitMillis(1000);
            }
			tried++;
		} while (tried <= retries); 
		
		return "";
	}
	
	private boolean isNeededStoreInFile(String content) {
		State result = testcase.getStateFromSteps(); 
		return 
			content!=null && "".compareTo(content)!=0 &&
			(result.isMoreCriticThan(State.WARN) || testcase.isExceptionInExecution());
	}
	
}
