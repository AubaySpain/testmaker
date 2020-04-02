package org.jorge2m.testmaker.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.jorge2m.testmaker.boundary.access.OptionTMaker;

public class InputParamsBasic extends InputParamsTM {
	
	private final List<OptionTMaker> specificOptions;
	
	public InputParamsBasic() {
		super();
		this.specificOptions = new ArrayList<>();
	}
	public InputParamsBasic(Class<? extends Enum<?>> suiteEnum, Class<? extends Enum<?>> appEnum) {
		super(suiteEnum, appEnum);
		this.specificOptions = new ArrayList<>();
	}
	public InputParamsBasic(List<OptionTMaker> specificOptions, Class<? extends Enum<?>> suiteEnum, Class<? extends Enum<?>> appEnum) {
		super(suiteEnum, appEnum);
		this.specificOptions = specificOptions;
	}
	
	@Override
	public List<OptionTMaker> getSpecificParameters() {
		return specificOptions;
	}
	
	@Override
	public void setSpecificDataFromCommandLine(CommandLine cmdLine) {}
	
	@Override
	public Map<String,String> getSpecificParamsValues() {
		return new HashMap<String, String>();
	}
	
}
