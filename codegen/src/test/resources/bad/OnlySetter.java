import com.squareup.moshi.JsonClass;

@JsonClass(generateAdapter = true, generator = "java")
public class OnlySetter {
    public int x;

    private String priv;

    public void setPriv(String priv) {
        this.priv = priv;
    }
}