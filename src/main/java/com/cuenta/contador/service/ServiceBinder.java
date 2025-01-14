package com.cuenta.contador.service;

import com.cuenta.contador.service.account.AccountService;
import com.cuenta.contador.service.account.AccountServiceImpl;
import com.cuenta.contador.service.auth.AuthenticationService;
import com.cuenta.contador.service.auth.AuthenticationServiceImpl;
import com.cuenta.contador.service.context.ContextService;
import com.cuenta.contador.service.credential.CredentialService;
import com.cuenta.contador.service.credential.CredentialServiceImpl;
import com.cuenta.contador.service.session.SessionService;
import com.cuenta.contador.service.session.SessionServiceImpl;
import com.cuenta.contador.service.user.UserService;
import com.cuenta.contador.service.user.UserServiceImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class ServiceBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(CredentialServiceImpl.class).to(CredentialService.class);
        bind(SessionServiceImpl.class).to(SessionService.class);
        bind(AuthenticationServiceImpl.class).to(AuthenticationService.class);
        bind(UserServiceImpl.class).to(UserService.class);
        bind(ContextService.class).to(ContextService.class);
        bind(AccountServiceImpl.class).to(AccountService.class);
    }
}
