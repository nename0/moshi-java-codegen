package io.github.nename0.moshi.java.codegen.model.complex;

import io.github.nename0.moshi.java.codegen.model.complex.other.Idded;

public class GenericMiddleGetter<K> extends Idded<K> {
    public K getId() {
        return id;
    }

    public void setId(K id) {
        this.id = id;
    }
}
