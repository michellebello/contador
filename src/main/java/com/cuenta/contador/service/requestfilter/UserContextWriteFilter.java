package com.cuenta.contador.service.requestfilter;

import com.cuenta.contador.service.context.ContextService;
import com.cuenta.contador.service.user.User.UserID;
import com.cuenta.contador.service.user.UserContext;

import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;

import java.io.IOException;
import java.util.UUID;

public class UserContextWriteFilter implements ContainerRequestFilter {

    private final ContextService contextService;

    @Inject
    public UserContextWriteFilter(ContextService contextService) {
        this.contextService = contextService;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (FilterUtils.isPublicUri(requestContext.getUriInfo())) {
            return;
        }
        UUID sessionToken = FilterUtils.getSessionToken(requestContext);
        try {
            UserID userID = contextService.getUserID(sessionToken);
            UserContext.setUserContext(sessionToken, userID);
        } catch (Exception e) {
            throw new WebApplicationException(401);
        }
    }
}
