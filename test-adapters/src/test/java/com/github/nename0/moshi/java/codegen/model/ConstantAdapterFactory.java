package com.github.nename0.moshi.java.codegen.model;

import com.squareup.moshi.*;

import javax.annotation.Nullable;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Set;

public class ConstantAdapterFactory implements JsonAdapter.Factory {
    @Nullable
    @Override
    public JsonAdapter<?> create(Type type, Set<? extends Annotation> annotations, Moshi moshi) {
        if (!type.equals(String.class)) {
            return null;
        }
        return annotations.stream()
                .filter(a -> a instanceof Constant)
                .findAny()
                .map(a -> {
                    Constant constant = (Constant) a;
                    return new ConstantJsonAdapter(constant.value());
                })
                .orElse(null);
    }

    public static class ConstantJsonAdapter extends JsonAdapter<String> {
        private final String constantValue;

        public ConstantJsonAdapter(String constantValue) {
            this.constantValue = constantValue;
        }

        @Nullable
        @Override
        public String fromJson(JsonReader reader) throws IOException {
            if (!reader.nextString().equals(constantValue)) {
                throw new JsonDataException("There must be the string '" + constantValue + "' here");
            }
            return constantValue;
        }

        @Override
        public void toJson(JsonWriter writer, @Nullable String value) throws IOException {
            // ignore actual value
            writer.value(constantValue);
        }
    }
}
