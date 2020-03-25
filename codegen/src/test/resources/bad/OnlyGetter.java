import com.squareup.moshi.JsonClass;

@JsonClass(generateAdapter = true, generator = "java")
public class OnlyGetter {
    public int x;

    private String priv;

    public String getPriv() {
        return priv;
    }
}