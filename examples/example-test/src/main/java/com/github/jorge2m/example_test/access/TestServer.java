package com.github.jorge2m.example_test.access;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import com.github.jorge2m.testmaker.restcontroller.RestApiTM;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Objects;

public class TestServer {

    private static Server server;
    private static CloseableHttpClient client = HttpClients.createDefault();

    public static void main(String[] args) throws Exception {
        ResourceConfig rc = new ResourceConfig(RestApiTM.class);

        ServletHolder holder = new ServletHolder(new ServletContainer(rc));

        server = new Server(8080);

        ServletContextHandler contextHandler = new ServletContextHandler(server, "/*");
        contextHandler.addServlet(holder, "/*");
        contextHandler.addServlet(holder, "/service/*");

        server.start();
        System.out.println("Started");
    }
}
