package io.github.nename0.moshi.java.codegen.model.complex;

import com.squareup.moshi.JsonClass;
import io.github.nename0.moshi.java.codegen.model.HexColor;

import java.util.Objects;

@JsonClass(generateAdapter = true, generator = "java")
public class GenericInheritanceChainGetter<T> extends GenericMiddleGetter<T> {
    
    @HexColor
    public int color;

    private T str;

    public T getStr() {
        return str;
    }

    public void setStr(T str) {
        this.str = str;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void fillRandomData() {
        super.fillRandomData();
        // always called with T == String
        color = RNG.nextInt(0x1000000);
        str = (T) randomString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GenericInheritanceChainGetter<?> that = (GenericInheritanceChainGetter<?>) o;
        return color == that.color &&
                Objects.equals(str, that.str);
    }
}

