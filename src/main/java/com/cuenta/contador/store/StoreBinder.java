package com.cuenta.contador.store;

import com.cuenta.contador.store.credential.CredentialStore;
import com.cuenta.contador.store.session.SessionStore;
import com.cuenta.contador.store.user.UserStore;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class StoreBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(CredentialStore.class).to(CredentialStore.class);
        bind(SessionStore.class).to(SessionStore.class);
        bind(UserStore.class).to(UserStore.class);
    }
}
