package com.github.jorge2m.testmaker.testreports.stepstore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.commons.compress.utils.IOUtils;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.core.har.Har;

import static com.github.jorge2m.testmaker.testreports.stepstore.StepEvidence.*;

/**
 * NetTraffic Manager based into BrowserMobProxy Class.
 * Stored in each Thread -> 1 proxy for each TestNG Method (test case)
 * Project info: https://github.com/lightbody/browsermob-proxy
 *
 */
public class NettrafficStorer extends StepEvidenceStorer {
	
	public NettrafficStorer() {
		super(HAR);
	}
	
	@Override
	protected String captureContent(StepTM step) {
		String content = "";
		Har har = ReverseProxy.getProxy().getHar(); 
		Writer writer = new StringWriter();
		try {
			har.writeTo(writer);
			content = writer.toString();
		}
		catch (IOException e) {
			step.getSuiteParent().getLogger().warn("Exception writing har. {}", e);
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
		catch (IOException | InterruptedException e) {
			step.getSuiteParent().getLogger().warn("Exception writing copying har to harp. %s", e);
		}
	}
	
	private void copyHarToHarp(String nameFileHar) throws IOException {
	    File fileHar = new File(nameFileHar);
	    File fileHarp = new File(nameFileHar.replace(HAR.getFileExtension(), HARP.getFileExtension()));

	    try (InputStream inHar = new FileInputStream(fileHar);
	         OutputStream outHarp = new FileOutputStream(fileHarp)) {

	        outHarp.write("onInputData(".getBytes());
	        IOUtils.copy(inHar, outHarp);
	        outHarp.write(");".getBytes());
	    }
	}	
	
	public void resetAndStartNetTraffic() {
		ReverseProxy.getProxy().newHar();
	}
	
	public static void stopNetTrafficThread() {
		BrowserMobProxy proxy = ReverseProxy.getProxy();
		if (proxy!=null) {
			if (!((BrowserMobProxyServer)proxy).isStopped()) {
				int port = proxy.getPort();
				proxy.stop();
				ReverseProxy.destroyProxy();
				Log4jTM.getLogger().info("Stop Proxy NetTraffic with port {}", port);
			}
		}
	}
}