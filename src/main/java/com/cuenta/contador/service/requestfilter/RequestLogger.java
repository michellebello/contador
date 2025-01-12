package com.cuenta.contador.service.requestfilter;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import java.time.LocalDateTime;
import java.util.logging.Logger;

@javax.ws.rs.ext.Provider
@Priority(500)
public class RequestLogger implements ContainerRequestFilter {
    private static final Logger logger = Logger.getLogger(RequestLogger.class.getName());

    @Override
    public void filter(ContainerRequestContext filterContext) {
        LocalDateTime dateTime = LocalDateTime.now();
        String method = filterContext.getMethod();
        String path = filterContext.getUriInfo().getPath();
        logger.info(String.format("%s Starting Request: %s %s", dateTime, method, path));
    }
}
