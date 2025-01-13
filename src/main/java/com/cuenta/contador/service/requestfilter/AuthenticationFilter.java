package com.cuenta.contador.service.requestfilter;

import com.cuenta.contador.service.auth.AuthenticationService;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@Provider
@Priority(Priorities.AUTHENTICATION)
public
class AuthenticationFilter implements ContainerRequestFilter {
    private final AuthenticationService authenticationService;

    @Inject
    AuthenticationFilter(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public void filter(ContainerRequestContext filterContext) throws IOException {
        if (FilterUtils.isPublicUri(filterContext.getUriInfo())) {
            return;
        }
        verifySession(filterContext);
    }

    private void verifySession(ContainerRequestContext filterContext) {
        try {
            UUID sessionToken = FilterUtils.getSessionToken(filterContext);
            if (!authenticationService.isValidSession(sessionToken)) {
                throw new WebApplicationException(401);
            }
        } catch (IllegalArgumentException e) {
            throw new WebApplicationException(401);
        }
    }
}