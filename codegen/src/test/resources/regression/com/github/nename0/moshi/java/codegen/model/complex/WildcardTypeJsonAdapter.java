// Code generated by moshi-java-codegen. Do not edit.
package com.github.nename0.moshi.java.codegen.model.complex;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import java.io.IOException;
import java.lang.Object;
import java.lang.Override;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public final class WildcardTypeJsonAdapter extends JsonAdapter<WildcardType> {
  private static final JsonReader.Options OPTIONS = JsonReader.Options.of("list");

  private final JsonAdapter<List<?>> adapter_list;

  public WildcardTypeJsonAdapter(Moshi moshi) {
    adapter_list = moshi.adapter(Types.newParameterizedType(List.class, Types.subtypeOf(
        Object.class)), Collections.emptySet(), "list");
  }

  @Override
  public WildcardType fromJson(JsonReader reader) throws IOException {
    WildcardType instance = new WildcardType();
    reader.beginObject();
    while (reader.hasNext()) {
      int index = reader.selectName(OPTIONS);
      switch (index) {
        case -1:
          reader.skipName();
          reader.skipValue();
          break;
        case 0:
          ((WildcardType)instance).list = adapter_list.fromJson(reader);
          break;
      }
    }
    reader.endObject();
    return instance;
  }

  @Override
  public void toJson(JsonWriter writer, WildcardType value) throws IOException {
    writer.beginObject();
    writer.name("list");
    adapter_list.toJson(writer, ((WildcardType)value).list);
    writer.endObject();
  }
}
