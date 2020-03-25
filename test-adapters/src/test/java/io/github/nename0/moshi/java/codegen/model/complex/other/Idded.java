package io.github.nename0.moshi.java.codegen.model.complex.other;

import io.github.nename0.moshi.java.codegen.model.TestingModel;

import java.util.Objects;

public abstract class Idded<J> extends TestingModel {
    protected J id;

    @Override
    @SuppressWarnings("unchecked")
    public void fillRandomData() {
        // always called with T == String
        id = (J) randomString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Idded<?> idded = (Idded<?>) o;
        return Objects.equals(id, idded.id);
    }
}
