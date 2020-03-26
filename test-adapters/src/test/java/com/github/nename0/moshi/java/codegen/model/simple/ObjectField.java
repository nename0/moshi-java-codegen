package com.github.nename0.moshi.java.codegen.model.simple;

import com.github.nename0.moshi.java.codegen.model.TestingModel;
import com.squareup.moshi.JsonClass;

import java.util.Objects;

@JsonClass(generateAdapter = true, generator = "java")
public class ObjectField extends TestingModel {
    public ChildInt object;

    @Override
    public void fillRandomData() {
        object = new ChildInt();
        object.fillRandomData();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectField that = (ObjectField) o;
        return Objects.equals(object, that.object);
    }
}
