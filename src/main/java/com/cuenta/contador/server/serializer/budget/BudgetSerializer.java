package com.cuenta.contador.server.serializer.budget;

import com.cuenta.contador.server.json.budget.BudgetAllocationJson;
import com.cuenta.contador.server.json.budget.BudgetJson;
import com.cuenta.contador.service.budget.Budget;
import com.cuenta.contador.service.budget.Budget.BudgetID;
import com.cuenta.contador.service.budget.BudgetAllocation;

import java.util.ArrayList;
import java.util.List;

public class BudgetSerializer {

  // turns BudgetJson -> Budget
  public Budget fromBudgetJson(BudgetJson budgetJson){
    return new Budget(
      budgetJson.getId() == null ? null : BudgetID.of(budgetJson.getId()),
      budgetJson.getYear(),
      budgetJson.getMonthNumber(),
      budgetJson.getTotalAmount()
    );
  }

  // turns BudgetAllocationJson -> BudgetAllocation
  public BudgetAllocation fromBudgetAllocationJson(BudgetAllocationJson budgetAllocationJson){
    return new BudgetAllocation(
        budgetAllocationJson.getCategory(),
        budgetAllocationJson.getAmount()
    );
  }

  // turns Budget -> BudgetJson
  public BudgetJson toJson(Budget budget){
    BudgetJson budgetJson = new BudgetJson();
    budgetJson.setId(budget.getBudgetId().getIntId());
    budgetJson.setYear(budget.getYear());
    budgetJson.setMonthNumber(budget.getMonthNumber());
    budgetJson.setTotalAmount(budget.getTotalAmount());
    return budgetJson;
  }

  // turns BudgetAllocation -> BudgetAllocationJson
  public BudgetAllocationJson toBudgetAllocationJson(BudgetAllocation budgetAllocation){
    BudgetAllocationJson budgetAllocationJson = new BudgetAllocationJson();
    budgetAllocationJson.setCategory(budgetAllocation.getCategory());
    budgetAllocationJson.setAmount(budgetAllocation.getAmount());
    return budgetAllocationJson;
  }

  // turns List<BudgetAllocationJson> -> List<BudgetAllocation>
  public List<BudgetAllocationJson> fromListOfBudgetAllocationJson(List<BudgetAllocation> budgetAllocationList){
    List<BudgetAllocationJson> listOfBudgetAllocationJson = new ArrayList<>();
    budgetAllocationList.forEach(budgetAllocation -> {
      listOfBudgetAllocationJson.add(toBudgetAllocationJson(budgetAllocation));
    });
    return listOfBudgetAllocationJson;
  }
}
