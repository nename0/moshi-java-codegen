package com.github.nename0.moshi.java.codegen;

import com.github.nename0.moshi.java.codegen.model.PackageLocalMember;
import com.github.nename0.moshi.java.codegen.model.ProtectedMember;
import com.github.nename0.moshi.java.codegen.model.PublicMember;
import com.google.testing.compile.JavaFileObjects;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assert_;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

public class FailingCompilationTest {
    @Test
    public void privateGenerator() {
        assert_().about(javaSource())
                .that(JavaFileObjects.forResource("bad/PrivateConstructor.java"))
                .processedWith(new JsonClassCodegenProcessor())
                .failsToCompile()
                .withErrorContaining("@JsonClass can't be applied to PrivateConstructor: The no parameter constructor is private");
    }

    @Test
    public void constructorParams() {
        assert_().about(javaSource())
                .that(JavaFileObjects.forResource("bad/ConstructorParams.java"))
                .processedWith(new JsonClassCodegenProcessor())
                .failsToCompile()
                .withErrorContaining("@JsonClass can't be applied to ConstructorParams: Requires a non-private no parameter constructor");
    }

    @Test
    public void privateField() {
        assert_().about(javaSource())
                .that(JavaFileObjects.forResource("bad/PrivateField.java"))
                .processedWith(new JsonClassCodegenProcessor())
                .failsToCompile()
                .withErrorContaining("Cannot access priv in PrivateField. No suitable getter and setter found");
    }

    @Test
    public void onlyGetter() {
        assert_().about(javaSource())
                .that(JavaFileObjects.forResource("bad/OnlyGetter.java"))
                .processedWith(new JsonClassCodegenProcessor())
                .failsToCompile()
                .withErrorContaining("Cannot access priv in OnlyGetter. No suitable getter and setter found");
    }

    @Test
    public void onlySetter() {
        assert_().about(javaSource())
                .that(JavaFileObjects.forResource("bad/OnlySetter.java"))
                .processedWith(new JsonClassCodegenProcessor())
                .failsToCompile()
                .withErrorContaining("Cannot access priv in OnlySetter. No suitable getter and setter found");
    }

    @Test
    public void interfacesNotSupported() {
        assert_().about(javaSource())
                .that(JavaFileObjects.forResource("bad/Interface.java"))
                .processedWith(new JsonClassCodegenProcessor())
                .failsToCompile()
                .withErrorContaining("must be a Class type");
    }

    @Test
    public void interfacesDoNotErrorWhenGeneratorNotSet() {
        assert_().about(javaSource())
                .that(JavaFileObjects.forResource("bad/InterfaceCustomGenerator.java"))
                .processedWith(new JsonClassCodegenProcessor())
                .compilesWithoutError();
    }

    @Test
    public void abstractClassesNotSupported() {
        assert_().about(javaSource())
                .that(JavaFileObjects.forResource("bad/AbstractClass.java"))
                .processedWith(new JsonClassCodegenProcessor())
                .failsToCompile()
                .withErrorContaining("@JsonClass can't be applied to AbstractClass: must not be abstract");
    }

    @Test
    public void innerPrivateClassesNotSupported() {
        assert_().about(javaSource())
                .that(JavaFileObjects.forResource("bad/InnerPrivate.java"))
                .processedWith(new JsonClassCodegenProcessor())
                .failsToCompile()
                .withErrorContaining("@JsonClass can't be applied to InnerPrivate.Inner: Access to InnerPrivate.Inner is private");
    }

    @Test
    public void innerNonStaticClassesNotSupported() {
        assert_().about(javaSource())
                .that(JavaFileObjects.forResource("bad/InnerNonStatic.java"))
                .processedWith(new JsonClassCodegenProcessor())
                .failsToCompile()
                .withErrorContaining("@JsonClass can't be applied to InnerNonStatic.Inner: InnerNonStatic.Inner is non-static");
    }

    @Test
    public void enumClassesNotSupported() {
        assert_().about(javaSource())
                .that(JavaFileObjects.forResource("bad/Enum.java"))
                .processedWith(new JsonClassCodegenProcessor())
                .failsToCompile()
                .withErrorContaining("@JsonClass can't be applied to unnamed package - Enum: must be a Class type.");
    }

    @Test
    public void localClassesNotSupported() {
        assert_().about(javaSource())
                .that(JavaFileObjects.forResource("bad/LocalClass.java"))
                .processedWith(new JsonClassCodegenProcessor())
                .failsToCompile()
                .withErrorContaining("@JsonClass can't be applied to LocalClass - foo(): must be a Class type.");
    }

    @Test
    public void multipleErrors() {
        assert_().about(javaSource())
                .that(JavaFileObjects.forResource("bad/MultipleErrors.java"))
                .processedWith(new JsonClassCodegenProcessor())
                .failsToCompile()
                .withErrorContaining("Cannot access x in MultipleErrors. No suitable getter and setter found")
                .and()
                .withErrorContaining("Cannot access priv in MultipleErrors. No suitable getter and setter found")
                .and()
                .withErrorContaining("Field other(JSON Name same) in MultipleErrors already present in MultipleErrors with the same name");
    }

    @Test
    public void nonRuntimeQualifier() {
        assert_().about(javaSource())
                .that(JavaFileObjects.forResource("bad/NonRuntimeQualifier.java"))
                .processedWith(new JsonClassCodegenProcessor())
                .failsToCompile()
                .withErrorContaining("JsonQualifier @UpperCase must have RUNTIME retention");
    }

    @Test
    public void protectedOutsidePackage() {
        assert_().about(javaSource())
                .that(JavaFileObjects.forResource("bad/ProtectedOutsidePackage.java"))
                .processedWith(new JsonClassCodegenProcessor())
                .failsToCompile()
                .withErrorContaining("Cannot access x in " + ProtectedMember.class.getName() + ". No suitable getter and setter found");
    }

    @Test
    public void packageLocalOutsidePackage() {
        assert_().about(javaSource())
                .that(JavaFileObjects.forResource("bad/PackageLocalOutsidePackage.java"))
                .processedWith(new JsonClassCodegenProcessor())
                .failsToCompile()
                .withErrorContaining("Cannot access x in " + PackageLocalMember.class.getName() + ". No suitable getter and setter found");
    }

    @Test
    public void dupplicateJsonNames() {
        assert_().about(javaSource())
                .that(JavaFileObjects.forResource("bad/DupplicateJsonNames.java"))
                .processedWith(new JsonClassCodegenProcessor())
                .failsToCompile()
                .withErrorContaining("Field b(JSON Name a) in DupplicateJsonNames already present in DupplicateJsonNames with the same name");
    }

    @Test
    public void dupplicateJsonNamesChild() {
        assert_().about(javaSource())
                .that(JavaFileObjects.forResource("bad/DupplicateJsonNamesChild.java"))
                .processedWith(new JsonClassCodegenProcessor())
                .failsToCompile()
                .withErrorContaining("Field x(JSON Name x) in " + PublicMember.class.getName() + " already present in DupplicateJsonNamesChild with the same name");
    }
}
