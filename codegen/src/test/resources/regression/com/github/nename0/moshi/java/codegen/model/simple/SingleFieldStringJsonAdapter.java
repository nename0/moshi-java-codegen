// Code generated by moshi-java-codegen. Do not edit.
package com.github.nename0.moshi.java.codegen.model.simple;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;
import com.squareup.moshi.Moshi;
import java.io.IOException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;

@SuppressWarnings("unused")
public final class SingleFieldStringJsonAdapter extends JsonAdapter<SingleFieldString> {
  private static final JsonReader.Options OPTIONS = JsonReader.Options.of("aString");

  private final JsonAdapter<String> adapter_aString;

  public SingleFieldStringJsonAdapter(Moshi moshi) {
    adapter_aString = moshi.adapter(String.class, Collections.emptySet(), "aString");
  }

  @Override
  public SingleFieldString fromJson(JsonReader reader) throws IOException {
    SingleFieldString instance = new SingleFieldString();
    reader.beginObject();
    while (reader.hasNext()) {
      int index = reader.selectName(OPTIONS);
      switch (index) {
        case -1:
          reader.skipName();
          reader.skipValue();
          break;
        case 0:
          ((SingleFieldString)instance).aString = adapter_aString.fromJson(reader);
          break;
      }
    }
    reader.endObject();
    return instance;
  }

  @Override
  public void toJson(JsonWriter writer, SingleFieldString value) throws IOException {
    writer.beginObject();
    writer.name("aString");
    adapter_aString.toJson(writer, ((SingleFieldString)value).aString);
    writer.endObject();
  }
}