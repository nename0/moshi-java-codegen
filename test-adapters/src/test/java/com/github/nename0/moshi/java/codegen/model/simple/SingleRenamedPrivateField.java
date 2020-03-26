package com.github.nename0.moshi.java.codegen.model.simple;

import com.github.nename0.moshi.java.codegen.model.TestingModel;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonClass;

@JsonClass(generateAdapter = true, generator = "java")
public class SingleRenamedPrivateField extends TestingModel {

    @Json(name = "byte")
    private byte aByte;

    @Override
    public void fillRandomData() {
        aByte = (byte)RNG.nextInt();
    }

    public byte getaByte() {
        return aByte;
    }

    public void setaByte(byte aByte) {
        this.aByte = aByte;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SingleRenamedPrivateField that = (SingleRenamedPrivateField) o;

        return aByte == that.aByte;
    }
}
