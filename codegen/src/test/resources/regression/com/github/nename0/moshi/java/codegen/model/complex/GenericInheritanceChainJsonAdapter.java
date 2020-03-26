// Code generated by moshi-java-codegen. Do not edit.
package com.github.nename0.moshi.java.codegen.model.complex;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import java.io.IOException;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Override;
import java.lang.SuppressWarnings;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Set;

@SuppressWarnings("unused")
public final class GenericInheritanceChainJsonAdapter<T> extends JsonAdapter<GenericInheritanceChain<T>> {
  private static final JsonReader.Options OPTIONS = JsonReader.Options.of("color", "generic", "id");

  private static final Set<? extends Annotation> ANNOTATIONS_COLOR_0 = Types.getFieldJsonQualifierAnnotations(GenericInheritanceChain.class, "color");

  private final JsonAdapter<Integer> adapter_color;

  private final JsonAdapter<T> adapter_generic;

  private final JsonAdapter<T> adapter_id;

  public GenericInheritanceChainJsonAdapter(Moshi moshi, Type[] types) {
    if (types.length != 1) {
      throw new IllegalArgumentException("TypeVariable mismatch: Expecting 1 type(s) for generic type variables [T], but received " + types.length);
    }
    adapter_color = moshi.adapter(int.class, ANNOTATIONS_COLOR_0, "color");
    adapter_generic = moshi.adapter(types[0], Collections.emptySet(), "generic");
    adapter_id = moshi.adapter(types[0], Collections.emptySet(), "id");
  }

  @Override
  public GenericInheritanceChain<T> fromJson(JsonReader reader) throws IOException {
    GenericInheritanceChain<T> instance = new GenericInheritanceChain<T>();
    reader.beginObject();
    while (reader.hasNext()) {
      int index = reader.selectName(OPTIONS);
      switch (index) {
        case -1:
          reader.skipName();
          reader.skipValue();
          break;
        case 0:
          ((GenericInheritanceChain<T>)instance).color = adapter_color.fromJson(reader);
          break;
        case 1:
          ((GenericInheritanceChain<T>)instance).setGeneric(adapter_generic.fromJson(reader));
          break;
        case 2:
          ((GenericInheritanceChain<T>)instance).setId(adapter_id.fromJson(reader));
          break;
      }
    }
    reader.endObject();
    return instance;
  }

  @Override
  public void toJson(JsonWriter writer, GenericInheritanceChain<T> value) throws IOException {
    writer.beginObject();
    writer.name("color");
    adapter_color.toJson(writer, ((GenericInheritanceChain<T>)value).color);
    writer.name("generic");
    adapter_generic.toJson(writer, ((GenericInheritanceChain<T>)value).getGeneric());
    writer.name("id");
    adapter_id.toJson(writer, ((GenericInheritanceChain<T>)value).getId());
    writer.endObject();
  }
}