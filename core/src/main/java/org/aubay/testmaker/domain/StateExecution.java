package org.aubay.testmaker.domain;


public enum StateExecution {
	NotStarted(false), 
	Started(false), 
	Finished(true), 
	Stopping(false), 
	Stopped(true),
	Aborted(true);
	
	boolean finished;
	private StateExecution(boolean finished) {
		this.finished = finished;
	}
	public boolean isFinished() {
		return finished;
	}
}