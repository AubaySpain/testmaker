package org.jorge2m.testmaker.restcontroller;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.jorge2m.testmaker.boundary.access.CmdLineMaker;
import org.jorge2m.testmaker.boundary.access.MessageError;
import org.jorge2m.testmaker.boundary.access.ResultCheckOptions;
import org.jorge2m.testmaker.conf.Channel;
import org.jorge2m.testmaker.domain.CreatorSuiteRun;
import org.jorge2m.testmaker.domain.InputParamsBasic;
import org.jorge2m.testmaker.domain.InputParamsTM;
import org.jorge2m.testmaker.domain.ServerSubscribers;
import org.jorge2m.testmaker.domain.InputParamsTM.TypeAccess;
import org.jorge2m.testmaker.domain.ServerSubscribers.ServerSubscriber;
import org.jorge2m.testmaker.domain.suitetree.SuiteBean;
import org.jorge2m.testmaker.domain.suitetree.SuiteTM;
import org.jorge2m.testmaker.domain.testfilter.TestMethodData;
import org.jorge2m.testmaker.service.TestMaker;
import org.jorge2m.testmaker.service.FilterSuites.SetSuiteRun;

@Path("/")
public class RestApiTM {
	
	private final static CreatorSuiteRun creatorSuiteRun = ServerRestTM.getServerRestTM().getCreatorSuiteRun();
	private final static Class<? extends Enum<?>> suiteEnum = ServerRestTM.getServerRestTM().getSuiteEnum();
	private final static Class<? extends Enum<?>> appEnum = ServerRestTM.getServerRestTM().getAppEnum();
	
	List<String> listFormatsFecha = Arrays.asList("yyyy-MM-dd HH:mm:ss", "HH:mm:ss", "yyyy-MM-dd");


	@POST
	@Path("/suiterun")
	@Produces("application/json")
	public Response newSuiteRun(@BeanParam InputParamsBasic inputParams) {
		return newSuiteRun((InputParamsTM)inputParams);
	}
	
	public Response newSuiteRun(@BeanParam InputParamsTM inputParams) {
		inputParams.setSuiteEnum(suiteEnum);
		inputParams.setAppEnum(appEnum);
		inputParams.setTypeAccess(TypeAccess.Rest);
		try {
			CmdLineMaker cmdLineAccess = CmdLineMaker.from(inputParams);
			ResultCheckOptions resultCheck = cmdLineAccess.checkOptionsValue();
			if (resultCheck.isOk()) {
				creatorSuiteRun.setInputParams(inputParams);
				SuiteTM suite = TestMaker.execSuite(creatorSuiteRun, inputParams.isAsyncExec());
				return Response
						.status(Response.Status.OK) 
						.entity(suite.getSuiteBean())
						.build();
			} else {
				List<MessageError> listErrors = resultCheck.getListMessagesError();
				GenericEntity<List<MessageError>> entity = new GenericEntity<List<MessageError>>(listErrors){};
				return Response
						.status(Response.Status.BAD_REQUEST)
						.entity(entity)
						.build();
				}
		}
		catch (Exception e) {
			return Response
					.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e.getCause())
					.build();
		}
	}
	
	@GET
	@Path("/suiterun/{idexecution}")
	@Produces("application/json")
	public SuiteBean getSuiteRunData(@PathParam("idexecution") String idSuiteExec) throws Exception {
		SuiteBean suite = TestMaker.getSuite(idSuiteExec);
		if (suite!=null) {
			return suite;
		} else {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}
	
	@GET
	@Path("/suiterun/{idexecution}/report")
	public Response getSuiteReportHtml(@PathParam("idexecution") String idExecSuite) throws Exception {
		SuiteBean suite = TestMaker.getSuite(idExecSuite);
		if (suite!=null) {
			URI uriReport = UriBuilder.fromUri(suite.getUrlReportHtml()).build();
			return Response.temporaryRedirect(uriReport).build();
		} else {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}
	
	@GET
	@Path("/suiteruns")
	@Produces("application/json")
	public List<SuiteBean> getListSuitesRunData(
						   @QueryParam("suite") String suite,
						   @QueryParam("channel") String channel,
						   @QueryParam("application") String application,
						   @QueryParam("state") String state,
						   @QueryParam("desde") String fechaDesde) throws Exception {
		if (channel!=null) {
			if (!enumContains(Channel.class, channel)) {
				throw new WebApplicationException("Parameter 'channel' incorrect", Response.Status.BAD_REQUEST);
			}
		}
		if (state!=null) {
			if (!enumContains(SetSuiteRun.class, state)) {
				throw new WebApplicationException("Parameter 'state' incorrect", Response.Status.BAD_REQUEST);
			}
		}
		Date dateDesde = null;
		if (fechaDesde!=null) {
			try {
				dateDesde = getDateFromParam(fechaDesde);
			}
			catch (ParseException e) {
				throw new WebApplicationException(
						"Parameter 'desde' incorrect. Possible formats: " + listFormatsFecha, Response.Status.BAD_REQUEST);
			}
		}

		return (TestMaker.getListSuites(suite, channel, application, state, dateDesde));
	}
	
	@DELETE
	@Path("/suiterun/{idexecution}")
	public void stopSuiteRun(@PathParam("idexecution") String idExecSuite) {
		SuiteTM suite = TestMaker.getSuiteExecuted(idExecSuite);
		if (suite!=null) {
			TestMaker.stopSuite(suite);
		}
	}
	
	@GET
	@Path("/subscription")
	public Response addSubscriber(@QueryParam("urlslave") String urlslave) {
		try {
			URL urlSubscriber = new URL(urlslave);
			ServerSubscriber subscriber = new ServerSubscriber(urlSubscriber);
			ServerSubscribers.add(subscriber);
		}
		catch (MalformedURLException e) {
			return Response
				.status(Response.Status.BAD_REQUEST)
				.entity("urlslave param with malformed value " + urlslave)
				.build();
		}

		return Response.ok().build();
	}
	
	@DELETE
	@Path("/subscription")
	public Response removeSubscriber(@QueryParam("urlslave") String url) {
		try {
			URL urlSubscriber = new URL(url);
			ServerSubscriber subscriber = new ServerSubscriber(urlSubscriber);
			ServerSubscribers.remove(subscriber);
		}
		catch (MalformedURLException e) {
			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity("urlslave param with malformed value " + url)
					.build();
		}

		return Response.ok().build();
	}
	
	@POST
	@Path("/suite/testcases")
	@Produces("application/json")
	public List<TestMethodData> getTestCasesFromSuite(@BeanParam InputParamsTM inputParams) throws Exception {
		inputParams.setSuiteEnum(suiteEnum);
		inputParams.setAppEnum(appEnum);
		inputParams.setTypeAccess(TypeAccess.Rest);
		creatorSuiteRun.setInputParams(inputParams);
		return (creatorSuiteRun.getListAllTestCasesData());
	}

	
	@GET
	@Path("/testserver")
	public String test() {
		return "Jetty Server Started Ok";
	}
	
	private Date getDateFromParam(String fecha) throws ParseException {
		for (String fechaFormat : listFormatsFecha) {
			Date date = getFecha(fecha, new SimpleDateFormat(fechaFormat));
			if (date!=null) {
				return date;
			}
		}
		throw new ParseException("Unparseable date: \"" + fecha + "\"", 0);
	}

	private Date getFecha(String fecha, SimpleDateFormat fechaFormat) {
		try {
			Date date = fechaFormat.parse(fecha);
			return date;
		}
		catch (ParseException e) {
			return null;
		}
	}

	private boolean enumContains(Class<? extends Enum<?>> enumClass, String value) {
		for (Enum<?> enumItem : enumClass.getEnumConstants()) {
			if (enumItem.name().compareTo(value)==0) {
				return true;
			}
		}
		return false;
	}
}
