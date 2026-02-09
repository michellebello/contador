package com.cuenta.contador.infra;

import org.jetbrains.annotations.NotNull;
import org.jooq.Converter;

public class BooleanConverter implements Converter<Byte, Boolean> {

  @Override
  public Boolean from(Byte databaseObject) {
    return databaseObject != null && databaseObject == 1;
  }

  @Override
  public Byte to(Boolean userObject) {
    return userObject != null && userObject ? (byte) 1 : (byte) 0;
  }

  @Override
  public @NotNull Class<Byte> fromType() {
    return Byte.class;
  }

  @Override
  public @NotNull Class<Boolean> toType() {
    return Boolean.class;
  }
}