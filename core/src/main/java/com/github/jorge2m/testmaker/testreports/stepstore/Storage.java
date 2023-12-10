package com.github.jorge2m.testmaker.testreports.stepstore;

public enum Storage {

	FILE(true, false), 
	MEMORY(false, true), 
	FILE_AND_MEMORY(true, true);
	
	boolean file;
	boolean memory;
	private Storage(boolean file, boolean memory) {
		this.file = file;
		this.memory = memory;
	}
	public boolean inFile() {
		return file;
	}
	public boolean inMemory() {
		return memory;
	}
	
}
