package io.github.nename0.moshi.java.codegen.model.complex;

import com.squareup.moshi.JsonClass;
import io.github.nename0.moshi.java.codegen.model.TestingModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonClass(generateAdapter = true, generator = "java")
public class GenericListField<T> extends TestingModel {
    public List<T> list;

    @Override
    @SuppressWarnings("unchecked")
    public void fillRandomData() {
        // always called with T == String
        int len = RNG.nextInt(10);
        list = new ArrayList<>(len);
        for (int i = 0; i < len; i++) {
            list.add((T) randomString());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenericListField listField = (GenericListField) o;
        return Objects.equals(list, listField.list);
    }
}
