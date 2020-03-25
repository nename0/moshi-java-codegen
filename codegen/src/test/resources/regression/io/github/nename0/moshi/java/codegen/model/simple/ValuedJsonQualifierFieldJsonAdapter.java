// Code generated by moshi-java-codegen. Do not edit.
package io.github.nename0.moshi.java.codegen.model.simple;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import java.io.IOException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.lang.annotation.Annotation;
import java.util.Set;

@SuppressWarnings("unused")
public final class ValuedJsonQualifierFieldJsonAdapter extends JsonAdapter<ValuedJsonQualifierField> {
  private static final JsonReader.Options OPTIONS = JsonReader.Options.of("constant_value");

  private static final Set<? extends Annotation> ANNOTATIONS_CONSTANT_VALUE_0 = Types.getFieldJsonQualifierAnnotations(ValuedJsonQualifierField.class, "constant_value");

  private final JsonAdapter<String> adapter_constant_value;

  public ValuedJsonQualifierFieldJsonAdapter(Moshi moshi) {
    adapter_constant_value = moshi.adapter(String.class, ANNOTATIONS_CONSTANT_VALUE_0,
        "constant_value");
  }

  @Override
  public ValuedJsonQualifierField fromJson(JsonReader reader) throws IOException {
    ValuedJsonQualifierField instance = new ValuedJsonQualifierField();
    reader.beginObject();
    while (reader.hasNext()) {
      int index = reader.selectName(OPTIONS);
      switch (index) {
        case -1:
          reader.skipName();
          reader.skipValue();
          break;
        case 0:
          ((ValuedJsonQualifierField)instance).constant_value = adapter_constant_value.fromJson(reader);
          break;
      }
    }
    reader.endObject();
    return instance;
  }

  @Override
  public void toJson(JsonWriter writer, ValuedJsonQualifierField value) throws IOException {
    writer.beginObject();
    writer.name("constant_value");
    adapter_constant_value.toJson(writer, ((ValuedJsonQualifierField)value).constant_value);
    writer.endObject();
  }
}