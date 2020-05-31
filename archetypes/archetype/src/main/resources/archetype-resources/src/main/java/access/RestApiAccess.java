package ${package}.access;

import ${package}.access.datatmaker.Apps;
import ${package}.access.datatmaker.Suites;
import com.github.jorge2m.testmaker.boundary.access.ServerCmdLine;
import com.github.jorge2m.testmaker.boundary.access.ServerCmdLine.ResultCmdServer;
import com.github.jorge2m.testmaker.domain.CreatorSuiteRun;
import com.github.jorge2m.testmaker.restcontroller.ServerRestTM;

public class RestApiAccess {

	public static void main(String[] args) throws Exception {
		ResultCmdServer result = ServerCmdLine.parse(args);
		if (result!=null && result.isOk()) {
			CreatorSuiteRun creatorSuiteRun = MyCreatorSuiteRun.getNew();
			ServerRestTM serverRest = new ServerRestTM.Builder(creatorSuiteRun, Suites.class, Apps.class)
				.setWithParams(result)
				/*
				.portHttp(result.getPort())
				.portHttps(result.getSecurePort())
				.certificate(
					ServerRestTM.class.getResource("/robotest.pro.mango.com.pfx").toExternalForm(), 
					"yvuF62JiD6HsGVS9lqY9CsZZC/unbW1MMR3dLotF48Q=")*/
				.build();
			serverRest.start();
		}
	}
}
