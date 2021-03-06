package com.github.nename0.moshi.java.codegen.model.simple;

import com.github.nename0.moshi.java.codegen.model.TestingModel;
import com.squareup.moshi.JsonClass;

@JsonClass(generateAdapter = true, generator = "java")
public class SingleFieldInt extends TestingModel {

    public int anInt;

    @Override
    public void fillRandomData() {
        anInt = RNG.nextInt();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SingleFieldInt that = (SingleFieldInt) o;

        return anInt == that.anInt;
    }
}
