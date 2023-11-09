package com.github.jorge2m.testmaker.domain;

import java.net.URL;
import java.util.List;
import java.util.Optional;

import com.github.jorge2m.testmaker.boundary.remotetest.RemoteTest;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteBean;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.github.jorge2m.testmaker.domain.util.RoundRobin;

public class ServerSubscribers {

	private static RoundRobin<ServerSubscriber> collection = new RoundRobin<>();

	public static boolean add(ServerSubscriber subscriber) {
		return collection.add(subscriber);
	}
	public static void remove(ServerSubscriber subscriber) {
		collection.remove(subscriber);
	}
	public static List<ServerSubscriber> getCollection() {
		return collection.getCollection();
	}
	public static boolean isSome() {
		return collection.size() > 0;
	}
	
	public static Optional<SuiteBean> sendTestToRemoteServer(TestCaseTM testCase, Object testObject) throws Exception {
		for (int i=0; i<collection.size(); i++) {
			var server = collection.next();
			var remoteTest = new RemoteTest(server);
			var suiteBeanOpt = remoteTest.execute(testCase, testObject);
			if (suiteBeanOpt.isPresent()) {
				return suiteBeanOpt;
			}
		}
		return Optional.empty();
	}
	
	public static class ServerSubscriber {

		private final URL url;
		
		public ServerSubscriber(URL url) {
			this.url = url;
		}
		public URL getUrl() {
			return url;
		}
		
		@Override
		public boolean equals(Object o) {
			if (o==this) {
				return true;
			}
			if (!(o instanceof ServerSubscriber)) { 
				return false; 
			} 
			ServerSubscriber c = (ServerSubscriber) o;
			if (c.getUrl().equals(this.getUrl())) {
				return true;
			}
			return (
				c.getUrl().toString().compareTo(this.getUrl().toString())==0);
		}
	}
}
