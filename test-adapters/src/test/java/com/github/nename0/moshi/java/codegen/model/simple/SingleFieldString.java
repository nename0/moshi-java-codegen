package com.github.nename0.moshi.java.codegen.model.simple;

import com.github.nename0.moshi.java.codegen.model.TestingModel;
import com.squareup.moshi.JsonClass;

import java.util.Objects;

@JsonClass(generateAdapter = true, generator = "java")
public class SingleFieldString extends TestingModel {

    public String aString;

    @Override
    public void fillRandomData() {
        aString = randomString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SingleFieldString that = (SingleFieldString) o;

        return Objects.equals(aString, that.aString);
    }
}
