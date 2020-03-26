package io.github.nename0.moshi.java.codegen.other;

import com.squareup.moshi.JsonClass;

import com.github.nename0.moshi.java.codegen.model.ProtectedMember;

@JsonClass(generateAdapter = true, generator = "java")
public class ProtectedOutsidePackage extends ProtectedMember {

}