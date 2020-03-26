package com.github.nename0.moshi.java.codegen.model.simple;

import com.github.nename0.moshi.java.codegen.model.TestingModel;
import com.squareup.moshi.JsonClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonClass(generateAdapter = true, generator = "java")
public class ListField extends TestingModel {
    public List<Integer> list;

    @Override
    public void fillRandomData() {
        int len = RNG.nextInt(10);
        list = new ArrayList<>(len);
        for (int i = 0; i < len; i++) {
            list.add(RNG.nextInt());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListField listField = (ListField) o;
        return Objects.equals(list, listField.list);
    }
}
