package com.cuenta.contador.infra.temporal;

import com.cuenta.contador.infra.Pair;

import java.time.LocalDate;

public class Interval {

  public Pair<LocalDate, LocalDate> getIntervalBefore(int count, Unit unit, LocalDate endDate) {
    LocalDate startDate = unit.before(endDate, count);
    return new Pair<>(startDate, endDate);
  }

  public Pair<LocalDate, LocalDate> getIntervalAfter(int count, Unit unit, LocalDate startDate) {
    LocalDate endDate = unit.after(startDate, count);
    return new Pair<>(startDate, endDate);
  }
}
