package com.cuenta.contador.server.resource.transaction;

import com.cuenta.contador.server.json.transaction.PaginatedTransactionJson;
import com.cuenta.contador.server.json.transaction.TaxableTransactionJson;
import com.cuenta.contador.server.json.transaction.TaxableTransactionNoteUpdateJson;
import com.cuenta.contador.server.json.transaction.TransactionJson;
import com.cuenta.contador.server.serializer.transaction.TransactionSerializer;
import com.cuenta.contador.service.coordinator.transaction.TransactionCoordinatorService;
import com.cuenta.contador.service.transaction.*;
import com.cuenta.contador.service.transaction.Transaction.TransactionID;

import jakarta.inject.Inject;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jooq.impl.QOM;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Path("transactions")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TransactionResource {
    private static final String TRANSACTION_ID = "transactionId";
    private final TransactionService transactionService;
    private final TransactionCoordinatorService transactionCoordinatorService;
    private final TransactionSerializer transactionSerializer;

    @Inject
    public TransactionResource(TransactionService transactionService, TransactionCoordinatorService transactionCoordinatorService, TransactionSerializer transactionSerializer) {
        this.transactionService = transactionService;
        this.transactionCoordinatorService = transactionCoordinatorService;
        this.transactionSerializer = transactionSerializer;
    }

    @POST
    public Response storeTransaction(TransactionJson transactionJson){
        Transaction transaction = transactionSerializer.fromTransactionJson(transactionJson);
        transactionCoordinatorService.createAndProcessTransaction(transaction);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("{"+ TRANSACTION_ID +"}")
    public Response getTransaction(@PathParam(TRANSACTION_ID) int transactionId){
        Transaction transaction = transactionService.getTransaction(TransactionID.of(transactionId));
        if (transaction != null){
            TransactionJson transactionJson = transactionSerializer.toJoinedTransactionJson(transaction);
            return Response.ok(transactionJson).build();
        } else {
            String message = "Transaction with id " + transactionId + " not found";
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @GET
    public PaginatedTransactionJson getTransactions(
            @QueryParam("after") String afterString,
            @QueryParam("before") String beforeString,
            @QueryParam("cursor") int cursor
    ) {
        afterString = (afterString != null && !afterString.isEmpty())? afterString.trim() : null;
        LocalDate after = afterString != null? LocalDate.parse(afterString) : LocalDate.of(2025, 1, 1);

        beforeString = (beforeString != null && !beforeString.isEmpty()) ? beforeString.trim() : null;
        LocalDate before = beforeString != null? LocalDate.parse(beforeString) : LocalDate.now();

        PaginatedTransaction paginatedTransaction = transactionService.getPaginatedTransactions(List.of(), after, before, cursor);
        return transactionSerializer.toPaginatedTransactionJson(paginatedTransaction);
    }

    @GET
    @Path("/chart")
    public Map<String, Double> getTransactionsForChart(
      @QueryParam("after") String afterString,
      @QueryParam("before") String beforeString
    ) {
        afterString = (afterString != null && !afterString.isEmpty())? afterString.trim() : null;
        LocalDate after = afterString != null? LocalDate.parse(afterString) : LocalDate.of(2025, 1, 1);

        beforeString = (beforeString != null && !beforeString.isEmpty()) ? beforeString.trim() : null;
        LocalDate before = beforeString != null? LocalDate.parse(beforeString) : LocalDate.now();
        return transactionService.getTransactionBreakdown(after, before);
    }

    @GET
    @Path("/taxable")
    public List<TaxableTransactionJson> getTaxableTransactions(
      @QueryParam("month") Integer month,
      @QueryParam("year") Integer year,
      @QueryParam("category") String category
    ){
        int yearFilter = (year != null) ? year: LocalDateTime.now().getYear();

        LocalDate monthStart;
        LocalDate monthEnd;

        if (month != null) {
            monthStart = LocalDate.from(LocalDate.of(yearFilter, month, 1).atStartOfDay());
            monthEnd = LocalDate.from(monthStart.plusMonths(1).atStartOfDay());
        } else {
            monthStart = LocalDate.from(LocalDate.of(yearFilter, 1, 1).atStartOfDay());
            monthEnd = LocalDate.from(monthStart.plusYears(1).atStartOfDay());
        }

        String categoryFilter = (category != null)? category : "";
        List<TaxableTransaction> transactions = transactionService.getTaxableTransactions(monthStart, monthEnd, categoryFilter);
        return transactions.stream().map(transactionSerializer::toTaxableTransactionJson).toList();
    }

    @POST
    @Path("/taxable/")
    public Response addTaxableTransactionNote(TaxableTransactionNoteUpdateJson noteUpdateJson) {
        if (noteUpdateJson ==  null){
            return Response.status(Response.Status.NO_CONTENT).entity("No data provided").build();
        }
        TaxableTransactionNoteUpdate noteUpdate = transactionSerializer.fromTaxableTransactionNoteUpdateJson(noteUpdateJson);
        try {
            transactionService.addTransactionNote(noteUpdate);
            return Response.ok("Note added").build();
        } catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error, try again").build();
        }
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
            transactionCoordinatorService.updateTransaction(TransactionID.of(transactionId), transactionToUpdate);
            return Response.ok("Transaction successfully updated.").build();
        } catch (Exception e){
            System.out.println(e.getMessage());
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
            transactionCoordinatorService.deleteTransaction(TransactionID.of(transactionId));
            return Response.ok("Successfully deleted transaction").build();
        } catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Server error try again").build();
        }
    }
}
