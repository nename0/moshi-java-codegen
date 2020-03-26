package com.github.nename0.moshi.java.codegen.model.simple;

import com.squareup.moshi.Json;
import com.squareup.moshi.JsonClass;

import java.util.Objects;

@JsonClass(generateAdapter = true, generator = "java")
public class ShadowedField extends ParentString {
    @Json(name = "renamed")
    public String name;

    @Override
    public void fillRandomData() {
        super.fillRandomData();
        name = randomString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ShadowedField that = (ShadowedField) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
