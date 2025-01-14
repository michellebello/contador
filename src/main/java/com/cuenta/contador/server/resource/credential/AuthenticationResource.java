package com.cuenta.contador.server.resource.credential;

import com.cuenta.contador.infra.Pair;
import com.cuenta.contador.server.json.credential.CredentialJson;
import com.cuenta.contador.server.json.user.RegisterJson;
import com.cuenta.contador.server.serializer.credential.AuthenticationSerializer;
import com.cuenta.contador.service.auth.AuthenticationService;
import com.cuenta.contador.service.credential.Credential;
import com.cuenta.contador.service.user.User;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
