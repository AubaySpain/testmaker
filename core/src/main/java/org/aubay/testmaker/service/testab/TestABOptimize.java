package org.jorge2m.testmaker.service.testab;

public interface TestABOptimize extends TestAB {

	@Override
	default TypeTestAB getType() {
		return TypeTestAB.Optimize;
	}
	
	public String getAuth();
	public String getIdExperiment();
	public String getGroup();
	public String getPreview();
}
