package org.aubay.testmaker.domain;

import java.net.URL;

import org.aubay.testmaker.boundary.remotetest.RemoteTest;
import org.aubay.testmaker.domain.suitetree.TestCaseTM;
import org.aubay.testmaker.domain.util.RoundRobin;

public class ServerSubscribers {

	private static RoundRobin<ServerSubscriber> collection = new RoundRobin<>();

	public static boolean add(ServerSubscriber subscriber) {
		return collection.add(subscriber);
	}
	public static void remove(ServerSubscriber subscriber) {
		collection.remove(subscriber);
	}
	public static boolean isSome() {
		return collection.size() > 0;
	}
	
	public static void sendTestToRemoteServer(TestCaseTM testCase, Object testObject) throws Exception {
		ServerSubscriber server = collection.next();
		RemoteTest remoteTest = new RemoteTest(server);
		remoteTest.execute(testCase, testObject);
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
