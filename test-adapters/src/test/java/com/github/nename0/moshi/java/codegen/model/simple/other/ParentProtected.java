package com.github.nename0.moshi.java.codegen.model.simple.other;

import com.github.nename0.moshi.java.codegen.model.TestingModel;

public class ParentProtected extends TestingModel {
    protected double coordinates;

    @Override
    public void fillRandomData() {
        coordinates = RNG.nextDouble();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParentProtected that = (ParentProtected) o;
        return coordinates == that.coordinates;
    }
}
