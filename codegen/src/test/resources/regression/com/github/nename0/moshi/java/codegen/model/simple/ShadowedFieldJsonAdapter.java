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
public final class ShadowedFieldJsonAdapter extends JsonAdapter<ShadowedField> {
  private static final JsonReader.Options OPTIONS = JsonReader.Options.of("name", "renamed");

  private final JsonAdapter<String> adapter_name_ParentString;

  private final JsonAdapter<String> adapter_name_ShadowedField;

  public ShadowedFieldJsonAdapter(Moshi moshi) {
    adapter_name_ParentString = moshi.adapter(String.class, Collections.emptySet(), "name");
    adapter_name_ShadowedField = moshi.adapter(String.class, Collections.emptySet(), "renamed");
  }

  @Override
  public ShadowedField fromJson(JsonReader reader) throws IOException {
    ShadowedField instance = new ShadowedField();
    reader.beginObject();
    while (reader.hasNext()) {
      int index = reader.selectName(OPTIONS);
      switch (index) {
        case -1:
          reader.skipName();
          reader.skipValue();
          break;
        case 0:
          ((ParentString)instance).name = adapter_name_ParentString.fromJson(reader);
          break;
        case 1:
          ((ShadowedField)instance).name = adapter_name_ShadowedField.fromJson(reader);
          break;
      }
    }
    reader.endObject();
    return instance;
  }

  @Override
  public void toJson(JsonWriter writer, ShadowedField value) throws IOException {
    writer.beginObject();
    writer.name("name");
    adapter_name_ParentString.toJson(writer, ((ParentString)value).name);
    writer.name("renamed");
    adapter_name_ShadowedField.toJson(writer, ((ShadowedField)value).name);
    writer.endObject();
  }
}
