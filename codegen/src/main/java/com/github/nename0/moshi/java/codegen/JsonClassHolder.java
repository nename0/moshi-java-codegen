/*
 * Copyright (C) 2015 BlueLine Labs
 *
 * Original File Name: JsonObjectHolder.java
 * From: https://git.io/JvS6u
 *
 * Modifications 2020
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.nename0.moshi.java.codegen;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeVariableName;

import java.util.List;
import java.util.TreeMap;

import javax.lang.model.element.TypeElement;

/**
 * contains information about a parsed json class
 */
public class JsonClassHolder {
    /**
     *  The @JsonClass annotated element. Used for the originatingElements of {@link javax.annotation.processing.Filer#createSourceFile(java.lang.CharSequence, javax.lang.model.element.Element...)}
     */
    public TypeElement originatingElement;
    /**
     * The name of the package where the adapter will be in. Used for the package <>; statement
     */
    public String packageName;
    /**
     * The adapter class name. Used to generate the output filename and the class declaration
     */
    public ClassName adapterClassName;
    /**
     * the type name might be parameterized of the class which the adapter will generate
     */
    public TypeName jsonClassTypeName;
    /**
     * The type variables that the json class has (if any). The adapter will inherit these
     */
    public List<TypeVariableName> typeVariables;

    /**
     * The parsed fields of the class
     * Using a TreeMap now to keep the entries sorted. This ensures that code is
     * always written the exact same way, no matter which JDK you're using.
     */
    public final TreeMap<String, JsonFieldHolder> fieldMap = new TreeMap<>();

    public JsonClassHolder setOriginatingElement(TypeElement originatingElement) {
        this.originatingElement = originatingElement;
        return this;
    }

    public JsonClassHolder setPackageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    public JsonClassHolder setAdapterClassName(ClassName adapterClassName) {
        this.adapterClassName = adapterClassName;
        return this;
    }

    public JsonClassHolder setJsonClassTypeName(TypeName jsonClassTypeName) {
        this.jsonClassTypeName = jsonClassTypeName;
        return this;
    }

    public JsonClassHolder setTypeVariables(List<TypeVariableName> typeVariables) {
        this.typeVariables = typeVariables;
        return this;
    }
}
