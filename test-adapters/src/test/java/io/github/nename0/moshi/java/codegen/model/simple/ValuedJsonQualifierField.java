package io.github.nename0.moshi.java.codegen.model.simple;

import com.squareup.moshi.JsonClass;
import io.github.nename0.moshi.java.codegen.model.Constant;
import io.github.nename0.moshi.java.codegen.model.TestingModel;

import java.util.Objects;

@JsonClass(generateAdapter = true, generator = "java")
public class ValuedJsonQualifierField extends TestingModel {
    public static final String CONSTANT_VALUE = "CONSTANT_VALUE";

    @Constant(CONSTANT_VALUE)
    public String constant_value;

    @Override
    public void fillRandomData() {
        constant_value = CONSTANT_VALUE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValuedJsonQualifierField that = (ValuedJsonQualifierField) o;
        return Objects.equals(constant_value, that.constant_value);
    }
}

