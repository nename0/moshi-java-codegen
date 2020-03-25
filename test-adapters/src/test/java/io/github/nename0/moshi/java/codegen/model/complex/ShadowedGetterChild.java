package io.github.nename0.moshi.java.codegen.model.complex;

import com.squareup.moshi.JsonClass;
import io.github.nename0.moshi.java.codegen.model.complex.other.ShadowedGetterParent;

@JsonClass(generateAdapter = true, generator = "java")
public class ShadowedGetterChild extends ShadowedGetterParent {
    public int getid() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
