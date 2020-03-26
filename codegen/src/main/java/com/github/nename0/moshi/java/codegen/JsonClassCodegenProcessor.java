/*
 * Copyright (C) 2015 BlueLine Labs
 *
 * Original File Name: JsonAnnotationProcessor.java
 * From: https://git.io/JvS6Y
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

import com.google.auto.service.AutoService;
import com.github.nename0.moshi.java.codegen.renderer.JsonAdapterRenderer;
import net.ltgt.gradle.incap.IncrementalAnnotationProcessor;
import net.ltgt.gradle.incap.IncrementalAnnotationProcessorType;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@IncrementalAnnotationProcessor(IncrementalAnnotationProcessorType.ISOLATING)
@AutoService(Processor.class)
public class JsonClassCodegenProcessor extends AbstractProcessor {

    public static final String GENERATOR_NAME = "java";

    Elements elementUtils;
    Types typeUtils;
    Filer filer;
    Messager messager;

    JsonClassParser jsonClassParser;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementUtils = processingEnv.getElementUtils();
        typeUtils = processingEnv.getTypeUtils();
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();

        jsonClassParser = new JsonClassParser(processingEnv);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(jsonClassParser.getAnnotation().getName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        HashMap<String, JsonClassHolder> jsonClassMap = new HashMap<>();
        jsonClassParser.findAndParseObjects(roundEnv, jsonClassMap);
        
        for (Map.Entry<String, JsonClassHolder> entry : jsonClassMap.entrySet()) {
            JsonClassHolder classHolder = entry.getValue();
            try {
                new JsonAdapterRenderer(classHolder).writeTo(filer);
            } catch (IOException e) {
                error("Exception occurred while attempting to write JsonAdapter for type %s. Exception message: %s", entry.getKey(), e.getMessage());
            }
        }

        return true;
    }

    private void error(String message, Object... args) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, String.format(message, args));
    }
}
