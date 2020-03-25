import com.squareup.moshi.JsonClass;

@JsonClass(generateAdapter = true, generator = "java")
public enum Enum {
    A,
    B;
}