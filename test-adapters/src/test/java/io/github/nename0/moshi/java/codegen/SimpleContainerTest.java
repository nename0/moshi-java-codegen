package io.github.nename0.moshi.java.codegen;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonClass;
import com.squareup.moshi.Moshi;
import io.github.nename0.moshi.java.codegen.model.simple.ArrayField;
import io.github.nename0.moshi.java.codegen.model.simple.ChildInt;
import io.github.nename0.moshi.java.codegen.model.simple.ListField;
import io.github.nename0.moshi.java.codegen.model.simple.ObjectField;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static com.google.common.truth.Truth.assertThat;

public class SimpleContainerTest {
    private final Moshi moshi = new Moshi.Builder()
            .build();

    @Test
    public void arrayField() throws IOException {
        assertThat(ArrayField.class.getAnnotation(JsonClass.class)).isNotNull();

        JsonAdapter<ArrayField> adapter = moshi.adapter(ArrayField.class);

        ArrayField fromModel = adapter.fromJson("{\"array\": [\"first\", null]}");
        assertThat(fromModel).isNotNull();
        assertThat(fromModel.array).hasLength(2);
        assertThat(fromModel.array).asList().containsExactly("first", null).inOrder();

        ArrayField toModel = new ArrayField();
        toModel.array = new String[]{"A", "B", "C"};
        String json = adapter.toJson(toModel);
        assertThat(json).isEqualTo("{\"array\":[\"A\",\"B\",\"C\"]}");
    }

    @Test
    public void listField() throws IOException {
        assertThat(ListField.class.getAnnotation(JsonClass.class)).isNotNull();

        JsonAdapter<ListField> adapter = moshi.adapter(ListField.class);

        ListField fromModel = adapter.fromJson("{\"list\": [33,44,55]}");
        assertThat(fromModel).isNotNull();
        assertThat(fromModel.list).hasSize(3);
        assertThat(fromModel.list).containsExactly(33,44,55).inOrder();

        ListField toModel = new ListField();
        toModel.list = Arrays.asList(1, 2, 3);
        String json = adapter.toJson(toModel);
        assertThat(json).isEqualTo("{\"list\":[1,2,3]}");
    }

    @Test
    public void objectField() throws IOException {
        assertThat(ObjectField.class.getAnnotation(JsonClass.class)).isNotNull();

        JsonAdapter<ObjectField> adapter = moshi.adapter(ObjectField.class);

        ObjectField fromModel = adapter.fromJson("{\"object\": {\"name\": \"In object\", \"age\": 4711}}");
        assertThat(fromModel).isNotNull();
        assertThat(fromModel.object).isNotNull();
        assertThat(fromModel.object.name).isEqualTo("In object");
        assertThat(fromModel.object.age).isEqualTo(4711);

        ObjectField toModel = new ObjectField();
        toModel.object = new ChildInt();
        toModel.object.name = "A name";
        toModel.object.age = 0;
        String json = adapter.toJson(toModel);
        assertThat(json).startsWith("{\"object\":{");
        assertThat(json).contains("\"name\":\"A name\"");
        assertThat(json).contains(",\"");
        assertThat(json).contains("\"age\":0");
        assertThat(json).endsWith("}}");
    }
}
