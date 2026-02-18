package com.cuenta.contador.infra.temporal;

import java.time.LocalDate;

public enum Unit {
  DAY {
    @Override
    public LocalDate after(LocalDate date, int count) {
      return date.plusDays(count);
    }

    @Override
    public LocalDate before(LocalDate date, int count) {
      return date.minusDays(count);
    }
  },
  WEEK {
    @Override
    public LocalDate after(LocalDate date, int count) {
      return date.plusWeeks(count);
    }

    @Override
    public LocalDate before(LocalDate date, int count) {
      return date.minusWeeks(count);
    }
  },
  MONTH {
    @Override
    public LocalDate after(LocalDate date, int count) {
      return date.plusMonths(count);
    }

    @Override
    public LocalDate before(LocalDate date, int count) {
      return date.minusMonths(count);
    }
  },
  YEAR {
    @Override
    public LocalDate after(LocalDate date, int count) {
      return date.plusYears(count);
    }

    @Override
    public LocalDate before(LocalDate date, int count) {
      return date.minusYears(count);
    }
  };

  public abstract LocalDate after(LocalDate date, int count);
  public abstract LocalDate before(LocalDate date, int count);
}
