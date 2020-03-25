package io.github.nename0.moshi.java.codegen.model;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public abstract class TestingModel {
    protected static final Random RNG = new Random();

    public abstract void fillRandomData();

    public abstract boolean equals(Object o);

    protected static String randomString() {
        byte[] bytes = new byte[64];
        RNG.nextBytes(bytes);
        // Yes this will create unprintable characters, but moshi should handle that
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
