package com.cuenta.contador.infra;

import java.io.Serializable;
import java.util.Objects;

public abstract class ID<T> implements Serializable {
    public int id;

    public ID(int id) {
        this.id = id;
    }

    public int getIntId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ID<?> id1 = (ID<?>) o;
        return getIntId() == id1.getIntId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIntId());
    }
}
