package org.aubay.testmaker.service.testab;

public interface TestABGoogleExp extends TestAB {

	@Override
	default TypeTestAB getType() {
		return TypeTestAB.GoogleExperiments;
	}
	
	public String getValueCookie(Enum<?> app);
}
