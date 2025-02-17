package com.cuenta.contador.server.resource.credential;

import com.cuenta.contador.infra.Pair;
import com.cuenta.contador.infra.Unauthenticated;
import com.cuenta.contador.server.json.credential.CredentialJson;
import com.cuenta.contador.server.json.user.RegisterJson;
import com.cuenta.contador.server.serializer.credential.AuthenticationSerializer;
import com.cuenta.contador.service.auth.AuthenticationService;
import com.cuenta.contador.service.credential.Credential;
import com.cuenta.contador.service.user.User;

import com.cuenta.contador.service.user.UserContext;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.UUID;

@Path("auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthenticationResource {
    private final AuthenticationService authenticationService;
    private final AuthenticationSerializer authenticationSerializer;

    @Inject
    public AuthenticationResource(AuthenticationService authenticationService, AuthenticationSerializer authenticationSerializer) {
        this.authenticationService = authenticationService;
        this.authenticationSerializer = authenticationSerializer;
    }

    @Path("register")
    @POST
    @Unauthenticated
    public Response register(RegisterJson registerJson) {
        try {
            Pair<Credential, User> credentialUserPair = authenticationSerializer.fromRegisterJson(registerJson);
            UUID sessionId = authenticationService.createUser(credentialUserPair.getLeft(), credentialUserPair.getRight());
            return Response.ok(sessionId).build();
        } catch (Exception e) {
            return Response.status(400).build();
        }
    }

    @POST
    @Path("login")
    @Unauthenticated
    public Response login(CredentialJson credentialJson) {
        try {
            Credential credential = authenticationSerializer.fromCredentialJson(credentialJson);
            UUID sessionId = authenticationService.authenticate(credential.getUsername(), credential.getPassword());
            return Response.ok(sessionId).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new ErrorMessage(e.getMessage()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorMessage("An error occurred, please try again"))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @POST
    @Path("logout")
    public Response logout(){
        UUID sessionId = UserContext.getSessionId();
        try {
            authenticationService.deleteSession(sessionId);
            return Response.status(200).entity("SessionId deleted").build();
        } catch(Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity("SessionId not found, try again").build();
        }
    }

    public static class ErrorMessage {
        private String message;

        public ErrorMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
