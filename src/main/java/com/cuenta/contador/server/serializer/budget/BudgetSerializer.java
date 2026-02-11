package com.cuenta.contador.server.serializer.budget;

import com.cuenta.contador.server.json.budget.BudgetAllocationJson;
import com.cuenta.contador.server.json.budget.BudgetJson;
import com.cuenta.contador.service.budget.Budget;
import com.cuenta.contador.service.budget.BudgetAllocation;
import com.cuenta.contador.service.user.User;

import java.util.List;

public class BudgetSerializer {
  public Budget fromBudgetJson(BudgetJson budgetJson){
    return new Budget(
      budgetJson.getId() == null ? null : Budget.BudgetID.of(budgetJson.getId()),
      User.UserID.of(budgetJson.getUserId()),
      budgetJson.getYear(),
      budgetJson.getMonthNumber(),
      budgetJson.getTotalAmount()
    );
  }

  public BudgetJson toJoinedBudgetJson(Budget budget, List<BudgetAllocationJson> listOfAllocationJson){
    BudgetJson budgetJson = new BudgetJson();
    budgetJson.setId(budgetJson.getId());
    budgetJson.setUserId(budgetJson.getUserId());
    budgetJson.setYear(budgetJson.getYear());
    budgetJson.setMonthNumber(budgetJson.getMonthNumber());
    budgetJson.setBudgetAllocationJsonList(listOfAllocationJson);
    return budgetJson;
  }
}
