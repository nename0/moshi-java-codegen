package io.github.nename0.moshi.java.codegen.model.simple;

import io.github.nename0.moshi.java.codegen.model.TestingModel;

import java.util.Objects;

public class ParentString extends TestingModel {
    public String name;

    @Override
    public void fillRandomData() {
        name = randomString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParentString that = (ParentString) o;
        return Objects.equals(name, that.name);
    }
}
