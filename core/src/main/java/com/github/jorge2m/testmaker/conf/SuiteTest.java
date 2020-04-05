package com.github.jorge2m.testmaker.conf;

public interface SuiteTest {
	public int getMaxSecondsToWaitStart();
	public SuiteTest getValueOf(String suiteName);
}
