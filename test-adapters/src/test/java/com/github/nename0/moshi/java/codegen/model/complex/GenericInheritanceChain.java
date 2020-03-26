package com.github.nename0.moshi.java.codegen.model.complex;

import com.github.nename0.moshi.java.codegen.model.HexColor;
import com.squareup.moshi.JsonClass;

import java.util.Objects;

@JsonClass(generateAdapter = true, generator = "java")
public class GenericInheritanceChain<T> extends GenericMiddle<T> {
    
    @HexColor
    public int color;

    private T generic;

    public T getGeneric() {
        return generic;
    }

    public void setGeneric(T generic) {
        this.generic = generic;
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void fillRandomData() {
        super.fillRandomData();
        // always called with T == String
        color = RNG.nextInt(0x1000000);
        generic = (T) randomString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GenericInheritanceChain<?> that = (GenericInheritanceChain<?>) o;
        return color == that.color &&
                Objects.equals(generic, that.generic);
    }
}

