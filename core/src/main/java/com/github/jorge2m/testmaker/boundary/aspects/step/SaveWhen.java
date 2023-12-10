package com.github.jorge2m.testmaker.boundary.aspects.step;

public enum SaveWhen {
	ALWAYS, 
	NEVER, 
	IF_PROBLEM;
	
	boolean ifProblemSave() {
		return (this==ALWAYS || this==IF_PROBLEM);
	}
	
}
