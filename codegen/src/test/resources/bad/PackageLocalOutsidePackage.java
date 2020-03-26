package io.github.nename0.moshi.java.codegen.other;

import com.squareup.moshi.JsonClass;

import com.github.nename0.moshi.java.codegen.model.PackageLocalMember;

@JsonClass(generateAdapter = true, generator = "java")
public class PackageLocalOutsidePackage extends PackageLocalMember {

}