import com.squareup.moshi.JsonClass;

@JsonClass(generateAdapter = true, generator = "java")
public interface Interface {
    void foo();
}