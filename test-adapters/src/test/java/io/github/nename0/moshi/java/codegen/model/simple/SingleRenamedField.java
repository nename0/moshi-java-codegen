package io.github.nename0.moshi.java.codegen.model.simple;

import com.squareup.moshi.Json;
import com.squareup.moshi.JsonClass;
import io.github.nename0.moshi.java.codegen.model.TestingModel;

@JsonClass(generateAdapter = true, generator = "java")
public class SingleRenamedField extends TestingModel {

    @Json(name = "boolean")
    public boolean aBoolean;

    @Override
    public void fillRandomData() {
        aBoolean = RNG.nextBoolean();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SingleRenamedField that = (SingleRenamedField) o;

        return aBoolean == that.aBoolean;
    }
}
