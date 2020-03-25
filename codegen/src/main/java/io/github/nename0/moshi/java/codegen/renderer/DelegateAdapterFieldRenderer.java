package io.github.nename0.moshi.java.codegen.renderer;

import com.squareup.javapoet.*;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Types;
import io.github.nename0.moshi.java.codegen.JsonClassHolder;
import io.github.nename0.moshi.java.codegen.JsonFieldHolder;

import javax.lang.model.element.Modifier;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Collectors;

public final class DelegateAdapterFieldRenderer {
    private final JsonClassHolder classHolder;
    private final ParameterSpec moshiConstructorParam;
    private final TypeSpec.Builder builder;

    public DelegateAdapterFieldRenderer(JsonClassHolder classHolder, ParameterSpec moshiConstructorParam, TypeSpec.Builder builder) {
        this.classHolder = classHolder;
        this.moshiConstructorParam = moshiConstructorParam;
        this.builder = builder;
    }

    public void addAdapterFields() {
        for (JsonFieldHolder fieldHolder : classHolder.fieldMap.values()) {
            TypeName adapterType = ParameterizedTypeName.get(ClassName.get(JsonAdapter.class), fieldHolder.type.box());
            fieldHolder.adapterField = FieldSpec.builder(adapterType, getUniqueAdapterFieldName(classHolder, fieldHolder))
                    .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                    .build();
            builder.addField(fieldHolder.adapterField);
        }
    }

    public void addAdapterInitializers(MethodSpec.Builder constructorBuilder, TypeRenderer typeRenderer) {
        int annotationCount = 0;
        for (JsonFieldHolder fieldHolder : classHolder.fieldMap.values()) {
            List<CodeBlock> parameters = new ArrayList<>();
            parameters.add(typeRenderer.render(fieldHolder.type));

            if (fieldHolder.jsonAnnotations.size() > 0) {
                ParameterizedTypeName fieldType = ParameterizedTypeName.get(Set.class, Types.subtypeOf(Annotation.class));
                String fieldName = "ANNOTATIONS_" + fieldHolder.fieldName.toUpperCase() + "_" + (annotationCount++);
                FieldSpec annotations = FieldSpec.builder(fieldType, fieldName)
                        .addModifiers(Modifier.PRIVATE, Modifier.FINAL, Modifier.STATIC)
                        .initializer("$T.getFieldJsonQualifierAnnotations($T.class, $S)", Types.class, fieldHolder.fieldContainingClass, fieldHolder.fieldName)
                        .build();
                builder.addField(annotations);

                parameters.add(CodeBlock.of("$N", annotations));
                // fieldHolder.jsonAnnotations.stream()
                //         .map(type -> CodeBlock.of("$T.class", type))
                //         .collect(CodeBlock.joining(",$W"))));
            } else
                parameters.add(CodeBlock.of("$T.emptySet()", Collections.class));
            parameters.add(CodeBlock.of("$S", fieldHolder.jsonName));
            CodeBlock parametersCode = parameters.stream().collect(CodeBlock.joining(",$W"));
            constructorBuilder.addStatement("$N = $N.adapter($L)", fieldHolder.adapterField, moshiConstructorParam, parametersCode);
        }
    }

    private static String getUniqueAdapterFieldName(JsonClassHolder classHolder, JsonFieldHolder fieldHolder) {
        List<JsonFieldHolder> othersSameName = classHolder.fieldMap.values().stream()
                .filter(otherField -> otherField != fieldHolder && otherField.fieldName.equals(fieldHolder.fieldName))
                .collect(Collectors.toList());
        String fieldName = "adapter_" + fieldHolder.fieldName;
        if (othersSameName.isEmpty()) {
            return fieldName;
        }
        List<String> classNames = fieldHolder.fieldContainingClass.simpleNames();
        Optional<JsonFieldHolder> othersAlsoSameClassName = othersSameName.stream()
                .filter(otherField -> otherField.fieldContainingClass.simpleNames().equals(classNames))
                .findFirst();
        if (!othersAlsoSameClassName.isPresent()) {
            return fieldName + "_" + String.join("_", classNames);
        }
        String fullClassName = fieldHolder.fieldContainingClass.canonicalName();
        fullClassName = String.join("_", fullClassName.split("\\."));
        return fieldName + "_" + fullClassName;

    }
}