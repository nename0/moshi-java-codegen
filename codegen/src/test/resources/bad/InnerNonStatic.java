import com.squareup.moshi.JsonClass;

public class InnerNonStatic {
    @JsonClass(generateAdapter = true, generator = "java")
    public class Inner {
        public int x;
    }
}