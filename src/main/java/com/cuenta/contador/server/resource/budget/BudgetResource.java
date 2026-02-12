package com.cuenta.contador.server.resource.budget;

import com.cuenta.contador.server.json.budget.BudgetAllocationJson;
import com.cuenta.contador.server.json.budget.BudgetJson;
import com.cuenta.contador.server.serializer.budget.BudgetSerializer;
import com.cuenta.contador.service.budget.Budget;
import com.cuenta.contador.service.budget.Budget.BudgetID;
import com.cuenta.contador.service.budget.BudgetAllocation;
import com.cuenta.contador.service.budget.BudgetService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

@Path("budgets")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BudgetResource {
  private final BudgetSerializer budgetSerializer;
  private final BudgetService budgetService;

  @Inject
  public BudgetResource(BudgetSerializer budgetSerializer, BudgetService budgetService){
    this.budgetSerializer = budgetSerializer;
    this.budgetService = budgetService;
  }

  @POST
  public Response storeBudget(BudgetJson budgetJson){
    Budget budget = budgetSerializer.fromBudgetJson(budgetJson);
    budgetService.storeBudget(budget);
    return Response.status(Response.Status.CREATED).build();
  }

  @POST
  @Path("/{budgetId}/allocations")
  public Response storeBudgetAllocations(@PathParam("budgetId") int budgetId,
    List<BudgetAllocationJson> budgetAllocationJsonList){
    List<BudgetAllocation> budgetAllocationList = new ArrayList<>();
    budgetAllocationJsonList.forEach(budgetAllocationJson -> {
      budgetAllocationList.add(budgetSerializer.fromBudgetAllocationJson(budgetAllocationJson));
    });
    budgetService.storeBudgetAllocations(BudgetID.of(budgetId), budgetAllocationList);
    return Response.status(Response.Status.CREATED).build();
  }

  @GET
  @Path("/{year}/{monthNum}")
  public Response getBudget(
    @PathParam("year") int year,
    @PathParam("monthNum") byte monthNum
  ) {
    Budget budget = budgetService.getBudget(year, monthNum);
    BudgetJson budgetJson = budgetSerializer.toJson(budget);
    return Response.ok(budgetJson).build();
  }

  @GET
  @Path("/{budgetId}/allocations")
  public List<BudgetAllocationJson> getBudgetAllocations(@PathParam("budgetId") int budgetId){
    List<BudgetAllocation> budgetAllocations = budgetService.getBudgetAllocations(BudgetID.of(budgetId));
    List<BudgetAllocationJson> budgetAllocationJson = new ArrayList<>();
    budgetAllocations.forEach(budgetAllocation -> {
      budgetAllocationJson.add(budgetSerializer.toBudgetAllocationJson(budgetAllocation));
    });
    return budgetAllocationJson;
  }
}
