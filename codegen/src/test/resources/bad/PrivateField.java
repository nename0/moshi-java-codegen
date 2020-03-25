import com.squareup.moshi.JsonClass;

@JsonClass(generateAdapter = true, generator = "java")
public class PrivateField {
    public int x;

    private String priv;
}