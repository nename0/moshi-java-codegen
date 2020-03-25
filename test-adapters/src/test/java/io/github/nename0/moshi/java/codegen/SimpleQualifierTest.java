package io.github.nename0.moshi.java.codegen;

import com.squareup.moshi.*;
import io.github.nename0.moshi.java.codegen.model.Constant;
import io.github.nename0.moshi.java.codegen.model.ConstantAdapterFactory;
import io.github.nename0.moshi.java.codegen.model.HexColorAdapter;
import io.github.nename0.moshi.java.codegen.model.simple.JsonQualifierField;
import io.github.nename0.moshi.java.codegen.model.simple.ValuedJsonQualifierField;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Set;

import static com.google.common.truth.Truth.assertThat;
import static io.github.nename0.moshi.java.codegen.model.simple.ValuedJsonQualifierField.CONSTANT_VALUE;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SimpleQualifierTest {
    private final HexColorAdapter hexColorAdapter = new HexColorAdapter();
    private final Moshi moshi = new Moshi.Builder()
            .add(hexColorAdapter)
            .add(new ConstantAdapterFactory())
            .build();

    @Test
    public void jsonQualifierField() throws IOException {
        assertThat(JsonQualifierField.class.getAnnotation(JsonClass.class)).isNotNull();

        JsonAdapter<JsonQualifierField> adapter = moshi.adapter(JsonQualifierField.class);

        JsonQualifierField fromModel = adapter.fromJson("{\"color\": \"#004400\"}");
        assertThat(fromModel).isNotNull();
        assertThat(fromModel.color).isEqualTo(hexColorAdapter.fromJson("#004400"));

        JsonQualifierField toModel = new JsonQualifierField();
        toModel.color = hexColorAdapter.fromJson("#123456");
        String json = adapter.toJson(toModel);
        assertThat(json).isEqualTo("{\"color\":\"#123456\"}");
    }

    @Test
    public void constantAnnotation() throws IOException {
        // does not test codegen at all. Just tests the ConstantAdapterFactory implementation
        Set<? extends Annotation> annotations = Types.getFieldJsonQualifierAnnotations(ValuedJsonQualifierField.class, "constant_value");
        assertThat(annotations.iterator().next()).isInstanceOf(Constant.class);
        assertThat(((Constant) annotations.iterator().next()).value()).isEqualTo(CONSTANT_VALUE);
        JsonAdapter<String> adapter = moshi.adapter(String.class, annotations);

        assertThat(adapter.toJson("any")).isEqualTo("\"" + CONSTANT_VALUE + "\"");
        assertThat(adapter.fromJson("\"" + CONSTANT_VALUE + "\"")).isEqualTo(CONSTANT_VALUE);

        JsonDataException failure = assertThrows(JsonDataException.class, () -> {
            adapter.fromJson("\"other\"");
        });
        assertThat(failure).hasMessageThat().contains("There must be the string '" + CONSTANT_VALUE + "' here");
    }

    @Test
    public void valuedJsonQualifierField() throws IOException {
        assertThat(ValuedJsonQualifierField.class.getAnnotation(JsonClass.class)).isNotNull();

        JsonAdapter<ValuedJsonQualifierField> adapter = moshi.adapter(ValuedJsonQualifierField.class);

        ValuedJsonQualifierField fromModel = adapter.fromJson("{\"constant_value\": \"" + CONSTANT_VALUE + "\"}");
        assertThat(fromModel).isNotNull();
        assertThat(fromModel.constant_value).isEqualTo(CONSTANT_VALUE);

        ValuedJsonQualifierField toModel = new ValuedJsonQualifierField();
        String json = adapter.toJson(toModel);
        assertThat(json).isEqualTo("{\"constant_value\":\"" + CONSTANT_VALUE + "\"}");
    }
}
