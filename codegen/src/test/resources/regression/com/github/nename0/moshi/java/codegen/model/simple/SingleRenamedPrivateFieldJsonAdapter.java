// Code generated by moshi-java-codegen. Do not edit.
package com.github.nename0.moshi.java.codegen.model.simple;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;
import com.squareup.moshi.Moshi;
import java.io.IOException;
import java.lang.Byte;
import java.lang.Override;
import java.lang.SuppressWarnings;
import java.util.Collections;

@SuppressWarnings("unused")
public final class SingleRenamedPrivateFieldJsonAdapter extends JsonAdapter<SingleRenamedPrivateField> {
  private static final JsonReader.Options OPTIONS = JsonReader.Options.of("byte");

  private final JsonAdapter<Byte> adapter_aByte;

  public SingleRenamedPrivateFieldJsonAdapter(Moshi moshi) {
    adapter_aByte = moshi.adapter(byte.class, Collections.emptySet(), "byte");
  }

  @Override
  public SingleRenamedPrivateField fromJson(JsonReader reader) throws IOException {
    SingleRenamedPrivateField instance = new SingleRenamedPrivateField();
    reader.beginObject();
    while (reader.hasNext()) {
      int index = reader.selectName(OPTIONS);
      switch (index) {
        case -1:
          reader.skipName();
          reader.skipValue();
          break;
        case 0:
          ((SingleRenamedPrivateField)instance).setaByte(adapter_aByte.fromJson(reader));
          break;
      }
    }
    reader.endObject();
    return instance;
  }

  @Override
  public void toJson(JsonWriter writer, SingleRenamedPrivateField value) throws IOException {
    writer.beginObject();
    writer.name("byte");
    adapter_aByte.toJson(writer, ((SingleRenamedPrivateField)value).getaByte());
    writer.endObject();
  }
}