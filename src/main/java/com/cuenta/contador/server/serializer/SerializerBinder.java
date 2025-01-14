package com.cuenta.contador.server.serializer;

import com.cuenta.contador.server.serializer.account.AccountSerializer;
import com.cuenta.contador.server.serializer.credential.AuthenticationSerializer;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class SerializerBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(AuthenticationSerializer.class).to(AuthenticationSerializer.class);
        bind(AccountSerializer.class).to(AccountSerializer.class);
    }
}
