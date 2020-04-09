package com.github.jorge2m.example_test.access;

import com.github.jorge2m.example_test.access.CreatorSuiteRunTest;
import com.github.jorge2m.example_test.access.CreatorSuiteRunTest.Apps;
import com.github.jorge2m.example_test.access.CreatorSuiteRunTest.Suites;
import com.github.jorge2m.testmaker.boundary.access.ServerCmdLine;
import com.github.jorge2m.testmaker.boundary.access.ServerCmdLine.ResultCmdServer;
import com.github.jorge2m.testmaker.domain.CreatorSuiteRun;
import com.github.jorge2m.testmaker.restcontroller.ServerRestTM;

public class ServerRest {

	public static void main(String[] args) throws Exception {
		ResultCmdServer result = ServerCmdLine.parse(args);
		if (result!=null && result.isOk()) {
			CreatorSuiteRun creatorSuiteRun = new CreatorSuiteRunTest();
			ServerRestTM serverRest = new ServerRestTM.Builder(creatorSuiteRun, Suites.class, Apps.class)
				.setWithParams(result)
				.build();
			serverRest.start();
		}
	}
}
