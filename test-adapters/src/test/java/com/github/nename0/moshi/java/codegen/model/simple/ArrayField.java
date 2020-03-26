package com.github.nename0.moshi.java.codegen.model.simple;

import com.github.nename0.moshi.java.codegen.model.TestingModel;
import com.squareup.moshi.JsonClass;

import java.util.Arrays;

@JsonClass(generateAdapter = true, generator = "java")
public class ArrayField extends TestingModel {
    public String[] array;

    @Override
    public void fillRandomData() {
        array = new String[RNG.nextInt(10)];
        for (int i = 0; i < array.length; i++) {
            array[i] = randomString();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrayField that = (ArrayField) o;
        return Arrays.equals(array, that.array);
    }
}
