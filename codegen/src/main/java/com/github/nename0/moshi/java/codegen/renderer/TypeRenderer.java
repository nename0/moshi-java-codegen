/*
 * Copyright (C) 2018 Square, Inc.
 *
 * Original File Name:  TypeRenderer.kt
 * From: https://git.io/JvS6R
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
package com.github.nename0.moshi.java.codegen.renderer;

import com.squareup.javapoet.*;
import com.squareup.moshi.Types;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Renders type literals using {@link com.squareup.moshi.Types} like `Types.newParameterizedType(List::class.java, String::class.java)`.
 * Rendering is pluggable so that type variables can either be resolved or emitted as other code
 * blocks.
 */
public final class TypeRenderer {
    private final ParameterSpec typeConstructorParam;
    private final List<String> typeVarNames;

    public TypeRenderer(ParameterSpec typeConstructorParam, List<TypeVariableName> typeVarNames) {
        this.typeConstructorParam = typeConstructorParam;
        this.typeVarNames = typeVarNames.stream().map(t -> t.name).collect(Collectors.toList());
    }

    public CodeBlock render(TypeName typeName) {
        if (typeName.isPrimitive() || typeName instanceof ClassName) {
            // for primitives we really need the unboxed value here. As @JsonQualifier might require the primitive type
            // The HexColor Example needs this:
            // https://github.com/square/moshi/blob/b413423d0575849db7bdc6fcfbdb7d99f7e3a2c3/README.md#alternate-type-adapters-with-jsonqualifier
            return CodeBlock.of("$T.class", typeName);
        }
        if (typeName instanceof ArrayTypeName arrayTypeName) {
            // Types.arrayOf()
            return CodeBlock.of("$T$Z.arrayOf($Z$L)", Types.class, render(arrayTypeName.componentType));
        }
        if (typeName instanceof ParameterizedTypeName pTypeName) {
            // Types.newParameterizedType() / Types.newParameterizedTypeWithOwner()
            CodeBlock.Builder builder = CodeBlock.builder()
                    .add("$T$Z.", Types.class);
            ClassName enclosingClassName = pTypeName.rawType.enclosingClassName();
            if (enclosingClassName != null)
                builder.add("newParameterizedTypeWithOwner($Z$L,$W", render(enclosingClassName));
            else
                builder.add("newParameterizedType($Z");
            builder.add("$T.class", pTypeName.rawType);
            for (TypeName typeArgument : pTypeName.typeArguments) {
                builder.add(",$W$L", render(typeArgument));
            }
            return builder.add(")").build();
        }
        if (typeName instanceof WildcardTypeName wildcardTypeName) {
            // Types.supertypeOf() / Types.subtypeOf()
            TypeName target;
            String method;
            if (wildcardTypeName.lowerBounds.size() == 1) {
                target = wildcardTypeName.lowerBounds.get(0);
                method = "supertypeOf";
            } else if (wildcardTypeName.upperBounds.size() == 1) {
                target = wildcardTypeName.upperBounds.get(0);
                method = "subtypeOf";
            } else {
                throw new IllegalArgumentException(
                        "Unrepresentable wildcard type. Cannot have more than one bound: " + wildcardTypeName);
            }
            return CodeBlock.of("$T$Z.$N($Z$L)", Types.class, method, render(target));
        }
        if (typeName instanceof TypeVariableName typeVariableName) {
            // from second constructor param
            return CodeBlock.of("$N[$L]", typeConstructorParam, typeVarNames.indexOf(typeVariableName.name));
        }
        throw new IllegalArgumentException("Unrepresentable type: " + typeName);
    }
}
