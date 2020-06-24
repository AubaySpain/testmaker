package com.github.jorge2m.testmaker.restcontroller;

import com.github.jorge2m.testmaker.domain.CreatorSuiteRun;
import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.domain.SuiteMaker;

public class CreatorSuiteRunService extends CreatorSuiteRun {

	private final InputParamsTM inputParamsService;
	private final CreatorSuiteRun creatorServer;
	
	public CreatorSuiteRunService(InputParamsTM inputParams, CreatorSuiteRun creatorServer) throws Exception {
		super();
		this.inputParamsService = inputParams;
		this.creatorServer = creatorServer;
	}
	
	@Override
	public SuiteMaker getSuiteMaker() throws Exception {
		return getSuiteMakerSync(inputParamsService, creatorServer);
	}
	
	private static synchronized SuiteMaker getSuiteMakerSync(InputParamsTM inputParamsService, CreatorSuiteRun creatorServer) 
	throws Exception {
		System.out.println("creatorServer.setInputParams with: " + inputParamsService);
		creatorServer.setInputParams(inputParamsService);
		return creatorServer.getSuiteMaker();
	}
}
