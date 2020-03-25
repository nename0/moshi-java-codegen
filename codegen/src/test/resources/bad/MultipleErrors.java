import com.squareup.moshi.Json;
import com.squareup.moshi.JsonClass;

@JsonClass(generateAdapter = true, generator = "java")
public class MultipleErrors {
    private int x;

    private String priv;

    public String same;

    @Json(name = "same")
    public String other;
}