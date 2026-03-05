package com.cuenta.contador.service.coordinator.transaction;

import com.cuenta.contador.service.transaction.Transaction;
import com.cuenta.contador.service.transaction.Transaction.TransactionID;

public interface TransactionCoordinatorService {
  void createAndProcessTransaction(Transaction transaction);
  void updateTransaction(TransactionID transactionId, Transaction transaction);
  void deleteTransaction(TransactionID transactionId);
}
