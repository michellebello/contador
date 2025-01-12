package com.cuenta.contador.service.requestfilter;

import com.cuenta.contador.service.auth.AuthenticationService;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.UriInfo;
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

    private static final String CONTADOR_TOKEN = "CONTADOR_TOKEN";
    private static final List<URI> PUBLIC_URIS = List.of(
            URI.create("auth/login"),
            URI.create("auth/register")
    );

    @Inject
    AuthenticationFilter(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public void filter(ContainerRequestContext filterContext) throws IOException {
        if (PUBLIC_URIS.stream().anyMatch(uri -> uri.getPath().equals(filterContext.getUriInfo().getPath()))) {
            return;
        }
        verifySession(filterContext);
    }

    private void verifySession(ContainerRequestContext filterContext) {
        String sessionTokenStr = filterContext.getHeaderString(CONTADOR_TOKEN);
        if (sessionTokenStr == null) {
            throw new WebApplicationException(401);
        }
        try {
            UUID sessionToken = UUID.fromString(sessionTokenStr);
            if (!authenticationService.isValidSession(sessionToken)) {
                throw new WebApplicationException(401);
            }
        } catch (Exception e) {
            throw new WebApplicationException(401);
        }
    }
}