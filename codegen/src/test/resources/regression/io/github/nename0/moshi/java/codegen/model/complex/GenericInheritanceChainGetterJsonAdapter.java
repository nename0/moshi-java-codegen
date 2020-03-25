// Code generated by moshi-java-codegen. Do not edit.
package io.github.nename0.moshi.java.codegen.model.complex;

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
public final class GenericInheritanceChainGetterJsonAdapter<T> extends JsonAdapter<GenericInheritanceChainGetter<T>> {
  private static final JsonReader.Options OPTIONS = JsonReader.Options.of("color", "id", "str");

  private static final Set<? extends Annotation> ANNOTATIONS_COLOR_0 = Types.getFieldJsonQualifierAnnotations(GenericInheritanceChainGetter.class, "color");

  private final JsonAdapter<Integer> adapter_color;

  private final JsonAdapter<T> adapter_id;

  private final JsonAdapter<T> adapter_str;

  public GenericInheritanceChainGetterJsonAdapter(Moshi moshi, Type[] types) {
    if (types.length != 1) {
      throw new IllegalArgumentException("TypeVariable mismatch: Expecting 1 type(s) for generic type variables [T], but received " + types.length);
    }
    adapter_color = moshi.adapter(int.class, ANNOTATIONS_COLOR_0, "color");
    adapter_id = moshi.adapter(types[0], Collections.emptySet(), "id");
    adapter_str = moshi.adapter(types[0], Collections.emptySet(), "str");
  }

  @Override
  public GenericInheritanceChainGetter<T> fromJson(JsonReader reader) throws IOException {
    GenericInheritanceChainGetter<T> instance = new GenericInheritanceChainGetter<T>();
    reader.beginObject();
    while (reader.hasNext()) {
      int index = reader.selectName(OPTIONS);
      switch (index) {
        case -1:
          reader.skipName();
          reader.skipValue();
          break;
        case 0:
          ((GenericInheritanceChainGetter<T>)instance).color = adapter_color.fromJson(reader);
          break;
        case 1:
          ((GenericMiddleGetter<T>)instance).setId(adapter_id.fromJson(reader));
          break;
        case 2:
          ((GenericInheritanceChainGetter<T>)instance).setStr(adapter_str.fromJson(reader));
          break;
      }
    }
    reader.endObject();
    return instance;
  }

  @Override
  public void toJson(JsonWriter writer, GenericInheritanceChainGetter<T> value) throws IOException {
    writer.beginObject();
    writer.name("color");
    adapter_color.toJson(writer, ((GenericInheritanceChainGetter<T>)value).color);
    writer.name("id");
    adapter_id.toJson(writer, ((GenericMiddleGetter<T>)value).getId());
    writer.name("str");
    adapter_str.toJson(writer, ((GenericInheritanceChainGetter<T>)value).getStr());
    writer.endObject();
  }
}
