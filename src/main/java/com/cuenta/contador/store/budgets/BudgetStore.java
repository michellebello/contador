package com.cuenta.contador.store.budgets;

import com.cuenta.contador.infra.DSLContextProvider;
import com.cuenta.contador.jooq_auto_generated.tables.records.BudgetRecord;
import com.cuenta.contador.service.budget.Budget;
import com.cuenta.contador.service.user.User.UserID;
import jakarta.inject.Inject;
import org.jooq.DSLContext;

import static com.cuenta.contador.jooq_auto_generated.Tables.BUDGET;

public class BudgetStore {
  private final DSLContext db;

  public BudgetStore(DSLContextProvider dbProvider){
    this.db = dbProvider.get();
  }

  @Inject
  public void storeBudget(UserID userId, Budget budget){
    db.insertInto(BUDGET)
      .set(BUDGET.USER_ID, userId.getIntId())
      .set(BUDGET.MONTH_NUM, budget.getMonthNumber())
      .set(BUDGET.YEAR, budget.getYear())
      .set(BUDGET.TOTAL_AMOUNT, budget.getTotalAmount())
      .execute();
    // insert budget_allocations here
  }

  public Budget getBudget(UserID userId, int year, byte month){
    BudgetRecord record = db.selectFrom(BUDGET)
      .where(BUDGET.USER_ID.eq(userId.getIntId()))
      .and(BUDGET.YEAR.eq(year))
      .and(BUDGET.MONTH_NUM.eq(month))
      .fetchOne();
    return record != null? fromRecord(record): null;
  }

//  public Budget updateBudget()
//
//  public void deleteBudget(){}

  private Budget fromRecord(BudgetRecord budgetRecord){
    return new Budget(
      Budget.BudgetID.of(budgetRecord.get(BUDGET.ID)),
      UserID.of(budgetRecord.get(BUDGET.USER_ID)),
      budgetRecord.get(BUDGET.YEAR),
      budgetRecord.get(BUDGET.MONTH_NUM),
      budgetRecord.get(BUDGET.TOTAL_AMOUNT)
    );
  }
}
