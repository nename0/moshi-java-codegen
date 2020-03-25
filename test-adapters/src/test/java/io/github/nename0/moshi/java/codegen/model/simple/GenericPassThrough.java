package io.github.nename0.moshi.java.codegen.model.simple;

import com.squareup.moshi.JsonClass;

@JsonClass(generateAdapter = true, generator = "java")
public class GenericPassThrough<K> extends GenericField<K> {
}
