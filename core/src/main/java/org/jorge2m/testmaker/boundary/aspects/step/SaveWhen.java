package org.jorge2m.testmaker.boundary.aspects.step;

public enum SaveWhen {
	Always, 
	Never, 
	IfProblem;
	
	boolean IfProblemSave() {
		return (this==Always || this==IfProblem);
	}
}
