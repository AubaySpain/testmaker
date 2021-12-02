package com.github.jorge2m.testmaker.testreports.stepstore;

import java.util.LinkedList;

import com.github.jorge2m.testmaker.conf.Log4jTM;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.proxy.CaptureType;

public class ReverseProxy {

	private static ThreadLocal<BrowserMobProxy> proxyInThread;
	private static LinkedList<Integer> listPortsAssigned; 
	
	private final static int INIT_PORT = 1000;
	private final static int MAX_SIZE_LIST_PORTS = 25;
	
	public static BrowserMobProxy getProxy() {
		return makeProxyInThread();
	}
	
	public static void destroyProxy() {
		if (proxyInThread!=null) {
			proxyInThread.remove();
		}
	}
	
	private static BrowserMobProxy makeProxyInThread() {
		if (proxyInThread==null) {
			proxyInThread = new ThreadLocal<>();
		}
		
		BrowserMobProxy proxy = null;
		if (proxyInThread!=null) {
			proxy = proxyInThread.get();
		}
		
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
			Log4jTM.getLogger().info("Created Proxy NetTraffic with port " + proxy.getPort());
		}
		
		return proxy;
	}

	
	private static int checkoutPort() {
		synchronized(NettrafficStorer.class) {
			if (listPortsAssigned==null) {
				listPortsAssigned = new LinkedList<>();
			}
			
			if (!listPortsAssigned.isEmpty()) {
				if (listPortsAssigned.size() >= MAX_SIZE_LIST_PORTS) {
					int port = listPortsAssigned.getFirst();
					listPortsAssigned.removeFirst();
					listPortsAssigned.addLast(port);
					return port;
				}
				
				int port = listPortsAssigned.getLast() + 1;
				listPortsAssigned.addLast(port);
				return port;
			}	
				
			int port = INIT_PORT;
			listPortsAssigned.addLast(port);
			return port;
		}
	}
	
	private static BrowserMobProxy forceCreateProxy() {
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
				Log4jTM.getLogger().info(e.getClass() + " in start of proxy in port " + port + ". " + e.getMessage());
				port = checkoutPort();
				i+=1;
			}
		}
		
		return proxy;
	}
	
	private static int checkoutPort(int numPort) {
		for (int i=0; i<listPortsAssigned.size(); i++) {
			if (listPortsAssigned.get(i)==numPort) {
				listPortsAssigned.remove(i);
			}
		}
	
		listPortsAssigned.addLast(numPort);
		return numPort;
	}
	
}
