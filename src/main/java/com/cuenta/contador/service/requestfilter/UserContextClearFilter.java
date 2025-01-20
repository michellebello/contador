package com.cuenta.contador.service.requestfilter;

import com.cuenta.contador.service.user.UserContext;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;

import java.io.IOException;

public class UserContextClearFilter implements ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        if(FilterUtils.isPublicUri(requestContext.getUriInfo())) {
            return;
        }
        UserContext.clearUserContext();
    }
}
