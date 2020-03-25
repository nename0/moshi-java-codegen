import com.squareup.moshi.Json;
import com.squareup.moshi.JsonClass;

@JsonClass(generateAdapter = true, generator = "java")
public class DupplicateJsonNames {
    public int a;

    @Json(name="a")
    public int b;
}