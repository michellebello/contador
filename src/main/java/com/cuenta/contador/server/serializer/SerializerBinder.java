package com.cuenta.contador.server.serializer;

import com.cuenta.contador.server.serializer.account.AccountSerializer;
import com.cuenta.contador.server.serializer.budget.BudgetSerializer;
import com.cuenta.contador.server.serializer.credential.AuthenticationSerializer;
import com.cuenta.contador.server.serializer.transaction.TransactionSerializer;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class SerializerBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(AuthenticationSerializer.class).to(AuthenticationSerializer.class);
        bind(AccountSerializer.class).to(AccountSerializer.class);
        bind(TransactionSerializer.class).to(TransactionSerializer.class);
        bind(BudgetSerializer.class).to(BudgetSerializer.class);
    }
}
