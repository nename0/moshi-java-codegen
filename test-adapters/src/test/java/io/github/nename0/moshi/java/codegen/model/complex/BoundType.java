package io.github.nename0.moshi.java.codegen.model.complex;

import com.squareup.moshi.JsonClass;
import io.github.nename0.moshi.java.codegen.model.TestingModel;
import io.github.nename0.moshi.java.codegen.model.complex.other.Idded;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonClass(generateAdapter = true, generator = "java")
public class BoundType<T> extends TestingModel {
    public List<? extends GenericInheritanceChain<T>> list;

    public List<? super String> otherList;

    @Override
    @SuppressWarnings("unchecked")
    public void fillRandomData() {
        int len = RNG.nextInt(10);
        list = new ArrayList<>(len);
        List l = list;
        for (int i = 0; i < len; i++) {
            GenericMiddle<T> value = new GenericInheritanceChain<>();
            value.fillRandomData();
            l.add(value);
        }
        len = RNG.nextInt(10);
        otherList = new ArrayList<>(len);
        for (int i = 0; i < len; i++) {
            otherList.add(randomString());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoundType boundType = (BoundType) o;
        return Objects.equals(list, boundType.list)
                && Objects.equals(otherList, boundType.otherList);
    }
}
