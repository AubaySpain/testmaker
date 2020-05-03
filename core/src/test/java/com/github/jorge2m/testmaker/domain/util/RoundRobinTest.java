package com.github.jorge2m.testmaker.domain.util;

import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

import com.github.jorge2m.testmaker.domain.ServerSubscribers.ServerSubscriber;

public class RoundRobinTest {
	
	@Test
	public void test() throws MalformedURLException {
		//Given
		ServerSubscriber server1 = new ServerSubscriber(new URL("https://robotest1.mango.com:444"));
		ServerSubscriber server2 = new ServerSubscriber(new URL("https://robotest2.mango.com:444"));
		ServerSubscriber server3 = new ServerSubscriber(new URL("https://robotest2.mango.com:555"));
		
		//When
		RoundRobin<ServerSubscriber> listServers = new RoundRobin<>();
		listServers.add(server1);
		listServers.add(server1);
		listServers.add(server2);
		listServers.add(server3);
		
		//Then
		assertTrue(listServers.size()==3);
		assertTrue(listServers.next().equals(server1));
		assertTrue(listServers.next().equals(server2));
		assertTrue(listServers.next().equals(server3));
		assertTrue(listServers.next().equals(server1));
	}

}
