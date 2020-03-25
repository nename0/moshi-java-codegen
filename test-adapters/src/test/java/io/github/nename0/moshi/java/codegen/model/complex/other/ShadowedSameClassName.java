package io.github.nename0.moshi.java.codegen.model.complex.other;

import io.github.nename0.moshi.java.codegen.model.TestingModel;

public class ShadowedSameClassName extends TestingModel {
    public int same;

    @Override
    public void fillRandomData() {
        same = RNG.nextInt();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShadowedSameClassName that = (ShadowedSameClassName) o;
        return same == that.same;
    }
}
