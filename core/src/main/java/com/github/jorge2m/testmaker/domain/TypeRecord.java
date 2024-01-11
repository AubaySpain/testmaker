package com.github.jorge2m.testmaker.domain;

import java.util.ArrayList;
import java.util.List;

import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;

public enum TypeRecord {
	ALWAYS("true"),
	NEVER("false"),
	WHEN_RETRYKO("whenretryko"),
	WHEN_DEFECT("whendefect");
	
	private String param;
	private TypeRecord(String param) {
		this.param = param;
	}
	
	public String getParam() {
		return param;
	}
	
	public static TypeRecord of(String param) {
		for (var typeRecord : values()) {
			if (typeRecord.getParam().equals(param)) {
				return typeRecord;
			}
		}
		return TypeRecord.NEVER;
	}
	
	public static List<String> getAllParamValues() {
		List<String> paramValues = new ArrayList<>();
		for (var typeRecord : values()) {
			paramValues.add(typeRecord.getParam());
		}
		return paramValues;
	}
	
	public static boolean isStartRecordNeeded(TestCaseTM testcase) {
		if (!driverSupportsVideo(testcase)) {
			return false;
		}

		switch (getTypeRecord(testcase)) {
		case ALWAYS:
			return true;
		case WHEN_RETRYKO:
			return testcase.isRetried();
		case WHEN_DEFECT:
			return true;
		default:
			return false;
		}
	}
	
	public static boolean isStopRecordNeeded(TestCaseTM testcase) {
		if (!driverSupportsVideo(testcase)) {
			return false;
		}
		
		switch (getTypeRecord(testcase)) {
		case ALWAYS:
			return true;
		case WHEN_RETRYKO:
			return testcase.isRetried();
		case WHEN_DEFECT:
			return (testcase.getStateResult().isMoreCriticThan(State.WARN));
		default:
			return false;
		}
	}
	
	private static boolean driverSupportsVideo(TestCaseTM testcase) {
		return testcase.getInputParamsSuite().getDriverType().supportsVideo();
	}
	
	private static TypeRecord getTypeRecord(TestCaseTM testcase) {
		return TypeRecord.of(testcase.getInputParamsSuite().getRecord());
	}
	
}
