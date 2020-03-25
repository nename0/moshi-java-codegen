package io.github.nename0.moshi.java.codegen.model.complex.other;

import com.squareup.moshi.Json;
import io.github.nename0.moshi.java.codegen.model.complex.GenericMiddleGetter;

import java.util.Objects;

public class ShadowedGetterParent extends GenericMiddleGetter<String> {
    @Json(name = "renamed-id")
    protected int id;

    @Override
    public void fillRandomData() {
        super.fillRandomData();
        id = RNG.nextInt();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ShadowedGetterParent that = (ShadowedGetterParent) o;
        return id == that.id;
    }
}
