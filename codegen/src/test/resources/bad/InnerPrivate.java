import com.squareup.moshi.JsonClass;

public class InnerPrivate {
    @JsonClass(generateAdapter = true, generator = "java")
    private static class Inner {
        public int x;
    }
}