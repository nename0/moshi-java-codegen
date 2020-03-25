package io.github.nename0.moshi.java.codegen.model.simple;

import com.squareup.moshi.JsonClass;
import io.github.nename0.moshi.java.codegen.model.TestingModel;

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
