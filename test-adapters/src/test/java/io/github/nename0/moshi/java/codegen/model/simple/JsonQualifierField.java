package io.github.nename0.moshi.java.codegen.model.simple;

import com.squareup.moshi.JsonClass;
import io.github.nename0.moshi.java.codegen.model.HexColor;
import io.github.nename0.moshi.java.codegen.model.TestingModel;

@JsonClass(generateAdapter = true, generator = "java")
public class JsonQualifierField extends TestingModel {
    @HexColor
    public int color;

    @Override
    public void fillRandomData() {
        color = RNG.nextInt(0x1000000);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JsonQualifierField that = (JsonQualifierField) o;
        return color == that.color;
    }
}
