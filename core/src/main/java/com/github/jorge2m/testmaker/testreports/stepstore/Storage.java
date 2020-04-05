package com.github.jorge2m.testmaker.testreports.stepstore;

public enum Storage {

	File(true, false), 
	Memory(false, true), 
	FileAndMemory(true, true);
	
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
