package org.jorge2m.testmaker.service.testab;

import java.util.List;

import org.jorge2m.testmaker.conf.Channel;

public interface TestAB {

	public enum TypeTestAB {
		GoogleExperiments,
		Optimize;
	}
	
	public TypeTestAB getType();
	public List<Integer> getVariantes();
	public List<Channel> getChannels();
	public List<Enum<?>> getApps();
}


