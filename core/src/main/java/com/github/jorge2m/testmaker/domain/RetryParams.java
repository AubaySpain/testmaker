package com.github.jorge2m.testmaker.domain;

public class RetryParams {

	private Integer retriesTestCase = 0;
	private Integer retriesMaxSuite = 999;
	
	public Integer getRetriesTestCase() {
		return retriesTestCase;
	}
	public void setRetriesTestCase(Integer retriesTestCase) {
		this.retriesTestCase = retriesTestCase;
	}
	public Integer getRetriesMaxSuite() {
		return retriesMaxSuite;
	}
	public void setRetriesMaxSuite(Integer retriesMaxSuite) {
		this.retriesMaxSuite = retriesMaxSuite;
	}

}
