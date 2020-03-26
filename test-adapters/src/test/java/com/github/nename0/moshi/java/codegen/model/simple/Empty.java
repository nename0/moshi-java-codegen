package com.github.nename0.moshi.java.codegen.model.simple;

import com.github.nename0.moshi.java.codegen.model.TestingModel;
import com.squareup.moshi.JsonClass;

@JsonClass(generateAdapter = true, generator = "java")
public class Empty extends TestingModel {
    @Override
    public void fillRandomData() {
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Empty;
    }
}
