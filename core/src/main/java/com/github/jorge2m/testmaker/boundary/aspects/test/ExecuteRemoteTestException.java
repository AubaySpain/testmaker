package com.github.jorge2m.testmaker.boundary.aspects.test;

public class ExecuteRemoteTestException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExecuteRemoteTestException(String errorMessage, Throwable err) {
		super(errorMessage, err);
	}
	
	public ExecuteRemoteTestException(String errorMessage) {
		super(errorMessage);
	}
}
