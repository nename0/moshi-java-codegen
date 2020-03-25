import com.squareup.moshi.JsonClass;

@JsonClass(generateAdapter = true, generator = "custom")
public interface InterfaceCustomGenerator {
    void foo();
}