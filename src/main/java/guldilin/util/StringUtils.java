package guldilin.util;

public class StringUtils {
    public static boolean isBlank(String str) {
        return str.isEmpty() || str.replaceAll(" ", "").isEmpty();
    }

    public static String normalize(String str) {
        return str.trim().replaceAll("( )+", " ");
    }
}
