package com.github.jorge2m.testmaker.boundary.remotetest;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class JaxRsClient {

	public Client getClientIgnoreCertificates() throws Exception {
		SSLContext context = SSLContext.getInstance("TLSv1.2");
		TrustManager[] trustManagerArray = {new NullX509TrustManager()};
		context.init(null, trustManagerArray, null); 

		Client client = ClientBuilder.newBuilder()
				.hostnameVerifier(new NullHostnameVerifier())
				.sslContext(context)
				.build();
		return client;
	}
	
	protected static class NullHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}
	
	protected static class NullX509TrustManager implements X509TrustManager {
		public void checkClientTrusted(X509Certificate[] chain, String authType)
		throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[0];
		}
	}
}
