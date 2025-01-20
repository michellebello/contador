package com.cuenta.contador;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.ServerConfiguration;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MyResourceTest {

    // Define the base URI for the server
    private static final URI BASE_URI = URI.create("http://localhost:8080/");

    private HttpServer server;

    @BeforeEach
    public void setUp() {
        // Configure Jersey
        ResourceConfig config = new ResourceConfig()
                .packages("your.package.path") // Replace with your resource package
                .register(org.glassfish.jersey.jackson.JacksonFeature.class); // Register Jackson for JSON support

        // Start the server
        server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, config);
    }
    @AfterEach
    public void tearDown() {
        if (server != null) {
            server.shutdown();
        }
    }

    @Test
    public void testServerStarts() {
        assertNotNull(server, "Server should have started successfully");
        ServerConfiguration configuration = server.getServerConfiguration();
        assertNotNull(configuration, "Server configuration should be initialized");
    }
}