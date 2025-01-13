package com.cuenta.contador.service.requestfilter;

import com.cuenta.contador.service.user.UserContext;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import java.io.IOException;

public class UserContextClearFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if(FilterUtils.isPublicUri(requestContext.getUriInfo())) {
            return;
        }
        UserContext.clearUserContext();
    }
}
