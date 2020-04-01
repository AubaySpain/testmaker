package org.aubay.testmaker.service.testab;

import java.util.List;

import org.aubay.testmaker.conf.Channel;

public class TestABactData {

	private final TestAB testAB;
	private final int vToActive;
	
	private TestABactData(TestAB testAB, int vToActive, List<Channel> supportedChannels, List<Enum<?>> supportedApps) {
		this.testAB = testAB;
		this.vToActive = vToActive;
	}
	
	public static TestABactData getNew(TestAB testAB, int vToActivate) {
		return (new TestABactData(testAB, vToActivate, null, null));
	}
	
	public TestAB getTestAB() {
		return testAB;
	}

	public int getvToActive() {
		return vToActive;
	}
}
