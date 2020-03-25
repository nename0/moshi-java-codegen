import com.squareup.moshi.JsonClass;

@JsonClass(generateAdapter = true, generator = "java")
public class ConstructorParams {
    public int x;

    ConstructorParams(int x){
        this.x = x;
    }
}