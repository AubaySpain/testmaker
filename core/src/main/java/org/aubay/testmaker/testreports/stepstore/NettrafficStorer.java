package org.jorge2m.testmaker.testreports.stepstore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.LinkedList;

import org.apache.commons.compress.utils.IOUtils;
import org.jorge2m.testmaker.conf.Log4jConfig;
import org.jorge2m.testmaker.domain.suitetree.StepTM;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.CaptureType;

/**
 * NetTraffic Manager based into BrowserMobProxy Class.
 * Stored in each Thread -> 1 proxy for each TestNG Method (test case)
 * Project info: https://github.com/lightbody/browsermob-proxy
 *
 */
public class NettrafficStorer extends EvidenceStorer {
	
	static ThreadLocal<BrowserMobProxy> proxyInThread;
	static int initPort = 1000;
	static int maxSizeListPorts = 20;
	static LinkedList<Integer> listPortsAssigned; 
	final static String nameProxyInContext = "BrowserMobProxy";
	
	public NettrafficStorer() {
		super(StepEvidence.har);
		if (proxyInThread==null) {
			proxyInThread = new ThreadLocal<>();
		}
			
		BrowserMobProxy proxy = getProxy();
		if (proxy==null || ((BrowserMobProxyServer)proxy).isStopped()) {
			if (proxy==null) {
				proxy = forceCreateProxy();
			} else {
				int initPort = proxy.getPort();
				proxy = new BrowserMobProxyServer();
				proxy.setTrustAllServers(true);
				proxy.start(checkoutPort(initPort));
			}
		
			proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.REQUEST_HEADERS);
			proxyInThread.set(proxy);
			Log4jConfig.pLogger.info("Created Proxy NetTraffic with port " + proxy.getPort());
		}
	}
	
	@Override
	protected String captureContent(StepTM step) {
		String content = "";
		Har har = getProxy().getHar(); 
		Writer writer = new StringWriter();
		try {
			har.writeTo(writer);
			content = writer.toString();
		}
		catch (IOException e) {
			Log4jConfig.pLogger.warn("Exception writing har. " + e);
		}
		return content;
	}
	
	@Override
	public void storeContentInFile(StepTM step) {
		super.storeContentInFile(step);
		try {
			copyHarToHarp(getPathFile(step));
			Thread.sleep(1000);
		}
		catch (Exception e) {
			Log4jConfig.pLogger.warn("Exception writing copying har to harp. " + e);
		}
	}
	
	private void copyHarToHarp(String nameFileHar) throws Exception {
		File fileHar = new File(nameFileHar);
		File fileHarp = new File(nameFileHar.replace(StepEvidence.har.fileExtension, StepEvidence.harp.fileExtension));
		InputStream inHar = new FileInputStream(fileHar);
		OutputStream outHarp = new FileOutputStream(fileHarp);
		outHarp.write("onInputData(".getBytes());
		try {
			IOUtils.copy(inHar, outHarp);
			outHarp.write(");".getBytes());
		}
		finally {
			IOUtils.closeQuietly(inHar);
			IOUtils.closeQuietly(outHarp);
		}
	}
	
	public static BrowserMobProxy getProxy() {
		if (proxyInThread!=null) {
			return proxyInThread.get();
		}
		return null;
	}
	
	public static void destroyProxy() {
		if (proxyInThread!=null) {
			proxyInThread.remove();
		}
	}
	
	public int checkoutPort() {
		synchronized(NettrafficStorer.class) {
			if (listPortsAssigned==null) {
				listPortsAssigned = new LinkedList<>();
			}
			
			if (!listPortsAssigned.isEmpty()) {
				if (listPortsAssigned.size()>=maxSizeListPorts) {
					int port = listPortsAssigned.getFirst();
					listPortsAssigned.removeFirst();
					listPortsAssigned.addLast(port);
					return port;
				}
				
				int port = listPortsAssigned.getLast() + 1;
				listPortsAssigned.addLast(port);
				return port;
			}	
				
			int port = initPort;
			listPortsAssigned.addLast(port);
			return port;
		}
	}
	
	public void resetAndStartNetTraffic() {
		getProxy().newHar();
	}
	
	public static void stopNetTrafficThread() {
		BrowserMobProxy proxy = getProxy();
		if (proxy!=null) {
			if (!((BrowserMobProxyServer)proxy).isStopped()) {
				int port = proxy.getPort();
				proxy.stop();
				destroyProxy();
				Log4jConfig.pLogger.info("Stop Proxy NetTraffic with port " + port);
			}
		}
	}
	
	private BrowserMobProxy forceCreateProxy() {
		BrowserMobProxy proxy = new BrowserMobProxyServer();
		boolean started = false;
		int i=0;
		while (!started && i<5) {
			int port = 0;
			try {
				proxy.setTrustAllServers(true);
				port = checkoutPort();
				proxy.start(port);
				started = true;
			}
			catch (RuntimeException e) {
				//NOTA: No debería (miramos de rotar los puertos), pero si en algún momento se produce el RuntimeException por un "java.net.BindException: Address already in use: bind"
				//entonces se produce un problema según el cuál todo funciona OK pero el Java no acaba. Como si se quedara algún Thread enganchado 
				Log4jConfig.pLogger.info(e.getClass() + " in start of proxy in port " + port + ". " + e.getMessage());
				port = checkoutPort();
				i+=1;
			}
		}
		
		return proxy;
	}
	
	private int checkoutPort(int numPort) {
		for (int i=0; i<listPortsAssigned.size(); i++) {
			if (listPortsAssigned.get(i)==numPort) {
				listPortsAssigned.remove(i);
			}
		}
	
		listPortsAssigned.addLast(numPort);
		return numPort;
	}
}