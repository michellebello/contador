package com.cuenta.contador.server.resource.transaction;

import com.cuenta.contador.server.json.transaction.TransactionJson;
import com.cuenta.contador.server.serializer.transaction.TransactionSerializer;
import com.cuenta.contador.service.transaction.Transaction;
import com.cuenta.contador.service.transaction.Transaction.TransactionID;
import com.cuenta.contador.service.transaction.TransactionService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("transactions")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TransactionResource {
    private static final String TRANSACTION_ID = "transactionId";

    private final TransactionService transactionService;
    private final TransactionSerializer transactionSerializer;

    @Inject
    public TransactionResource(TransactionService transactionService, TransactionSerializer transactionSerializer) {
        this.transactionService = transactionService;
        this.transactionSerializer = transactionSerializer;
    }

    @POST
    public Response storeTransaction(TransactionJson transactionJson){
        System.out.println("Received JSON: " + transactionJson);
        Transaction transaction = TransactionSerializer.fromTransactionJson(transactionJson);
        transactionService.storeTransaction(transaction);
        return Response.ok().build();
    }

    @GET
    @Path("{"+ TRANSACTION_ID +"}")
    public TransactionJson getTransaction(@PathParam(TRANSACTION_ID) int transaction_id){
        Transaction transaction = transactionService.getTransaction(TransactionID.of(transaction_id));
        return TransactionSerializer.toTransactionJson(transaction);
    }

    @GET
    public List<Transaction> getTransactions(){
        return List.of();
    }
}
