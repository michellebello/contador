package com.cuenta.contador.service.requestfilter;

import com.cuenta.contador.service.auth.AuthenticationService;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import jakarta.inject.Inject;
import java.io.IOException;
import java.net.URI; // Corrected import
import java.util.List; // Corrected import
import java.util.UUID;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
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
                throw new WebApplicationException(
                        Response.status(Response.Status.UNAUTHORIZED)
                                .entity("Invalid or expired session token.")
                                .build()
                );
            }
        } catch (IllegalArgumentException e) {
            throw new WebApplicationException(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity("Invalid session token format.")
                            .build()
            );
        }
    }
}