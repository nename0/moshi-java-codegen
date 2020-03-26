package com.github.nename0.moshi.java.codegen;

import com.github.nename0.moshi.java.codegen.model.simple.GenericChildNoParam;
import com.github.nename0.moshi.java.codegen.model.simple.GenericField;
import com.github.nename0.moshi.java.codegen.model.simple.GenericGetter;
import com.github.nename0.moshi.java.codegen.model.simple.GenericPassThrough;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonClass;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SimpleGenericTest {
    private final Moshi moshi = new Moshi.Builder()
            .build();

    @Test
    public void genericField() throws IOException {
        assertThat(GenericField.class.getAnnotation(JsonClass.class)).isNotNull();

        JsonAdapter<GenericField<String>> adapterStr = moshi.adapter(
                Types.newParameterizedType(GenericField.class, String.class));

        GenericField<String> fromModelStr = adapterStr.fromJson("{\"generic\": \"aString\"}");
        assertThat(fromModelStr).isNotNull();
        assertThat(fromModelStr.generic).isInstanceOf(String.class);
        assertThat(fromModelStr.generic).isEqualTo("aString");

        GenericField<String> toModelStr = new GenericField<>();
        toModelStr.generic = "Another string";
        String json = adapterStr.toJson(toModelStr);
        assertThat(json).isEqualTo("{\"generic\":\"Another string\"}");

        JsonAdapter<GenericField<Integer>> adapterInt = moshi.adapter(
                Types.newParameterizedType(GenericField.class, Integer.class));

        GenericField<Integer> fromModelInt = adapterInt.fromJson("{\"generic\": 444}");
        assertThat(fromModelInt).isNotNull();
        assertThat(fromModelInt.generic).isInstanceOf(Integer.class);
        assertThat(fromModelInt.generic).isEqualTo(444);

        GenericField<Integer> toModelInt = new GenericField<>();
        toModelInt.generic = 9876;
        json = adapterInt.toJson(toModelInt);
        assertThat(json).isEqualTo("{\"generic\":9876}");
    }

    @Test
    public void genericFieldSelfReference() throws IOException {
        JsonAdapter<GenericField<GenericField<String>>> adapter = moshi.adapter(
                Types.newParameterizedType(GenericField.class, Types.newParameterizedType(GenericField.class, String.class)));

        GenericField<GenericField<String>> fromModel = adapter.fromJson("{\"generic\": {\"generic\": \"aString\"}}");
        assertThat(fromModel).isNotNull();
        assertThat(fromModel.generic).isInstanceOf(GenericField.class);
        assertThat(fromModel.generic.generic).isInstanceOf(String.class);
        assertThat(fromModel.generic.generic).isEqualTo("aString");

        GenericField<GenericField<String>> toModel = new GenericField<>();
        toModel.generic = new GenericField<>();
        toModel.generic.generic = "XX";
        String json = adapter.toJson(toModel);
        assertThat(json).isEqualTo("{\"generic\":{\"generic\":\"XX\"}}");
    }

    @Test
    public void genericFieldAdapter() throws Exception {
        String className = Types.generatedJsonAdapterName(GenericField.class);
        Class<?> adapterClass = Class.forName(className);

        Constructor<?> constructor = adapterClass.getDeclaredConstructor(Moshi.class, Type[].class);
        InvocationTargetException failure = assertThrows(InvocationTargetException.class, () -> {
            constructor.newInstance(moshi, new Type[0]);
        });
        assertThat(failure).hasCauseThat().hasMessageThat().contains("TypeVariable mismatch: Expecting 1 type(s) for generic type variables [T], but received 0");

        failure = assertThrows(InvocationTargetException.class, () -> {
            constructor.newInstance(moshi, new Type[2]);
        });
        assertThat(failure).hasCauseThat().hasMessageThat().contains("TypeVariable mismatch: Expecting 1 type(s) for generic type variables [T], but received 2");
    }

    @Test
    public void genericChildNoParamAdapter() throws Exception {
        String className = Types.generatedJsonAdapterName(GenericChildNoParam.class);
        Class<?> adapterClass = Class.forName(className);

        Constructor<?> constructor = adapterClass.getDeclaredConstructor(Moshi.class);
        assertThrows(NoSuchMethodException.class, () -> {
            adapterClass.getDeclaredConstructor(Moshi.class, Type[].class);
        });
        assertThat(constructor.newInstance(moshi).getClass()).isEqualTo(adapterClass);
    }

    @Test
    public void genericPassThroughAdapter() throws Exception {
        String className = Types.generatedJsonAdapterName(GenericPassThrough.class);
        Class<?> adapterClass = Class.forName(className);

        Constructor<?> constructor = adapterClass.getDeclaredConstructor(Moshi.class, Type[].class);
        InvocationTargetException failure = assertThrows(InvocationTargetException.class, () -> {
            constructor.newInstance(moshi, new Type[0]);
        });
        assertThat(failure).hasCauseThat().hasMessageThat().contains("TypeVariable mismatch: Expecting 1 type(s) for generic type variables [K], but received 0");

        failure = assertThrows(InvocationTargetException.class, () -> {
            constructor.newInstance(moshi, new Type[2]);
        });
        assertThat(failure).hasCauseThat().hasMessageThat().contains("TypeVariable mismatch: Expecting 1 type(s) for generic type variables [K], but received 2");
    }

    @Test
    public void genericGetter() throws IOException {
        assertThat(GenericGetter.class.getAnnotation(JsonClass.class)).isNotNull();

        JsonAdapter<GenericGetter<String>> adapterStr = moshi.adapter(
                Types.newParameterizedType(GenericGetter.class, String.class));

        GenericGetter<String> fromModelStr = adapterStr.fromJson("{\"generic\": \"aString\"}");
        assertThat(fromModelStr).isNotNull();
        assertThat(fromModelStr.getGeneric()).isInstanceOf(String.class);
        assertThat(fromModelStr.getGeneric()).isEqualTo("aString");

        GenericGetter<String> toModelStr = new GenericGetter<>();
        toModelStr.setGeneric("Another string");
        String json = adapterStr.toJson(toModelStr);
        assertThat(json).isEqualTo("{\"generic\":\"Another string\"}");

        JsonAdapter<GenericGetter<Integer>> adapterInt = moshi.adapter(
                Types.newParameterizedType(GenericGetter.class, Integer.class));

        GenericGetter<Integer> fromModelInt = adapterInt.fromJson("{\"generic\": 444}");
        assertThat(fromModelInt).isNotNull();
        assertThat(fromModelInt.getGeneric()).isInstanceOf(Integer.class);
        assertThat(fromModelInt.getGeneric()).isEqualTo(444);

        GenericGetter<Integer> toModelInt = new GenericGetter<>();
        toModelInt.setGeneric(9876);
        json = adapterInt.toJson(toModelInt);
        assertThat(json).isEqualTo("{\"generic\":9876}");
    }
}
