package io.github.nename0.moshi.java.codegen;

import com.squareup.moshi.*;
import io.github.nename0.moshi.java.codegen.model.simple.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class SingleFieldTest {
    private final Moshi moshi = new Moshi.Builder()
            .build();

    @Test
    public void singleFieldString() throws IOException {
        assertThat(SingleFieldString.class.getAnnotation(JsonClass.class)).isNotNull();

        JsonAdapter<SingleFieldString> adapter = moshi.adapter(SingleFieldString.class);

        SingleFieldString fromModel = adapter.fromJson("{\"aString\": \"value\"}");
        assertThat(fromModel).isNotNull();
        assertThat(fromModel.aString).isEqualTo("value");

        SingleFieldString toModel = new SingleFieldString();
        toModel.aString = "Value2";
        String json = adapter.toJson(toModel);
        assertThat(json).isEqualTo("{\"aString\":\"Value2\"}");

        // test serializeNulls
        toModel.aString = null;
        json = adapter.serializeNulls().toJson(toModel);
        assertThat(json).isEqualTo("{\"" + "aString" + "\":null}");

        // test non - serializeNulls
        json = adapter.toJson(toModel);
        assertThat(json).isEqualTo("{}");

        JsonDataException failure = assertThrows(JsonDataException.class, () -> {
            adapter.fromJson("{\"aString\": []}");
        });
        assertThat(failure).hasMessageThat().contains("Expected a string but was BEGIN_ARRAY");
    }

    @Test()
    public void singleFieldInt() throws IOException {
        assertThat(SingleFieldInt.class.getAnnotation(JsonClass.class)).isNotNull();

        JsonAdapter<SingleFieldInt> adapter = moshi.adapter(SingleFieldInt.class);

        SingleFieldInt fromModel = adapter.fromJson("{\"anInt\": 555}");
        assertThat(fromModel).isNotNull();
        assertThat(fromModel.anInt).isEqualTo(555);

        SingleFieldInt toModel = new SingleFieldInt();
        toModel.anInt = 345;
        String json = adapter.toJson(toModel);
        assertThat(json).isEqualTo("{\"anInt\":345}");

        JsonDataException failure = assertThrows(JsonDataException.class, () -> {
            adapter.fromJson("{\"anInt\": \"A\"}");
        });
        assertThat(failure).hasMessageThat().contains("Expected an int but was A");

        failure = assertThrows(JsonDataException.class, () -> {
            adapter.fromJson("{\"anInt\": null}");
        });
        assertThat(failure).hasMessageThat().contains("Expected an int but was NULL");
    }

    @Test()
    public void singlePrivateField() throws IOException {
        assertThat(SinglePrivateField.class.getAnnotation(JsonClass.class)).isNotNull();

        JsonAdapter<SinglePrivateField> adapter = moshi.adapter(SinglePrivateField.class);

        SinglePrivateField fromModel = adapter.fromJson("{\"anInt\": 987}");
        assertThat(fromModel).isNotNull();
        assertThat(fromModel.getAnInt()).isEqualTo(987);

        SinglePrivateField toModel = new SinglePrivateField();
        toModel.setAnInt(4711);
        String json = adapter.toJson(toModel);
        assertThat(json).isEqualTo("{\"anInt\":4711}");

        JsonDataException failure = assertThrows(JsonDataException.class, () -> {
            adapter.fromJson("{\"anInt\": \"A\"}");
        });
        assertThat(failure).hasMessageThat().contains("Expected an int but was A");
    }

    @Test()
    public void singleRenamedField() throws IOException {
        assertThat(SingleRenamedField.class.getAnnotation(JsonClass.class)).isNotNull();

        JsonAdapter<SingleRenamedField> adapter = moshi.adapter(SingleRenamedField.class);

        SingleRenamedField fromModel = adapter.fromJson("{\"boolean\": true}");
        assertThat(fromModel).isNotNull();
        assertThat(fromModel.aBoolean).isEqualTo(true);

        SingleRenamedField toModel = new SingleRenamedField();
        toModel.aBoolean = false;
        String json = adapter.toJson(toModel);
        assertThat(json).isEqualTo("{\"boolean\":false}");

        JsonDataException failure = assertThrows(JsonDataException.class, () -> {
            adapter.fromJson("{\"boolean\": 5555}");
        });
        assertThat(failure).hasMessageThat().contains("Expected a boolean but was NUMBER");
    }

    @Test()
    public void singleRenamedPrivateField() throws IOException {
        assertThat(SingleRenamedPrivateField.class.getAnnotation(JsonClass.class)).isNotNull();

        JsonAdapter<SingleRenamedPrivateField> adapter = moshi.adapter(SingleRenamedPrivateField.class);

        SingleRenamedPrivateField fromModel = adapter.fromJson("{\"byte\": 42}");
        assertThat(fromModel).isNotNull();
        assertThat(fromModel.getaByte()).isEqualTo(42);

        SingleRenamedPrivateField toModel = new SingleRenamedPrivateField();
        toModel.setaByte((byte) 127);
        String json = adapter.toJson(toModel);
        assertThat(json).isEqualTo("{\"byte\":127}");

        JsonDataException failure = assertThrows(JsonDataException.class, () -> {
            adapter.fromJson("{\"byte\": 5555}");
        });
        assertThat(failure).hasMessageThat().contains("Expected a byte but was 5555");
    }
}
