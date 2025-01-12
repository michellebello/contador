package com.cuenta.contador.server.serializer;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class SerializerBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(AuthenticationSerializer.class).to(AuthenticationSerializer.class);
    }
}
