package io.github.nename0.moshi.java.codegen;

import com.google.testing.compile.JavaFileObjects;
import io.github.nename0.moshi.java.codegen.JsonClassCodegenProcessor;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.google.common.truth.Truth.assert_;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

/**
 * This class can be used to debug compilation with the JsonClasses from the test-adapter sub-project
 */
public class DebugProcessingTest {
    @Test
    public void debugProcessing() throws IOException {
        assert_().about(javaSource())
                .that(JavaFileObjects.forResource("io/github/nename0/moshi/java/codegen/model/complex/GenericInheritanceChain.java"))
                .processedWith(new JsonClassCodegenProcessor())
                .compilesWithoutError();
    }
}
