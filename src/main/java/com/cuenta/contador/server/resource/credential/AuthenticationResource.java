package com.cuenta.contador.server.resource.credential;

import com.cuenta.contador.infra.Pair;
import com.cuenta.contador.server.json.credential.CredentialJson;
import com.cuenta.contador.server.json.user.RegisterJson;
import com.cuenta.contador.server.serializer.credential.AuthenticationSerializer;
import com.cuenta.contador.service.auth.AuthenticationService;
import com.cuenta.contador.service.credential.Credential;
import com.cuenta.contador.service.user.User;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
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
    public Response register(RegisterJson registerJson) {
        try {
            Pair<Credential, User> credentialUserPair = authenticationSerializer.fromRegisterJson(registerJson);
            UUID sessionId = authenticationService.createUser(credentialUserPair.getLeft(), credentialUserPair.getRight());
            return Response.ok(sessionId).build();
        } catch (Exception e) {
            return Response.status(400).build();
        }
    }

    @Path("login")
    @POST
    public Response login(CredentialJson credentialJson) {
        try {
            Credential credential = authenticationSerializer.fromCredentialJson(credentialJson);
            UUID sessionId = authenticationService.authenticate(credential.getUsername(), credential.getPassword());
            return Response.ok(sessionId).build();
        } catch (Exception e) {
            return Response.status(400).build();
        }
    }
}
