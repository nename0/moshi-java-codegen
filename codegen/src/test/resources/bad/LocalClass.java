import com.squareup.moshi.JsonClass;

public abstract class LocalClass {
    @JsonClass(generateAdapter = true, generator = "java")
    public static void foo() {
        class PhoneNumber {
            String formattedPhoneNumber = null;
        }
        new PhoneNumber();
    }
}