package com.cuenta.contador.service.requestfilter;

import jakarta.annotation.Priority;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Logger;

@jakarta.ws.rs.ext.Provider
@Priority(500)
public class ResponseLogger implements ContainerResponseFilter {
    private static final Logger logger = Logger.getLogger(RequestLogger.class.getName());

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        LocalDateTime dateTime = LocalDateTime.now();
        String method = requestContext.getMethod();
        String path = requestContext.getUriInfo().getPath();
        logger.info(String.format("%s Ending Request: %s %s", dateTime, method, path));
    }
}
