import com.squareup.moshi.JsonClass;

@JsonClass(generateAdapter = true, generator = "java")
public abstract class AbstractClass {
    public int x;

    public abstract void foo();
}