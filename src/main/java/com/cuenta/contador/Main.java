package com.cuenta.contador;

import com.cuenta.contador.infra.InfraBinder;
import com.cuenta.contador.infra.MigrationHandler;
import com.cuenta.contador.server.resource.AuthenticationResource;
import com.cuenta.contador.server.resource.MyResource;
import com.cuenta.contador.server.serializer.SerializerBinder;
import com.cuenta.contador.service.ServiceBinder;
import com.cuenta.contador.service.requestfilter.AuthenticationFilter;
import com.cuenta.contador.service.requestfilter.RequestLogger;
import com.cuenta.contador.service.requestfilter.ResponseLogger;
import com.cuenta.contador.store.StoreBinder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

/**
 * Main class.
 *
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in com.cuenta.contador package

        MigrationHandler migrationHandler = new MigrationHandler();
        migrationHandler.migrate();

        final ResourceConfig rc = new ResourceConfig()
                .register(MyResource.class)
                .register(AuthenticationResource.class)
                .register(JacksonFeature.class)
                .register(ServiceBinder.class)
                .register(SerializerBinder.class)
                .register(InfraBinder.class)
                .register(StoreBinder.class)
                .register(AuthenticationFilter.class)
                .register(RequestLogger.class)
                .register(ResponseLogger.class);
//        rc.

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with endpoints available at "
                + "%s%nHit Ctrl-C to stop it...", BASE_URI));
        System.in.read();
        server.stop();
    }
}

