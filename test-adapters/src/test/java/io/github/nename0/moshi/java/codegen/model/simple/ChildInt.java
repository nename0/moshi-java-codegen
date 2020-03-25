package io.github.nename0.moshi.java.codegen.model.simple;

import com.squareup.moshi.JsonClass;

@JsonClass(generateAdapter = true, generator = "java")
public class ChildInt extends ParentString {
    public int age;

    @Override
    public void fillRandomData() {
        super.fillRandomData();
        age = RNG.nextInt();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ChildInt childInt = (ChildInt) o;
        return age == childInt.age;
    }
}
