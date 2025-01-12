package com.cuenta.contador.service.requestfilter;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

@javax.ws.rs.ext.Provider
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
