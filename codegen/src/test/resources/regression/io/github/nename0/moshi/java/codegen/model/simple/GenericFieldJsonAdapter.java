// Code generated by moshi-java-codegen. Do not edit.
package io.github.nename0.moshi.java.codegen.model.simple;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;
import com.squareup.moshi.Moshi;
import java.io.IOException;
import java.lang.IllegalArgumentException;
import java.lang.Override;
import java.lang.SuppressWarnings;
import java.lang.reflect.Type;
import java.util.Collections;

@SuppressWarnings("unused")
public final class GenericFieldJsonAdapter<T> extends JsonAdapter<GenericField<T>> {
  private static final JsonReader.Options OPTIONS = JsonReader.Options.of("generic");

  private final JsonAdapter<T> adapter_generic;

  public GenericFieldJsonAdapter(Moshi moshi, Type[] types) {
    if (types.length != 1) {
      throw new IllegalArgumentException("TypeVariable mismatch: Expecting 1 type(s) for generic type variables [T], but received " + types.length);
    }
    adapter_generic = moshi.adapter(types[0], Collections.emptySet(), "generic");
  }

  @Override
  public GenericField<T> fromJson(JsonReader reader) throws IOException {
    GenericField<T> instance = new GenericField<T>();
    reader.beginObject();
    while (reader.hasNext()) {
      int index = reader.selectName(OPTIONS);
      switch (index) {
        case -1:
          reader.skipName();
          reader.skipValue();
          break;
        case 0:
          ((GenericField<T>)instance).generic = adapter_generic.fromJson(reader);
          break;
      }
    }
    reader.endObject();
    return instance;
  }

  @Override
  public void toJson(JsonWriter writer, GenericField<T> value) throws IOException {
    writer.beginObject();
    writer.name("generic");
    adapter_generic.toJson(writer, ((GenericField<T>)value).generic);
    writer.endObject();
  }
}
