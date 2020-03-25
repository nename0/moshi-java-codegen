package io.github.nename0.moshi.java.codegen.model.simple;

import com.squareup.moshi.JsonClass;
import io.github.nename0.moshi.java.codegen.model.simple.other.ParentProtected;

@JsonClass(generateAdapter = true, generator = "java")
public class ChildGetter extends ParentProtected {
    public double getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double coordinates) {
        this.coordinates = coordinates;
    }
}
