package com.github.nename0.moshi.java.codegen.model.complex;

import com.squareup.moshi.Json;
import com.squareup.moshi.JsonClass;

@JsonClass(generateAdapter = true, generator = "java")
public class ShadowedSameClassName extends com.github.nename0.moshi.java.codegen.model.complex.other.ShadowedSameClassName {
    @Json(name = "renamed")
    public int same;

    @Override
    public void fillRandomData() {
        super.fillRandomData();
        same = RNG.nextInt();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ShadowedSameClassName that = (ShadowedSameClassName) o;
        return same == that.same;
    }
}
