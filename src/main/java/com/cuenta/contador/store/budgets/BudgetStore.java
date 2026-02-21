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

import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.cuenta.contador.jooq_auto_generated.Tables.BUDGET;
import static com.cuenta.contador.jooq_auto_generated.Tables.BUDGET_ALLOCATION;
import static org.jooq.impl.DSL.all;
import static org.jooq.impl.DSL.sum;

public class BudgetStore {
  private final DSLContext db;

  @Inject
  public BudgetStore(DSLContextProvider dbProvider){
    this.db = dbProvider.get();
  }

  public boolean budgetExists(BudgetID budgetId){
    return db.selectFrom(BUDGET).where(BUDGET.ID.eq(budgetId.getIntId())).fetchOne() != null;
  }

  private boolean budgetAllocationExists(BudgetID budgetId, int allocationId){
    return db.selectFrom(BUDGET_ALLOCATION)
      .where(BUDGET_ALLOCATION.BUDGET_ID.eq(budgetId.getIntId()))
      .and(BUDGET_ALLOCATION.ID.eq(allocationId))
      .fetchOne() != null;
  }

  public void storeBudget(UserID userId, Budget budget){
    db.insertInto(BUDGET)
      .set(BUDGET.USER_ID, userId.getIntId())
      .set(BUDGET.MONTH_NUM, budget.getMonthNumber())
      .set(BUDGET.YEAR, budget.getYear())
      .set(BUDGET.TOTAL_AMOUNT, budget.getTotalAmount())
      .set(BUDGET.TOTAL_SPENT, 0.0)
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
      record.set(BUDGET_ALLOCATION.SPENT, allocation.getSpent());
      records.add(record);
    });
    db.batchInsert(records).execute();
  }

  public List<Budget> getAllBudgets(UserID userId) {
    return db.selectFrom(BUDGET)
      .where(BUDGET.USER_ID.eq(userId.getIntId()))
      .orderBy(BUDGET.YEAR.desc(),
        BUDGET.MONTH_NUM.desc())
      .fetch()
      .map(this::fromRecord);
  }

  public Budget getBudget(UserID userId, int year, byte month){
    BudgetRecord record = db.selectFrom(BUDGET)
      .where(BUDGET.USER_ID.eq(userId.getIntId()))
      .and(BUDGET.YEAR.eq(year))
      .and(BUDGET.MONTH_NUM.eq(month))
      .fetchOne();
    return record != null? fromRecord(record): null;
  }

  public Double getBudgetSpent(UserID userId, BudgetID budgetId){
    Double spent = db.select(sum(BUDGET_ALLOCATION.AMOUNT))
      .from(BUDGET_ALLOCATION)
      .where(BUDGET_ALLOCATION.BUDGET_ID.eq(budgetId.getIntId()))
      .fetchOne(0, Double.class);
    spent = spent != null? spent : 0.0;
    return spent;
  }

  public List<BudgetAllocation> getBudgetAllocations(Budget.BudgetID budgetID) {
    return db.selectFrom(BUDGET_ALLOCATION)
      .where(BUDGET_ALLOCATION.BUDGET_ID.eq(budgetID.getIntId()))
      .fetchInto(BudgetAllocation.class);
  }

  public Map<BudgetID, List<BudgetAllocation>> getAllBudgetAllocations(UserID userId) {
    return db.select(BUDGET_ALLOCATION.ID, BUDGET_ALLOCATION.BUDGET_ID, BUDGET_ALLOCATION.CATEGORY, BUDGET_ALLOCATION.AMOUNT, BUDGET_ALLOCATION.SPENT)
      .from(BUDGET_ALLOCATION)
      .join(BUDGET).on(BUDGET.ID.eq(BUDGET_ALLOCATION.BUDGET_ID))
      .where(BUDGET.USER_ID.eq(userId.getIntId()))
      .fetchGroups(
        record -> new BudgetID(record.get(BUDGET_ALLOCATION.BUDGET_ID)),
        record -> new BudgetAllocation(
          record.get(BUDGET_ALLOCATION.ID),
          BudgetID.of(record.get(BUDGET_ALLOCATION.BUDGET_ID)),
          record.get(BUDGET_ALLOCATION.CATEGORY),
          record.get(BUDGET_ALLOCATION.AMOUNT),
          record.get(BUDGET_ALLOCATION.SPENT))
      );
  }
  public Budget getCurrentBudget(UserID userId) {
    BudgetID budgetId = getCurrentBudgetId(userId);
    BudgetRecord budgetRecord = db.selectFrom(BUDGET)
      .where(BUDGET.ID.eq(budgetId.getIntId()))
      .fetchOne();
    return budgetRecord != null? fromRecord(budgetRecord) : null;
  }

  public BudgetID getBudgetId(UserID userId, LocalDateTime createdOn){
    byte monthNum = (byte)createdOn.getMonthValue();
    int year = createdOn.getYear();

    Integer budgetId = db.select(BUDGET.ID)
      .from(BUDGET)
      .where(BUDGET.USER_ID.eq(userId.getIntId()))
      .and(BUDGET.MONTH_NUM.eq(monthNum))
      .and(BUDGET.YEAR.eq(year))
      .fetchOne(BUDGET.ID);
    return budgetId != null? BudgetID.of(budgetId) : null;
  }

  public BudgetID getCurrentBudgetId(UserID userId){
    YearMonth currMonth = YearMonth.now();
    byte monthNum = (byte) currMonth.getMonthValue();
    int year = currMonth.getYear();
    Integer budgetId = db.select(BUDGET.ID)
      .from(BUDGET)
      .where(BUDGET.USER_ID.eq(userId.getIntId()))
      .and(BUDGET.YEAR.eq(year))
      .and(BUDGET.MONTH_NUM.eq(monthNum))
      .fetchOne(BUDGET.ID);
    return budgetId != null ? BudgetID.of(budgetId) : null;
  }

  public boolean budgetCategoryExists(UserID userId, String category){
    BudgetID budgetId = getCurrentBudgetId(userId);
    return db.selectFrom(BUDGET_ALLOCATION)
      .where(BUDGET_ALLOCATION.BUDGET_ID.eq(budgetId.getIntId()))
      .and(BUDGET_ALLOCATION.CATEGORY.eq(category))
      .fetchOne() != null;
  }

  public void updateBudgetAllocation(BudgetID budgetId, String category, double transactionAmount){
    db.update(BUDGET_ALLOCATION)
      .set(BUDGET_ALLOCATION.SPENT, BUDGET_ALLOCATION.SPENT.plus(transactionAmount))
      .where(BUDGET_ALLOCATION.BUDGET_ID.eq(budgetId.getIntId()))
      .and(BUDGET_ALLOCATION.CATEGORY.eq(category))
      .execute();
  }

  public void updateBudgetAllocationTotal(BudgetID budgetId, Integer allocationId, Double allocationTotal) throws Exception {
    Double currTotal = db.select(BUDGET_ALLOCATION.AMOUNT)
      .from(BUDGET_ALLOCATION)
      .where(BUDGET_ALLOCATION.BUDGET_ID.eq(budgetId.getIntId()))
      .and(BUDGET_ALLOCATION.ID.eq(allocationId))
      .fetchOne(BUDGET_ALLOCATION.AMOUNT);

    //TO DO: do we need this?
    if (currTotal == null){
      throw new Exception("Current total not found");
    }

    double difference = currTotal - allocationTotal;

    db.update(BUDGET_ALLOCATION)
      .set(BUDGET_ALLOCATION.AMOUNT, allocationTotal)
      .where(BUDGET_ALLOCATION.BUDGET_ID.eq(budgetId.getIntId()))
      .and(BUDGET_ALLOCATION.ID.eq(allocationId))
      .execute();
    if (difference > 0){
      updateBudgetTotal(budgetId, false, difference);
    } else {
      difference *= -1;
      updateBudgetTotal(budgetId, true, difference );
    }
  }

  public void updateBudgetTotal(BudgetID budgetId, boolean add, double difference){
    if (add) {
      db.update(BUDGET)
        .set(BUDGET.TOTAL_AMOUNT, BUDGET.TOTAL_AMOUNT.plus(difference))
        .where(BUDGET.ID.eq(budgetId.getIntId()))
        .execute();
    } else {
      db.update(BUDGET)
        .set(BUDGET.TOTAL_AMOUNT, BUDGET.TOTAL_AMOUNT.minus(difference))
        .where(BUDGET.ID.eq(budgetId.getIntId()))
        .execute();
    }
  }

  public void updateBudgetSpent(BudgetID budgetId, double transactionAmount) {
    db.update(BUDGET)
      .set(BUDGET.TOTAL_SPENT, BUDGET.TOTAL_SPENT.plus(transactionAmount))
      .where(BUDGET.ID.eq(budgetId.getIntId()))
      .execute();
  }

  public void deleteBudgetAllocation(BudgetID budgetId, int allocationId) throws Exception {
    if (!budgetAllocationExists(budgetId, allocationId)){
      throw new Exception("Budget allocation does not exist");
    }
    db.deleteFrom(BUDGET_ALLOCATION)
      .where(BUDGET_ALLOCATION.BUDGET_ID.eq(budgetId.getIntId()))
      .and(BUDGET_ALLOCATION.ID.eq(allocationId))
      .execute();
  }

  private Budget fromRecord(BudgetRecord budgetRecord){
    return new Budget(
      Budget.BudgetID.of(budgetRecord.get(BUDGET.ID)),
      budgetRecord.get(BUDGET.YEAR),
      budgetRecord.get(BUDGET.MONTH_NUM),
      budgetRecord.get(BUDGET.TOTAL_AMOUNT),
      budgetRecord.get(BUDGET.TOTAL_SPENT)
    );
  }
}
