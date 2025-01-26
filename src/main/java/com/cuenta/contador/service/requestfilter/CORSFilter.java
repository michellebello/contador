package com.cuenta.contador.service.requestfilter;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Provider
@PreMatching
public class CORSFilter implements ContainerRequestFilter, ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if ("OPTIONS".equalsIgnoreCase(requestContext.getMethod())) {
            requestContext.abortWith(
                    Response.ok()
                            .header("Access-Control-Allow-Origin", "http://localhost:3000")
                            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                            .header("Access-Control-Allow-Headers", "Content-Type, Authorization, CONTADOR_TOKEN")
                            .header("Access-Control-Allow-Credentials", "true")
                            .build()
            );
            return; // Ensure no further processing for preflight requests
        }
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        // Before adding the header, check if it already exists
        if (!responseContext.getHeaders().containsKey("Access-Control-Allow-Origin")) {
            responseContext.getHeaders().add("Access-Control-Allow-Origin", "http://localhost:3000");
            responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            responseContext.getHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization, CONTADOR_TOKEN");
            responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
        }
    }
}


