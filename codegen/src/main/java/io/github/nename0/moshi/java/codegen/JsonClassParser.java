/*
 * Copyright (C) 2015 BlueLine Labs
 *
 * Original File Name: JsonObjectProcessor.java
 * From: https://git.io/JvS6z
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
import com.squareup.javapoet.TypeVariableName;
import com.squareup.moshi.JsonClass;
import com.squareup.moshi.Types;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static javax.lang.model.element.Modifier.*;
import static javax.tools.Diagnostic.Kind.ERROR;

/**
 * parses classes with @JsonClass(generateAdapter = true, generator = "java") annotations and creates JsonClassHolder
 */
public class JsonClassParser {

    protected final ProcessingEnvironment processingEnv;
    protected final Elements elements;
    protected final javax.lang.model.util.Types types;
    protected final JsonFieldParser fieldParser;

    public JsonClassParser(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
        this.elements = processingEnv.getElementUtils();
        this.types = processingEnv.getTypeUtils();
        fieldParser = new JsonFieldParser(this);
    }

    public Class<? extends Annotation> getAnnotation() {
        return JsonClass.class;
    }

    public void findAndParseObjects(RoundEnvironment env, Map<String, JsonClassHolder> jsonClassMap) {
        for (Element elem : env.getElementsAnnotatedWith(JsonClass.class)) {
            JsonClass annotation = elem.getAnnotation(JsonClass.class);
            if (!annotation.generateAdapter() || !JsonClassCodegenProcessor.GENERATOR_NAME.equals(annotation.generator())) {
                continue;
            }
            if (elem.getKind() != ElementKind.CLASS || !(elem instanceof TypeElement)) {
                error(elem, "@JsonClass can't be applied to %s - %s: must be a Class type.", elem.getEnclosingElement().toString(), elem.toString());
                continue;
            }
            try {
                processJsonClassAnnotation((TypeElement) elem, jsonClassMap);
            } catch (Exception e) {
                StringWriter stackTrace = new StringWriter();
                e.printStackTrace(new PrintWriter(stackTrace));

                error(elem, "Unable to generate JsonAdapter for @JsonClass. Stack trace incoming:\n%s", stackTrace.toString());
            }
        }
    }

    private void processJsonClassAnnotation(TypeElement classElement, Map<String, JsonClassHolder> jsonClassMap) {
        String className = elements.getBinaryName(classElement).toString();
        JsonClassHolder holder = jsonClassMap.get(className);
        if (holder == null) {
            if (classElement.getModifiers().contains(ABSTRACT)) {
                error(classElement, "@JsonClass can't be applied to %s: must not be abstract", classElement.getQualifiedName());
                return;
            }
            if (!checkClassAccessibleFromPackage(classElement)) {
                return;
            }
            if (!hasSingleArgumentConstructor(classElement)) {
                return;
            }

            String packageName = elements.getPackageOf(classElement).getQualifiedName().toString();
            String[] adapterClassNameParts = Types.generatedJsonAdapterName(className).split("\\.");
            ClassName adapterClassName = ClassName.get(packageName, adapterClassNameParts[adapterClassNameParts.length - 1]);
            List<TypeVariableName> typeVariables = classElement.getTypeParameters().stream().map(TypeVariableName::get).collect(Collectors.toList());

            holder = new JsonClassHolder()
                    .setOriginatingElement(classElement)
                    .setPackageName(packageName)
                    .setAdapterClassName(adapterClassName)
                    .setJsonClassTypeName(TypeName.get(classElement.asType()))
                    .setTypeVariables(typeVariables);

            fieldParser.findAndParseFields(classElement, holder.fieldMap);

            jsonClassMap.put(className, holder);
        }
    }

    private boolean checkClassAccessibleFromPackage(TypeElement element) {
        TypeElement current = element;
        while (true) {
            if (current.getModifiers().contains(PRIVATE)) {
                error(current, "@JsonClass can't be applied to %s: Access to %s is private", element.getQualifiedName(), current.getQualifiedName());
                return false;
            }
            if (!current.getNestingKind().isNested()) {
                break;
            }
            if (!current.getModifiers().contains(STATIC)) {
                error(current, "@JsonClass can't be applied to %s: %s is non-static", element.getQualifiedName(), current.getQualifiedName());
                return false;
            }
            Element parent = current.getEnclosingElement();
            if (parent.asType().getKind() != TypeKind.DECLARED || !(parent instanceof TypeElement)) {
                error(parent, "Expected %s(%s) nesting %s to be a class/interface", parent.getSimpleName(), parent.getKind().toString(), current.getQualifiedName());
                return false;
            }
            current = (TypeElement) parent;
        }
        return true;
    }

    private boolean hasSingleArgumentConstructor(TypeElement element) {
        for (ExecutableElement constructor : ElementFilter.constructorsIn(element.getEnclosedElements())) {
            if (constructor.getParameters().size() > 0) {
                continue;
            }
            if (constructor.getModifiers().contains(PRIVATE)) {
                error(constructor, "@JsonClass can't be applied to %s: The no parameter constructor is private", element.getQualifiedName());
                return false;
            }
            return true;
        }
        error(element, "@JsonClass can't be applied to %s: Requires a non-private no parameter constructor", element.getQualifiedName());
        return false;
    }

    public void error(Element element, String message, Object... args) {
        processingEnv.getMessager().printMessage(ERROR, String.format(message, args), element);
    }
}

