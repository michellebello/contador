package com.cuenta.contador.service.requestfilter;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.UUID;

public class FilterUtils {
    private static final String CONTADOR_TOKEN = "CONTADOR_TOKEN";
    private static final List<URI> PUBLIC_URIS = List.of(
            URI.create("auth/login"),
            URI.create("auth/register")
    );

    public static UUID getSessionToken(ContainerRequestContext requestContext) throws IllegalArgumentException {
        String sessionTokenStr = requestContext.getHeaderString(CONTADOR_TOKEN);
        if (sessionTokenStr == null) {
            throw new IllegalArgumentException("Session token not found in request headers");
        }
        try {
            return UUID.fromString(sessionTokenStr);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid session token format");
        }
    }

    public static boolean isPublicUri(UriInfo uriInfo) {
        return PUBLIC_URIS.stream().anyMatch(uri -> uri.getPath().equals(uriInfo.getPath()));
    }
}
