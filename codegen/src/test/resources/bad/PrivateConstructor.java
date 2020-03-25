import com.squareup.moshi.JsonClass;

@JsonClass(generateAdapter = true, generator = "java")
public class PrivateConstructor {
    public int x;

    private PrivateConstructor(){}
}