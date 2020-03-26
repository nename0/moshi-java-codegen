package com.github.nename0.moshi.java.codegen;

import com.github.nename0.moshi.java.codegen.model.simple.*;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonClass;
import com.squareup.moshi.Moshi;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.google.common.truth.Truth.assertThat;

public class SimpleInheritanceTest {
    private final Moshi moshi = new Moshi.Builder()
            .build();

    @Test
    public void childEmpty() throws IOException {
        assertThat(ChildEmpty.class.getAnnotation(JsonClass.class)).isNotNull();

        JsonAdapter<ChildEmpty> adapter = moshi.adapter(ChildEmpty.class);

        ChildEmpty fromModel = adapter.fromJson("{\"name\": \"Foo Bar\"}");
        assertThat(fromModel).isNotNull();
        assertThat(fromModel.name).isEqualTo("Foo Bar");

        ChildEmpty toModel = new ChildEmpty();
        toModel.name = "John Smith";
        String json = adapter.toJson(toModel);
        assertThat(json).isEqualTo("{\"name\":\"John Smith\"}");
    }

    @Test
    public void childInt() throws IOException {
        assertThat(ChildInt.class.getAnnotation(JsonClass.class)).isNotNull();

        JsonAdapter<ChildInt> adapter = moshi.adapter(ChildInt.class);

        ChildInt fromModel = adapter.fromJson("{\"name\": \"Foo Bar\", \"age\": 99}");
        assertThat(fromModel).isNotNull();
        assertThat(fromModel.name).isEqualTo("Foo Bar");
        assertThat(fromModel.age).isEqualTo(99);

        ChildInt toModel = new ChildInt();
        toModel.name = "John Smith";
        toModel.age = 45;
        String json = adapter.toJson(toModel);
        assertThat(json).startsWith("{");
        assertThat(json).contains("\"name\":\"John Smith\"");
        assertThat(json).contains(",\"");
        assertThat(json).contains("\"age\":45");
        assertThat(json).endsWith("}");
    }

    @Test
    public void shadowedField() throws IOException {
        assertThat(ShadowedField.class.getAnnotation(JsonClass.class)).isNotNull();

        JsonAdapter<ShadowedField> adapter = moshi.adapter(ShadowedField.class);

        ShadowedField fromModel = adapter.fromJson("{\"name\": \"Bar Baz\", \"renamed\": \"Other\"}");
        assertThat(fromModel).isNotNull();
        assertThat(((ParentString) fromModel).name).isEqualTo("Bar Baz");
        assertThat(fromModel.name).isEqualTo("Other");

        ShadowedField toModel = new ShadowedField();
        ((ParentString) toModel).name = "Mickey Mouse";
        toModel.name = "Donald Duck";
        String json = adapter.toJson(toModel);
        assertThat(json).startsWith("{");
        assertThat(json).contains("\"name\":\"Mickey Mouse\"");
        assertThat(json).contains(",\"");
        assertThat(json).contains("\"renamed\":\"Donald Duck\"");
        assertThat(json).endsWith("}");
    }

    @Test
    public void childGetter() throws IOException {
        assertThat(ChildGetter.class.getAnnotation(JsonClass.class)).isNotNull();

        JsonAdapter<ChildGetter> adapter = moshi.adapter(ChildGetter.class);

        ChildGetter fromModel = adapter.fromJson("{\"coordinates\": 1.11}");
        assertThat(fromModel).isNotNull();
        assertThat(fromModel.getCoordinates()).isEqualTo(1.11D);

        ChildGetter toModel = new ChildGetter();
        toModel.setCoordinates(3.33D);
        String json = adapter.toJson(toModel);
        assertThat(json).isEqualTo("{\"coordinates\":3.33}");
    }
}
