package com.cuenta.contador.server.resource.transaction;

import com.cuenta.contador.server.json.transaction.TransactionJson;
import com.cuenta.contador.server.serializer.transaction.TransactionSerializer;
import com.cuenta.contador.service.transaction.Transaction;
import com.cuenta.contador.service.transaction.Transaction.TransactionID;
import com.cuenta.contador.service.transaction.TransactionService;

import jakarta.inject.Inject;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;
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
        Transaction transaction = transactionSerializer.fromTransactionJson(transactionJson);
        transactionService.storeTransaction(transaction);
        return Response.ok(transaction).build();
    }

    @GET
    @Path("{"+ TRANSACTION_ID +"}")
    public Response getTransaction(@PathParam(TRANSACTION_ID) int transactionId){
        Transaction transaction = transactionService.getTransaction(TransactionID.of(transactionId));
        if (transaction != null){
            TransactionJson transactionJson = transactionSerializer.toTransactionJson(transaction);
            return Response.ok(transactionJson).build();
        } else {
            String message = "Transaction with id " + transactionId + " not found";
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @GET
    public List<TransactionJson> getTransactions(
            @QueryParam("after") String afterString,
            @QueryParam("before") String beforeString
    ) {
        // make sure no whitespace
        afterString = afterString != null? afterString.trim() : null;
        LocalDate after = afterString != null? LocalDate.parse(afterString) : LocalDate.of(2025, 1, 1);

        beforeString = beforeString != null? beforeString.trim() : null;
        LocalDate before = beforeString != null? LocalDate.parse(beforeString) : LocalDate.now();

        List<Transaction> transactions = transactionService.getTransactions(List.of(), after, before);

        return transactions.stream().map(transactionSerializer::toJoinedTransactionJson).toList();
    }


    @PATCH
    @Path("{" + TRANSACTION_ID + "}")
    public Response updateTransaction(@PathParam(TRANSACTION_ID) int transactionId, TransactionJson transactionJson){
        if (transactionJson == null){
            return Response.status(Response.Status.BAD_REQUEST).entity("Transaction cannot be update with any data, please provide data to update transaction.").build();
        }
        Transaction currTransaction = transactionService.getTransaction(TransactionID.of(transactionId));
        if (currTransaction == null){
            return Response.status(Response.Status.NOT_FOUND).entity("Transaction with id " + transactionId + " not found").build();
        }
        Transaction transactionToUpdate = transactionSerializer.fromPartialTransactionJson(transactionJson, currTransaction);
        try {
            transactionService.updateTransaction(TransactionID.of(transactionId), transactionToUpdate);
            return Response.ok("Transaction successfully updated.").build();
        } catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error, try again").build();
        }
    }

    @DELETE
    @Path("{"+ TRANSACTION_ID +"}")
    public Response deleteTransaction(@PathParam(TRANSACTION_ID) int transactionId){
        Transaction transactionToDelete = transactionService.getTransaction(TransactionID.of(transactionId));
        if (transactionToDelete == null){
            return Response.status(Response.Status.BAD_REQUEST).entity("Transaction with id " + transactionId + " not found.").build();
        }
        try {
            transactionService.deleteTransaction(TransactionID.of(transactionId));
            return Response.ok("Successfully deleted transaction").build();
        } catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Server error try again").build();
        }
    }
}
