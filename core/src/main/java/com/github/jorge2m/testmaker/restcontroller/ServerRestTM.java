package com.github.jorge2m.testmaker.restcontroller;

import java.net.InetAddress;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.github.jorge2m.testmaker.boundary.access.ServerCmdLine.ResultCmdServer;
import com.github.jorge2m.testmaker.boundary.remotetest.JaxRsClient;
import com.github.jorge2m.testmaker.domain.CreatorSuiteRun;

import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.server.handler.HandlerWrapper;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.ssl.SslContextFactory;
//import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class ServerRestTM extends JaxRsClient {
	
	private static ServerRestTM serverRestTM = null; 
	
	private final Integer httpPort;
	private final Integer httpsPort;
	private final String urlIniServerSlave;
	private final String urlServerSlave;
	private final String urlServerHub;
	private final String pathCertificate;
	private final String passwordCertificate;
	private final Server jettyServer;
	private final HttpConfiguration httpConfiguration;
	private final CreatorSuiteRun creatorSuiteRun;
	private final Class<? extends RestApiTM> restApiTM;
	private final Class<? extends Enum<?>> suiteEnum;
	private final Class<? extends Enum<?>> appEnum;

	private ServerRestTM(
			Integer httpPort, Integer httpsPort, 
			String urlIniServerSlave, String urlServerHub, String urlServerSlave, 
			String pathCertificate, String passwordCertificate,
			CreatorSuiteRun creatorSuiteRun, Class<? extends RestApiTM> restApiTM, 
			Class<? extends Enum<?>> suiteEnum, Class<? extends Enum<?>> appEnum) throws Exception {
		this.httpPort = httpPort;
		this.httpsPort = httpsPort;
		this.urlIniServerSlave = urlIniServerSlave;
		this.urlServerHub = urlServerHub;
		if (urlServerSlave!=null) {
			this.urlServerSlave = urlServerSlave;
		} else {
			this.urlServerSlave = makeUrlStartingServer();
		}
		this.pathCertificate = pathCertificate;
		this.passwordCertificate = passwordCertificate;
		this.creatorSuiteRun = creatorSuiteRun;
		this.restApiTM = restApiTM;
		this.suiteEnum = suiteEnum;
		this.appEnum = appEnum;
		jettyServer = new Server();
		httpConfiguration = new HttpConfiguration();
	}
	public static ServerRestTM getServerRestTM() {
		return serverRestTM;
	}
	
	private String makeUrlStartingServer() throws Exception {
		String hostAddress = InetAddress.getLocalHost().getHostAddress();
		if (httpPort!=null) {
			return "http://" + hostAddress + ":" + httpPort;
		}
		return "https://" + hostAddress + ":" + httpsPort;
	}
	
	public int getHttpPort() {
		return httpPort;
	}
	public int getHttpsPort() {
		return httpsPort;
	}
	public CreatorSuiteRun getCreatorSuiteRun() {
		return creatorSuiteRun;
	}
	public Class<? extends Enum<?>> getSuiteEnum() {
		return suiteEnum;
	}
	public Class<? extends Enum<?>> getAppEnum() {
		return appEnum;
	}
	public Server getJettyServer() {
		return this.jettyServer;
	}
	
	public void start() throws Exception {
		setServerHttpConnector();
		setServerJerseyHandler();
		if (httpsPort!=null) {
			setServerHttpsConnector();
		}
		try {
			jettyServer.start();
			System.out.println("Started Jetty Server!");
			if (httpPort!=null) {
				System.out.println("HttpPort: " + httpPort);
			}
			if (httpsPort!=null) {
				System.out.println("HttpsPort: " + httpsPort);
			}
			if (urlIniServerSlave!=null) {
				subscribeServerSlaveToStartingHub();
			}
			if (urlServerHub!=null) {
				subscribeStartingServerSlaveToHub();
			}
			jettyServer.join();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (urlServerHub!=null) {
				unsubscribeStartingServerSlaveFromHub();
			}
			jettyServer.destroy();
		}
	}

	private void subscribeStartingServerSlaveToHub() throws Exception {
		manageConnectionStartingServerSlaveToHub(true);
	}
	private void unsubscribeStartingServerSlaveFromHub() throws Exception {
		manageConnectionStartingServerSlaveToHub(false);
	}
	private void manageConnectionStartingServerSlaveToHub(boolean subscription) throws Exception {
		Client client = getClientIgnoreCertificates();
		Invocation.Builder builder = client
			.target(urlServerHub).path("subscription")
			.queryParam("urlslave", urlServerSlave)
			.request(MediaType.APPLICATION_JSON);
		
		Response response;
		if (subscription) {
			response = builder.get();
		} else {
			response = builder.delete();
		}
		
		String action = (subscription) ? "subscripting" : "unsubscripting";
		if (response.getStatus() >= 400) {
			System.out.println("Problem " + action + " server slave in url " + urlServerSlave + " with server hub in url " + urlServerHub);
			System.out.println(response);
		} else {
			System.out.println("Result OK " + action + " server slave in url " + urlServerSlave + " with server hub in url " + urlServerHub);
		}
	}
	
	private void subscribeServerSlaveToStartingHub() throws Exception {
		Client client = getClientIgnoreCertificates();
		String urlLocalServerHub = makeUrlStartingServer();
		Invocation.Builder builder = client
			.target(urlLocalServerHub).path("subscription")
			.queryParam("urlslave", urlIniServerSlave)
			.request(MediaType.APPLICATION_JSON);
		
		Response response = builder.get();
		if (response.getStatus() >= 400) {
			System.out.println("Problem subscripting url slave " + urlIniServerSlave + " in starting server hub in url " + urlLocalServerHub);
			System.out.println(response);
		} else {
			System.out.println("Result OK subscripting url slave " + urlIniServerSlave + " in starting server hub in url " + urlLocalServerHub);
		}
	}
	
	public static void stop() throws Exception {
		if (serverRestTM!=null) {
			try {
				serverRestTM.getJettyServer().stop();
				serverRestTM.getJettyServer().destroy();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void setServerHttpConnector() {
		if (httpsPort!=null) {
			httpConfiguration.setSecureScheme("https");
			httpConfiguration.setSecurePort(httpsPort);
		}
		ServerConnector http = new ServerConnector(jettyServer, new HttpConnectionFactory(httpConfiguration));
		if (httpPort!=null) {
			http.setPort(httpPort);
		}
		jettyServer.addConnector(http);
	}
	private void setServerHttpsConnector() {
		SslContextFactory sslContextFactory = new SslContextFactory(pathCertificate);
		sslContextFactory.setKeyStorePassword(passwordCertificate);
		HttpConfiguration httpsConfiguration = new HttpConfiguration(httpConfiguration);
		httpsConfiguration.addCustomizer(new SecureRequestCustomizer());
		ServerConnector httpsConnector = new ServerConnector(jettyServer,
			new SslConnectionFactory(sslContextFactory, HttpVersion.HTTP_1_1.asString()),
			new HttpConnectionFactory(httpsConfiguration));
		httpsConnector.setPort(httpsPort);
		jettyServer.addConnector(httpsConnector);
	}
	
	private void setServerJerseyHandler() {
		ServletContextHandler servletHandler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
		servletHandler.setContextPath("/");
		servletHandler.setHandler(getHandlers());
		ResourceConfig config = new ResourceConfig();
		//config.register(JacksonFeature.class);
		config.register(MoxyJsonFeature.class);
		config.register(restApiTM);
		config.register(LoggingFeature.class);
		servletHandler.addServlet(new ServletHolder(new ServletContainer(config)), "/*");
		jettyServer.setHandler(servletHandler);
	}
	
	private HandlerWrapper getHandlers() {
		ResourceHandler resource_handler = new ResourceHandler();
		resource_handler.setDirectoriesListed(false);
		resource_handler.setWelcomeFiles(new String[]{ "index.html" });
		resource_handler.setResourceBase(".");
		return resource_handler;
	}
	
	public static class Builder {
		private final CreatorSuiteRun creatorSuiteRun; 
		private final Class<? extends Enum<?>> suiteEnum;
		private final Class<? extends Enum<?>> appEnum;
		private Class<? extends RestApiTM> restApiTM = RestApiTM.class;
		private Integer httpPort = null;
		private Integer httpsPort = null; 
		private String urlIniServerSlave = null;
		private String urlServerSlave = null;
		private String urlServerHub = null;
		private String pathCertificate = ServerRestTM.class.getResource("/testkey.jks").toExternalForm();
		private String passwordCertificate = "robotest";

		public Builder(CreatorSuiteRun creatorSuiteRun, Class<? extends Enum<?>> suiteEnum, Class<? extends Enum<?>> appEnum) {
			this.creatorSuiteRun = creatorSuiteRun;
			this.suiteEnum = suiteEnum;
			this.appEnum = appEnum;
		}
		
		public Builder setWithParams(ResultCmdServer params) {
			portHttp(params.getPort());
			portHttps(params.getSecurePort());
			urlServerHub(params.getUrlServerHub());
			urlIniServerSlave(params.getUrlIniServerSlave());
			urlServerSlave(params.getUrlServerSlave());
			return this;
		}
		public Builder portHttp(Integer httpPort) {
			this.httpPort = httpPort;
			return this;
		}
		public Builder portHttps(Integer httpsPort) {
			this.httpsPort = httpsPort;
			return this;
		}
		public Builder urlIniServerSlave(String urlIniServerSlave) {
			this.urlIniServerSlave = urlIniServerSlave;
			return this;
		}
		public Builder urlServerHub(String urlServerHub) {
			this.urlServerHub = urlServerHub;
			return this;
		}
		public Builder urlServerSlave(String urlServerSlave) {
			this.urlServerSlave = urlServerSlave;
			return this;
		}
		public Builder certificate(String path, String password) {
			this.pathCertificate = path;
			this.passwordCertificate = password;
			return this;
		}
		public Builder restApi(Class<? extends RestApiTM> restApi) {
			this.restApiTM = restApi;
			return this;
		}

		public ServerRestTM build() throws Exception {
			boolean isRestartRequired = isRestartRequired(httpPort, httpsPort);
			if (isRestartRequired) {
				stop();
			}
			if (serverRestTM==null || isRestartRequired) {
				serverRestTM = new ServerRestTM(
						httpPort, httpsPort, urlIniServerSlave, urlServerHub, urlServerSlave,
						pathCertificate, passwordCertificate, 
						creatorSuiteRun, restApiTM, 
						suiteEnum, appEnum);
			}
			return serverRestTM;
		}
		private boolean isRestartRequired(Integer httpPort, Integer httpsPort) {
			return (
				serverRestTM!=null &&
				(serverRestTM.getHttpPort()!=httpPort || serverRestTM.getHttpsPort()!=httpsPort));
		}
	}
}
