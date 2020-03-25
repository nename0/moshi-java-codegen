/*
 * Copyright (C) 2015 BlueLine Labs
 *
 * Original File Name: JsonFieldHolder.java
 * From: https://git.io/JvS6d
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
package io.github.nename0.moshi.java.codegen;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonQualifier;

import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.*;
import java.util.stream.Collectors;

public class JsonFieldParser {
    protected final JsonClassParser classParser;
    protected final Elements elements;
    protected final Types types;

    public JsonFieldParser(JsonClassParser classParser) {
        this.classParser = classParser;
        elements = classParser.elements;
        types = classParser.types;
    }

    public void findAndParseFields(TypeElement classElement, Map<String, JsonFieldHolder> fields) {
        TypeElement superclass = classElement;
        DeclaredType declaredClass = (DeclaredType) classElement.asType();
        while (true) {
            addAllFields(classElement, superclass, declaredClass, fields);

            TypeMirror superclassType = superclass.getSuperclass();
            if (superclassType.getKind() == TypeKind.NONE) break;
            superclass = ((TypeElement) types.asElement(superclassType));
            declaredClass = (DeclaredType) types.directSupertypes(declaredClass).get(0);
        }
    }

    private void addAllFields(TypeElement originClass, TypeElement classElement, DeclaredType declaredClass, Map<String, JsonFieldHolder> fields) {
        for (VariableElement fieldElement : ElementFilter.fieldsIn(classElement.getEnclosedElements())) {
            Set<Modifier> modifiers = fieldElement.getModifiers();
            if (!modifiers.contains(Modifier.TRANSIENT) && !modifiers.contains(Modifier.STATIC)) {

                TypeMirror fieldType = types.asMemberOf(declaredClass, fieldElement);
                JsonFieldHolder fieldHolder;
                if (canAccessFieldFromPackage(originClass, fieldElement, elements)) {
                    fieldHolder = createFieldHolder(declaredClass, fieldElement, fieldType, null, null);
                } else {
                    fieldHolder = getGetterAndSetter(originClass, classElement, fieldElement, fieldType);
                    if (fieldHolder == null) {
                        classParser.error(fieldElement, "Cannot access %s in %s. No suitable getter and setter found", fieldElement.getSimpleName(), classElement.getQualifiedName());
                        continue;
                    }
                }

                JsonFieldHolder other = fields.get(fieldHolder.jsonName);
                if (other != null) {
                    classParser.error(fieldElement, "Field %s(JSON Name %s) in %s already present in %s with the same name",
                            fieldHolder.fieldName, fieldHolder.jsonName, classElement.getQualifiedName(), other.fieldContainingClass.toString());
                    continue;
                }
                fields.put(fieldHolder.jsonName, fieldHolder);
            }
        }
    }

    private boolean canAccessFieldFromPackage(Element inPackage, Element toAcccess, Elements elements) {
        Set<Modifier> modifiers = toAcccess.getModifiers();
        if (modifiers.contains(Modifier.PUBLIC)) {
            return true;
        }
        if (modifiers.contains(Modifier.PRIVATE)) {
            return false;
        }
        return elements.getPackageOf(inPackage).toString().equals(elements.getPackageOf(toAcccess).toString());
    }

    private JsonFieldHolder getGetterAndSetter(TypeElement originClass, TypeElement fieldClass, VariableElement fieldElement, TypeMirror fieldType) {
        TypeKind elementTypeKind = fieldType.getKind();

        String elementName = fieldElement.getSimpleName().toString();
        String elementNameLowerCase = elementName.toLowerCase();

        List<String> possibleGetterMethodNames = new ArrayList<>();
        possibleGetterMethodNames.add("get" + elementNameLowerCase);
        if (elementTypeKind == TypeKind.BOOLEAN) {
            possibleGetterMethodNames.add("is" + elementNameLowerCase);
            possibleGetterMethodNames.add("has" + elementNameLowerCase);
            possibleGetterMethodNames.add(elementNameLowerCase);
        }

        List<String> possibleSetterMethodNames = new ArrayList<>();
        possibleSetterMethodNames.add("set" + elementNameLowerCase);

        Deque<DeclaredType> possibleClasses = new LinkedList<>();
        // go up the inheritance chain to find all classes which could contain getters and setters
        for (DeclaredType declaredClass = (DeclaredType) originClass.asType();;) {
            possibleClasses.addFirst(declaredClass);
            TypeElement classElement = (TypeElement) declaredClass.asElement();
            // when we reached the field declaring class stop
            if (fieldClass.equals(classElement)) break;
            boolean isShadowed = ElementFilter.fieldsIn(classElement.getEnclosedElements()).stream()
                    .anyMatch(field -> elements.hides(field, fieldElement));
            // when the variable got shadowed only use superclasses to find getters and setters
            if (isShadowed)
                possibleClasses.clear();

            List<? extends TypeMirror> supertypes = types.directSupertypes(declaredClass);
            if (supertypes.isEmpty()) break;
            declaredClass = (DeclaredType) supertypes.get(0);
        }

        for (DeclaredType declaredClass : possibleClasses) {
            String getter = null, setter = null;
            TypeElement classElement = (TypeElement) declaredClass.asElement();
            for (ExecutableElement methodElement : ElementFilter.methodsIn(classElement.getEnclosedElements())) {
                if (getter == null && methodElement.getParameters().size() == 0) {
                    String methodNameString = methodElement.getSimpleName().toString();
                    String methodNameLowerCase = methodNameString.toLowerCase();
                    if (possibleGetterMethodNames.contains(methodNameLowerCase)) {
                        TypeMirror returnType = ((ExecutableType) types.asMemberOf(declaredClass, methodElement)).getReturnType();
                        if (types.isSameType(returnType, fieldType)) {
                            getter = methodNameString;
                            continue;
                        }
                    }
                }
                if (setter == null && methodElement.getParameters().size() == 1) {
                    String methodNameString = methodElement.getSimpleName().toString();
                    String methodNameLowerCase = methodNameString.toLowerCase();
                    if (possibleSetterMethodNames.contains(methodNameLowerCase)) {
                        TypeMirror paramType = ((ExecutableType) types.asMemberOf(declaredClass, methodElement)).getParameterTypes().get(0);
                        if (types.isSameType(paramType, fieldType)) {
                            setter = methodNameString;
                        }
                    }
                }
            }
            // getter and setter need to be in same class
            if (getter != null && setter != null) {
                return createFieldHolder(declaredClass, fieldElement, fieldType, getter, setter);
            }
        }

        return null;
    }

    private JsonFieldHolder createFieldHolder(DeclaredType accessorClass, VariableElement fieldElement, TypeMirror fieldType, String getter, String setter) {
        JsonFieldHolder fieldHolder = new JsonFieldHolder();

        String fieldName = fieldElement.getSimpleName().toString();
        Json jsonAnnotation = fieldElement.getAnnotation(Json.class);
        fieldHolder.setFieldName(fieldName)
            .setJsonName(jsonAnnotation != null ? jsonAnnotation.name() : fieldName);

        if (getter != null) {
            if (setter == null)
                throw new IllegalArgumentException("There must always be a getter and setter");
        }
        fieldHolder.setGetterMethod(getter)
                .setSetterMethod(setter)
                .setType(TypeName.get(fieldType))
                .setAccessorContainingClass(TypeName.get(accessorClass))
                .setFieldContainingClass(ClassName.get(((TypeElement) fieldElement.getEnclosingElement())));

        // use this to get JsonAdapter without reflection because of Types.getFieldJsonQualifierAnnotations
        // processor.elements.getElementValuesWithDefaults();
        List<? extends AnnotationMirror> annotationMirrors = jsonAnnotations(fieldElement.getAnnotationMirrors());
        checkJsonAnnotations(annotationMirrors);
        List<TypeName> jsonAnnotations = annotationMirrors
                .stream()
                .map(mirror ->
                        TypeName.get(mirror.getAnnotationType())
                )
                .collect(Collectors.toList());

        return fieldHolder.setJsonAnnotations(jsonAnnotations);
    }

    /**
     * Like {@link com.squareup.moshi.internal.Util#jsonAnnotations(java.lang.annotation.Annotation[])}
     * but for javax.lang.io.github.nename0.moshi.java.codegen.model types
     */
    private static List<? extends AnnotationMirror> jsonAnnotations(List<? extends AnnotationMirror> annotations) {
        List<AnnotationMirror> result = null;
        for (AnnotationMirror annotation : annotations) {
            JsonQualifier jsonQualifier = annotation.getAnnotationType().asElement().getAnnotation(JsonQualifier.class);

            if (jsonQualifier != null) {
                if (result == null) result = new ArrayList<>();
                result.add(annotation);
            }
        }
        return result == null ? Collections.emptyList() : result;
    }

    private void checkJsonAnnotations(List<? extends AnnotationMirror> annotations) {
        for (AnnotationMirror jsonAnnotation : annotations) {
            Element element = jsonAnnotation.getAnnotationType().asElement();

            Retention retention = element.getAnnotation(Retention.class);
            if (retention == null || retention.value() != RetentionPolicy.RUNTIME) {
                classParser.error(element, "JsonQualifier @%s must have RUNTIME retention", jsonAnnotation.getAnnotationType().toString());
                return;
            }
            // this will actually never happen, because javax.lang will already throw an parser error if
            // the jsonAnnotation is present on a field, but @Target forbids it
            Target target = element.getAnnotation(Target.class);
            if (target != null && !Arrays.asList(target.value()).contains(ElementType.FIELD)) {
                classParser.error(element, "JsonQualifier @%s must support FIELD target", jsonAnnotation.getAnnotationType().toString());
                return;
            }

        }
    }
}
