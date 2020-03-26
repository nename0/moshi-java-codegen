package com.github.nename0.moshi.java.codegen.model.simple;

import com.github.nename0.moshi.java.codegen.model.TestingModel;
import com.squareup.moshi.JsonClass;

import java.util.Objects;

@JsonClass(generateAdapter = true, generator = "java")
public class GenericField<T> extends TestingModel {
    public T generic;

    @Override
    @SuppressWarnings("unchecked")
    public void fillRandomData() {
        // always called with T == String
        generic = (T) randomString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenericField<?> that = (GenericField<?>) o;
        return Objects.equals(generic, that.generic);
    }
}
