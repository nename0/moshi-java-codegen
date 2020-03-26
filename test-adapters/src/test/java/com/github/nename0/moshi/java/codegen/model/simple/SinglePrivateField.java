package com.github.nename0.moshi.java.codegen.model.simple;

import com.github.nename0.moshi.java.codegen.model.TestingModel;
import com.squareup.moshi.JsonClass;

@JsonClass(generateAdapter = true, generator = "java")
public class SinglePrivateField extends TestingModel {
    private int anInt;

    @Override
    public void fillRandomData() {
        anInt = RNG.nextInt();
    }

    public int getAnInt() {
        return anInt;
    }

    public void setAnInt(int anInt) {
        this.anInt = anInt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SinglePrivateField that = (SinglePrivateField) o;
        return anInt == that.anInt;
    }
}
