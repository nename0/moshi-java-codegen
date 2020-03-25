import com.squareup.moshi.JsonClass;
import com.squareup.moshi.JsonQualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@JsonQualifier
@Target({ElementType.FIELD})
@interface UpperCase {

}

@JsonClass(generateAdapter = true, generator = "java")
public class NonRuntimeQualifier {
    @UpperCase
    public String name;
}