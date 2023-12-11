package com.github.jorge2m.testmaker.domain;

public enum StateExecution { 
	NOT_STARTED(false), 
	STARTED(false), 
	RUNNING(false),
	FINISHED(true), 
	STOPPING(false), 
	STOPPED(true),
	ABORTED(true);
	
	boolean finished;
	private StateExecution(boolean finished) {
		this.finished = finished;
	}
	public boolean isFinished() {
		return finished;
	}
	
	public static StateExecution from(String value) {
		//Temporal If
		if ("NotStarted".compareTo(value)==0) {
			return NOT_STARTED;
		}
		return StateExecution.valueOf(value.toUpperCase());
	}
}