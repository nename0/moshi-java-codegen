package com.github.nename0.moshi.java.codegen.model.complex;

import com.github.nename0.moshi.java.codegen.model.TestingModel;
import com.squareup.moshi.JsonClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonClass(generateAdapter = true, generator = "java")
public class WildcardType extends TestingModel {
    public List<?> list;

    @Override
    @SuppressWarnings("unchecked")
    public void fillRandomData() {
        int len = RNG.nextInt(10);
        list = new ArrayList<>(len);
        List l = list;
        for (int i = 0; i < len; i++) {
            l.add(randomString());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WildcardType that = (WildcardType) o;
        return Objects.equals(list, that.list);
    }
}
