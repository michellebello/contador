package com.cuenta.contador.store.budgets;

import com.cuenta.contador.infra.DSLContextProvider;
import com.cuenta.contador.jooq_auto_generated.tables.records.BudgetAllocationRecord;
import com.cuenta.contador.jooq_auto_generated.tables.records.BudgetRecord;
import com.cuenta.contador.service.budget.Budget.BudgetID;
import com.cuenta.contador.service.budget.Budget;
import com.cuenta.contador.service.budget.BudgetAllocation;
import com.cuenta.contador.service.user.User.UserID;
import jakarta.inject.Inject;
import org.jooq.DSLContext;
import org.jooq.Query;

import java.util.ArrayList;
import java.util.List;

import static com.cuenta.contador.jooq_auto_generated.Tables.BUDGET;
import static com.cuenta.contador.jooq_auto_generated.Tables.BUDGET_ALLOCATION;

public class BudgetStore {
  private final DSLContext db;

  @Inject
  public BudgetStore(DSLContextProvider dbProvider){
    this.db = dbProvider.get();
  }

  public boolean budgetExists(BudgetID budgetId){
    return db.selectFrom(BUDGET).where(BUDGET.ID.eq(budgetId.getIntId())).fetchOne() != null;
  }

  public void storeBudget(UserID userId, Budget budget){
    db.insertInto(BUDGET)
      .set(BUDGET.USER_ID, userId.getIntId())
      .set(BUDGET.MONTH_NUM, budget.getMonthNumber())
      .set(BUDGET.YEAR, budget.getYear())
      .set(BUDGET.TOTAL_AMOUNT, budget.getTotalAmount())
      .execute();
  }

  public void storeBudgetAllocations(BudgetID budgetId, List<BudgetAllocation> budgetAllocations){
    if (!budgetExists(budgetId)){
      throw new Error("BudgetId does not exists in db");
    }
    List<BudgetAllocationRecord> records = new ArrayList<>();
    budgetAllocations.forEach(allocation -> {
      BudgetAllocationRecord record = db.newRecord(BUDGET_ALLOCATION);
      record.set(BUDGET_ALLOCATION.BUDGET_ID, budgetId.getIntId());
      record.set(BUDGET_ALLOCATION.CATEGORY, allocation.getCategory());
      record.set(BUDGET_ALLOCATION.AMOUNT, allocation.getAmount());
      records.add(record);
    });
    db.batchInsert(records).execute();
  }

  public Budget getBudget(UserID userId, int year, byte month){
    BudgetRecord record = db.selectFrom(BUDGET)
      .where(BUDGET.USER_ID.eq(userId.getIntId()))
      .and(BUDGET.YEAR.eq(year))
      .and(BUDGET.MONTH_NUM.eq(month))
      .fetchOne();
    return record != null? fromRecord(record): null;
  }

  public List<BudgetAllocation> getBudgetAllocations(Budget.BudgetID budgetID){
    return db.selectFrom(BUDGET_ALLOCATION)
      .where(BUDGET_ALLOCATION.BUDGET_ID.eq(budgetID.getIntId()))
      .fetchInto(BudgetAllocation.class);
  }

//  public Budget updateBudget()
//
//  public void deleteBudget(){}

  private Budget fromRecord(BudgetRecord budgetRecord){
    return new Budget(
      Budget.BudgetID.of(budgetRecord.get(BUDGET.ID)),
      budgetRecord.get(BUDGET.YEAR),
      budgetRecord.get(BUDGET.MONTH_NUM),
      budgetRecord.get(BUDGET.TOTAL_AMOUNT)
    );
  }
}
