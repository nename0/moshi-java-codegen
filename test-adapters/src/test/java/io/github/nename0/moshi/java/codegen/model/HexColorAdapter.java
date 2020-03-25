package io.github.nename0.moshi.java.codegen.model;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

@SuppressWarnings("unused")
public class HexColorAdapter {
    @ToJson
    public String toJson(@HexColor int rgb) {
        return String.format("#%06x", rgb);
    }

    @FromJson
    @HexColor
    public int fromJson(String rgb) {
        return Integer.parseInt(rgb.substring(1), 16);
    }
}
