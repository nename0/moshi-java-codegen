package com.github.nename0.moshi.java.codegen;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeName;

import java.util.*;

/**
 * contains information about a parsed json field
 */
public class JsonFieldHolder {
    /**
     * the name of the field in java source
     */
    public String fieldName;
    /**
     * the name of the field in json as per @Json(name=x) or same as fieldName
     */
    public String jsonName;
    /**
     * the name of the setter method: "set" + fieldName or null to access directly
     */
    public String setterMethod;
    /**
     * the name of the getter method: "get"/"has"/"is" + fieldName or null to access directly
     */
    public String getterMethod;
    /**
     * the complete type including type parameters Used for the first param for {@link com.squareup.moshi.Moshi#adapter(java.lang.reflect.Type, java.util.Set, java.lang.String)}
     */
    public TypeName type;
    /**
     * the json annotations on this field. Used for the second param for {@link com.squareup.moshi.Moshi#adapter(java.lang.reflect.Type, java.util.Set, java.lang.String)}
     */
    public List<TypeName> jsonAnnotations;
    /**
     * the class which contains the way to access the field. When setter and getter are null this is the field declaring class.
     * Otherwise it might be a superclass with the getters and setters. Used access shadowed fields/methods
     */
    public TypeName accessorContainingClass;
    /**
     * the class which the field Used to generate unique adapter field names,
     * and for {@link com.squareup.moshi.Types#getFieldJsonQualifierAnnotations(java.lang.Class, java.lang.String)}
     */
    public ClassName fieldContainingClass;
    /**
     * The field for delegate adapter of this field. Used during rendering
     */
    public FieldSpec adapterField;

    public JsonFieldHolder setFieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public JsonFieldHolder setJsonName(String jsonName) {
        this.jsonName = jsonName;
        return this;
    }

    public JsonFieldHolder setSetterMethod(String setterMethod) {
        this.setterMethod = setterMethod;
        return this;
    }

    public JsonFieldHolder setGetterMethod(String getterMethod) {
        this.getterMethod = getterMethod;
        return this;
    }

    public JsonFieldHolder setType(TypeName type) {
        this.type = type;
        return this;
    }

    public JsonFieldHolder setJsonAnnotations(List<TypeName> jsonAnnotations) {
        this.jsonAnnotations = jsonAnnotations;
        return this;
    }

    public JsonFieldHolder setAccessorContainingClass(TypeName accessorContainingClass) {
        this.accessorContainingClass = accessorContainingClass;
        return this;
    }

    public JsonFieldHolder setFieldContainingClass(ClassName fieldContainingClass) {
        this.fieldContainingClass = fieldContainingClass;
        return this;
    }

    public boolean hasSetterAndGetter() {
        return setterMethod != null;
    }
}
