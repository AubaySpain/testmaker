package com.github.jorge2m.testmaker.service.testab;

public interface TestABOptimize extends TestAB {

	@Override
	default TypeTestAB getType() {
		return TypeTestAB.Optimize;
	}
	
	public String getIdExperiment();
}
