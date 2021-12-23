package guldilin.util;

public class StringUtils {
    public static boolean isBlank(String str) {
        return str.isEmpty() || str.replaceAll(" ", "").isEmpty();
    }

}
