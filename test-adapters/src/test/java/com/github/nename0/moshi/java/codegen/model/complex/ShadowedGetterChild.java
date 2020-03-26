package com.github.nename0.moshi.java.codegen.model.complex;

import com.github.nename0.moshi.java.codegen.model.complex.other.ShadowedGetterParent;
import com.squareup.moshi.JsonClass;

@JsonClass(generateAdapter = true, generator = "java")
public class ShadowedGetterChild extends ShadowedGetterParent {
    public int getid() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
