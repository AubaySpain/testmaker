package org.aubay.testmaker.boundary.access;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;
import org.aubay.testmaker.conf.ConstantesTM;

public class ServerCmdLine {

	final static int PORT_DEFAULT = 80;
	final static int SECUREPORT_DEFAULT= 443;
	
	final static String portparam = "port";
	final static String secureportparam = "secureport";
	final static String urlhubparam = "urlhub";
	final static String urlslaveparam = "urlslave";
	
	/** 
	 * [-port porthttp] [-secureport porthttps] [-urlhub url] [-urlslave url] [-help | -h] 
	 */  
	public static ResultCmdServer parse(String[] args) {
		ResultCmdServer resultParse = new ResultCmdServer();
		resultParse.setOk(false);
		Options options = setParamsStructure();
		try {
			resultParse = parseAndGetParamData(options, args);
		} catch (ParseException ex){  
			System.out.println(ex.getMessage());  
			new HelpFormatter().printHelp(ServerCmdLine.class.getCanonicalName(), options );
		} catch (java.lang.NumberFormatException ex){  
			new HelpFormatter().printHelp(ServerCmdLine.class.getCanonicalName(), options );
		}
		
		return resultParse;
	}
	
	private static Options setParamsStructure() {
		Options options = new Options();  
		options.addOption(Option.builder(portparam)
			.required(false)
			.hasArg()
			.desc("HTTP Server Port").build());
			
		options.addOption(Option.builder(secureportparam)
			.required(false)
			.hasArg()
			.desc("HTTPS Server Port").build());
		
		options.addOption(Option.builder(urlhubparam)
			.required(false)
			.hasArg()
			.desc("URL Server Hub where connect present slave").build());
		
		options.addOption(Option.builder(urlslaveparam)
			.required(false)
			.hasArg()
			.desc("URL Server Slave to subscribe in hub").build());
		
		return options;
	}
	
	private static ResultCmdServer parseAndGetParamData(Options options, String[] args) throws ParseException {
		ResultCmdServer resultParse = new ResultCmdServer();
		CommandLineParser parser  = new DefaultParser();
		CommandLine cmdLine = parser.parse(options, args);
		if (cmdLine.hasOption("h")){
			new HelpFormatter().printHelp(ServerCmdLine.class.getCanonicalName(), options );  
			return null;  
		}  

		if (cmdLine.hasOption(portparam)) {
			String port = cmdLine.getOptionValue(portparam);
			if (StringUtils.isNumeric(port)) {
				resultParse.setPort(Integer.parseInt(port));
			} else {
				System.out.println("Param " + portparam + " must be a numeric value");
				resultParse.setOk(false);
				return resultParse;
			}
		}
		
		if (cmdLine.hasOption(secureportparam)) {
			String portsecure = cmdLine.getOptionValue(secureportparam);
			if (StringUtils.isNumeric(portsecure)) {
				resultParse.setSecurePort(Integer.parseInt(portsecure));
			}
			else {
				System.out.println("Param " + secureportparam + " must be a numeric value");
				resultParse.setOk(false);
				return resultParse;
			}
		}
		
		if (cmdLine.hasOption(urlhubparam)) {
			String urlHub = cmdLine.getOptionValue(urlhubparam);
			if (checkPatternValue(ConstantesTM.URL_Pattern, urlHub)) {
				resultParse.setUrlServerHub(urlHub);
			}
			else {
				System.out.println("Param " + urlhubparam + " is not a url with a valid format");
				resultParse.setOk(false);
				return resultParse;
			}
		}
		
		if (cmdLine.hasOption(urlslaveparam)) {
			String urlServerSlave = cmdLine.getOptionValue(urlslaveparam);
			if (checkPatternValue(ConstantesTM.URL_Pattern, urlServerSlave)) {
				resultParse.setUrlServerSlave(urlServerSlave);
			}
			else {
				System.out.println("Param " + urlslaveparam + " is not a url with a valid format");
				resultParse.setOk(false);
				return resultParse;
			}
		}
		
		if (resultParse.getPort()==null && resultParse.getSecurePort()==null) {
			resultParse.setSecurePort(SECUREPORT_DEFAULT);  
			resultParse.setPort(PORT_DEFAULT);  
		}
		
		resultParse.setOk(true);
		return resultParse;
	}
	
	private static boolean checkPatternValue(String stringPattern, String value) {
		Pattern pattern = Pattern.compile(stringPattern);
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}
	

	public static class ResultCmdServer {
		private boolean ok = false;
		private Integer port = null;  
		private Integer securePort = null;
		private String urlServerHub = null;
		private String urlServerSlave = null;
		
		public boolean isOk() {
			return ok;
		}
		public void setOk(boolean ok) {
			this.ok = ok;
		}
		public Integer getPort() {
			return port;
		}
		public void setPort(Integer port) {
			this.port = port;
		}
		public Integer getSecurePort() {
			return securePort;
		}
		public void setSecurePort(Integer securePort) {
			this.securePort = securePort;
		}
		public String getUrlServerHub() {
			return urlServerHub;
		}
		public void setUrlServerHub(String urlServerHub) {
			this.urlServerHub = urlServerHub;
		}
		public String getUrlServerSlave() {
			return urlServerSlave;
		}
		public void setUrlServerSlave(String urlServerSlave) {
			this.urlServerSlave = urlServerSlave;
		}
	}
}
