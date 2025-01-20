package com.cuenta.contador;

import com.cuenta.contador.infra.InfraBinder;
import com.cuenta.contador.infra.MigrationHandler;

import com.cuenta.contador.server.serializer.SerializerBinder;
import com.cuenta.contador.service.ServiceBinder;
import com.cuenta.contador.service.requestfilter.*;
import com.cuenta.contador.store.StoreBinder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature; // Import JacksonFeature
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

/**
 * Main class for starting the Jersey-based server.
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/"; // Base path includes "/api"

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // Migrate the database before starting the server
        MigrationHandler migrationHandler = new MigrationHandler();
        migrationHandler.migrate();

        // Configure resources, filters, and binders
        final ResourceConfig rc = new ResourceConfig()
                .packages("com.cuenta.contador.server.resource") // Automatically scan resource package
                .register(JacksonFeature.class) // Ensure Jackson is registered
                .register(ServiceBinder.class)
                .register(SerializerBinder.class)
                .register(InfraBinder.class)
                .register(StoreBinder.class)
                .register(AuthenticationFilter.class)
                .register(RequestLogger.class)
                .register(ResponseLogger.class)
                .register(UserContextWriteFilter.class)
                .register(UserContextClearFilter.class);

        // Create and start a new instance of the Grizzly HTTP server
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     * @param args Command-line arguments
     * @throws IOException If server fails to start
     */
    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.printf("Jersey app started with endpoints available at "
                + "%s%nHit Ctrl-C to stop it...%n", BASE_URI);

        // Keep the server running until interrupted
        System.in.read();
        server.stop();
    }
}

