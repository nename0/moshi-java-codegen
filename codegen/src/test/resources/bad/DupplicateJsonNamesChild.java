import com.squareup.moshi.JsonClass;
import com.github.nename0.moshi.java.codegen.model.PublicMember;

@JsonClass(generateAdapter = true, generator = "java")
public class DupplicateJsonNamesChild extends PublicMember {
    public int x;
}
