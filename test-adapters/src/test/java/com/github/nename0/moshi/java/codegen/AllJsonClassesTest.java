package com.github.nename0.moshi.java.codegen;

import com.github.nename0.moshi.java.codegen.model.ConstantAdapterFactory;
import com.github.nename0.moshi.java.codegen.model.FakeWildcardSupportingAdapterFactory;
import com.github.nename0.moshi.java.codegen.model.HexColorAdapter;
import com.github.nename0.moshi.java.codegen.model.TestingModel;
import com.github.nename0.moshi.java.codegen.model.simple.*;
import com.squareup.moshi.*;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.reflections.Reflections;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AllJsonClassesTest implements ArgumentsProvider {
    public static final List<Class<? extends TestingModel>> ALL_CLASSES = Arrays.asList(
            ChildEmpty.class,
            ChildInt.class,
            SingleFieldInt.class,
            SingleFieldString.class,
            SinglePrivateField.class,
            SingleRenamedField.class,
            SingleRenamedPrivateField.class
    );

    private final Moshi moshi = new Moshi.Builder()
            .add(new HexColorAdapter())
            .add(new ConstantAdapterFactory())
            .add(new FakeWildcardSupportingAdapterFactory())
            .build();

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        Reflections reflections = new Reflections(TestingModel.class.getPackage().getName());
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(JsonClass.class);
        return typesAnnotatedWith.stream().map(c -> {
            if (!TestingModel.class.isAssignableFrom(c)) {
                throw new IllegalArgumentException(c + " needs to extend TestingModel");
            }
            return Arguments.of(c);
        });
    }

    @ParameterizedTest(name = "correctJsonClassAnnotation() with class {0}")
    @ArgumentsSource(AllJsonClassesTest.class)
    public <T extends TestingModel> void correctJsonClassAnnotation(Class<T> modelClass) {
        JsonClass jsonClass = modelClass.getAnnotation(JsonClass.class);
        assertThat(jsonClass).isNotNull();
        assertThat(jsonClass.generateAdapter()).isTrue();
        assertThat(jsonClass.generator()).isEqualTo("java");
    }

    @ParameterizedTest(name = "with class {0}")
    @ArgumentsSource(AllJsonClassesTest.class)
    public <T extends TestingModel> void testClass(Class<T> modelClass) throws Exception {
        String className = Types.generatedJsonAdapterName(modelClass);

        Class<?> adapterClass = Class.forName(className);
        assertThat(adapterClass).isAssignableTo(JsonAdapter.class);
        // this does not support generics, but so does the moshi.adapter call
        ParameterizedType adapterParameterized = (ParameterizedType) adapterClass.getGenericSuperclass();
        Class<?> actualModelClass = Types.getRawType(adapterParameterized.getActualTypeArguments()[0]);
        assertThat(actualModelClass).isEqualTo(modelClass);

        JsonAdapter<T> adapter;
        if (modelClass.getTypeParameters().length > 0) {
            Type[] typeArguments = new Type[modelClass.getTypeParameters().length];
            // type arguments are always Strings
            Arrays.fill(typeArguments, String.class);

            adapter = moshi.adapter(Types.newParameterizedType(modelClass, typeArguments));
        } else {
            adapter = moshi.adapter(modelClass);
        }

        // check null handling
        assertThat(adapter.fromJson("null")).isNull();
        assertThat(adapter.toJson(null)).isEqualTo("null");

        // failOnUnknown()
        JsonDataException failure = assertThrows(JsonDataException.class, () -> {
            adapter.failOnUnknown().fromJson("{\"unknown\": 1}");
        });
        assertThat(failure).hasMessageThat().contains("Cannot skip unexpected NAME at $.unknown");

        // empty object
        T empty = adapter.fromJson("{}");
        assertThat(empty).isEqualTo(modelClass.newInstance());

        // round trip
        for (int i = 0; i < 10; i++) {
            T instance = modelClass.newInstance();
            instance.fillRandomData();
            String json = adapter.toJson(instance);
            assertThat(json).isNotEqualTo("null");
            if (modelClass != Empty.class) // special handling for Empty class
                assertThat(json).isNotEqualTo("{}");
            T parsed = adapter.fromJson(json);
            assertThat(parsed).isEqualTo(instance);
        }
    }
}
