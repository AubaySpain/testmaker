package com.github.jorge2m.testmaker.restcontroller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ws.rs.BeanParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.apache.commons.io.FileUtils;

import com.github.jorge2m.testmaker.boundary.access.CmdLineMaker;
import com.github.jorge2m.testmaker.boundary.access.MessageError;
import com.github.jorge2m.testmaker.boundary.access.ResultCheckOptions;
import com.github.jorge2m.testmaker.boundary.remotetest.JaxRsClient;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.conf.defaultmail.SenderReportByMailAdapter;
import com.github.jorge2m.testmaker.domain.CreatorSuiteRun;
import com.github.jorge2m.testmaker.domain.InputParamsBasic;
import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.domain.ServerSubscribers;
import com.github.jorge2m.testmaker.domain.InputParamsTM.TypeAccess;
import com.github.jorge2m.testmaker.domain.SenderReportOutputPort;
import com.github.jorge2m.testmaker.domain.ServerSubscribers.ServerSubscriber;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteBean;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteTM;
import com.github.jorge2m.testmaker.domain.testfilter.TestMethodData;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.service.FilterSuites.SetSuiteRun;

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
				CreatorSuiteRun creatorSuiteRunService = new CreatorSuiteRunService(inputParams, creatorSuiteRun);
				//creatorSuiteRun.setInputParams(inputParams);
				SuiteTM suite = TestMaker.execSuite(creatorSuiteRunService, inputParams.isAsyncExec());
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
			Log4jTM.getLogger().error("Problem in suiterun execution" , e);
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
	
	@DELETE
	@Path("/suiterun/report")
	public void deleteSuiteReports(
					@QueryParam("suite") String suite,
					@QueryParam("channel") String channel,
					@QueryParam("application") String application,
					@QueryParam("state") String state,
					@QueryParam("date_from") String fechaDesde,
					@QueryParam("date_to") String fechaHasta) throws Exception {
		List<SuiteBean> listSuites = getListSuitesRunData(suite, channel, application, state, fechaDesde, fechaHasta);
		for (SuiteBean suiteBean : listSuites) {
			purgeSuite(suiteBean);
		}
	}
	
	private void purgeSuite(SuiteBean suite) throws IOException {
		purgeSuiteReport(suite);
		TestMaker.purgeSuite(suite);
	}
	private void purgeSuiteReport(SuiteBean suite) throws IOException {
		File file = new File(suite.getPathReportHtml());
		String nameParentDir = file.getParentFile().getName();
		if (nameParentDir.compareTo(suite.getIdExecSuite())==0) {
			FileUtils.deleteDirectory(file.getParentFile());
		}
	}
	
	@GET
	@Path("/suiteruns/mail")
	public Response sendReportMail(
			@HeaderParam("host") String host,
			@QueryParam("suite") String suite,
			@QueryParam("channel") String channel,
			@QueryParam("application") String application,
			@QueryParam("state") String state,
			@QueryParam("date_from") String fechaDesde,
			@QueryParam("date_to") String fechaHasta,
			@QueryParam("user") String user,
			@QueryParam("password") String password,
			@QueryParam("toMails") String toMails,
			@QueryParam("ccMails") String ccMails) throws Exception {
		List<SuiteBean> listSuites = getListSuitesRunData(
				suite, channel, application, state, fechaDesde, fechaHasta);
		SenderReportOutputPort sender = new SenderReportByMailAdapter(user, password, getMails(toMails), getMails(ccMails), host);
		boolean sendedOk = sender.send(listSuites);
		if (!sendedOk) {
			//throw new WebApplicationException("Problem sending email", Response.Status.INTERNAL_SERVER_ERROR);
		}
		return Response.ok().build();
	}
	
	private List<String> getMails(String mailsCommaSeparated) {
		if (mailsCommaSeparated!=null) {
			String[] mails = mailsCommaSeparated.split(",");
			return Arrays.asList(mails);
		}
		return Arrays.asList();
	}
	
	@GET
	@Path("/suiteruns")
	@Produces("application/json")
	public List<SuiteBean> getListSuitesRunData(
						   @QueryParam("suite") String suite,
						   @QueryParam("channel") String channel,
						   @QueryParam("application") String application,
						   @QueryParam("state") String state,
						   @QueryParam("date_from") String fechaDesde,
						   @QueryParam("date_to") String fechaHasta) throws Exception {
		if (channel!=null) {
			if (!enumContains(Channel.class, channel)) {
				throw new WebApplicationException(Response
					.status(Response.Status.BAD_REQUEST)
					.entity("Parameter 'channel' incorrect. Possible values: " + Arrays.asList(Channel.values()))
					.build());
			}
		}
		if (state!=null) {
			if (!enumContains(SetSuiteRun.class, state)) {
				throw new WebApplicationException(Response
					.status(Response.Status.BAD_REQUEST)
					.entity("Parameter 'state' incorrect. Possible values: " + Arrays.asList(SetSuiteRun.values()))
					.build());
			}
		}
		Date dateDesde = null;
		if (fechaDesde!=null) {
			try {
				dateDesde = getDateFromParam(fechaDesde);
			}
			catch (ParseException e) {
				throw new WebApplicationException(Response
					.status(Response.Status.BAD_REQUEST)
					.entity("Parameter 'date_from' incorrect. Possible formats: " + listFormatsFecha)
					.build());
			}
		}
		
		Date dateHasta = null;
		if (fechaHasta!=null) {
			try {
				dateHasta = getDateFromParam(fechaHasta);
			}
			catch (ParseException e) {
				throw new WebApplicationException(Response
					.status(Response.Status.BAD_REQUEST)
					.entity("Parameter 'date_to' incorrect. Possible formats: " + listFormatsFecha)
					.build());
			}
		}

		return (TestMaker.getListSuites(suite, channel, application, state, dateDesde, dateHasta));
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
	public Response addSubscriber(
			@QueryParam("urlslave") String urlslave, 
			@DefaultValue("false") @QueryParam("checkslave") boolean checkslave) {
		
		if (checkslave) {
			Optional<Response> responseCheck = checkSlaveAvailability(urlslave);
			if (responseCheck.isPresent()) {
				return responseCheck.get();
			}
		}
		
		//Subscribe slave to hub
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

	private Optional<Response> checkSlaveAvailability(String urlslave) {
		try {
			if (!checkServerAvailability(urlslave, 5)) {
				return Optional.of(
					Response
						.status(Response.Status.NOT_FOUND)
						.entity("Not available Server Slave in " + urlslave)
						.build());
			}
		}
		catch (Exception e) {
			return Optional.of(
				Response
					.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e.getCause())
					.build());
		}
		
		return Optional.empty();
	}
	
	private boolean checkServerAvailability(String urlSlave, int retries) throws Exception {
		for (int i=0; i<retries; i++) {
			if (checkServerAvailability(urlSlave)) {
				return true;
			}
			Thread.sleep(1000);
		}
		return false;
	}
	
	private boolean checkServerAvailability(String urlSlave) throws Exception {
		JaxRsClient jaxRsClient = new JaxRsClient();
		Client client = jaxRsClient.getClientIgnoreCertificates();
		try {
			client
				.target(urlSlave + "/testserver")
				.request(MediaType.APPLICATION_JSON)
				.get();
			return true;
		}
		catch (Exception e) {
			return false;
		}
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
	
	@GET
	@Path("/subscriptions")
	@Produces("application/json")
	public StringList getSubscriptionsCollection() {	
		List<String> servers = ServerSubscribers.getCollection().stream()
				.map(s -> s.getUrl().toString())
				.collect(Collectors.toList());
		
		return new StringList(servers);
	}
	
	@POST
	@Path("/suite/testcases")
	@Produces("application/json")
	public List<TestMethodData> getTestCasesFromSuite(@BeanParam InputParamsBasic inputParams) throws Exception {
		return getTestCasesFromSuite((InputParamsTM)inputParams);
	}
	
	public List<TestMethodData> getTestCasesFromSuite(@BeanParam InputParamsTM inputParams) throws Exception {
		inputParams.setSuiteEnum(suiteEnum);
		inputParams.setAppEnum(appEnum);
		inputParams.setTypeAccess(TypeAccess.Rest);
		CreatorSuiteRun creatorSuiteRunService = new CreatorSuiteRunService(inputParams, creatorSuiteRun);
		//creatorSuiteRun.setInputParams(inputParams);
		return (creatorSuiteRunService.getListAllTestCasesData());
	}

	
	@GET
	@Path("/testserver")
	public String test() {
		return "Jetty Server Started Ok";
	}
	
	@GET
	@Path("/health")
	public String health() {
		return "{\"status\":\"UP\"}";
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
