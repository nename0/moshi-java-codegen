package io.github.nename0.moshi.java.codegen.model;

import com.squareup.moshi.*;

import javax.annotation.Nullable;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Set;

public class FakeWildcardSupportingAdapterFactory extends JsonAdapter<String> implements JsonAdapter.Factory {

    @Nullable
    @Override
    public JsonAdapter<?> create(Type type, Set<? extends Annotation> annotations, Moshi moshi) {
        if (!(type instanceof WildcardType)) {
            return null;
        }
        return this;
    }

    @Nullable
    @Override
    public String fromJson(JsonReader reader) throws IOException {
        return reader.nextString();
    }

    @Override
    public void toJson(JsonWriter writer, @Nullable String value) throws IOException {
        writer.value(value);
    }
}
