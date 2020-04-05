package com.github.jorge2m.testmaker.domain;

public class TestCaseParams {

	private String name;
	private Integer invocationCount;
	private Integer threadPoolSize;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getInvocationCount() {
		return invocationCount;
	}
	public void setInvocationCount(Integer invocationCount) {
		this.invocationCount = invocationCount;
	}
	public Integer getThreadPoolSize() {
		return threadPoolSize;
	}
	public void setThreadPoolSize(Integer threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
	}
}
