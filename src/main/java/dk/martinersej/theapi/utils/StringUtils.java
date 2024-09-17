package dk.martinersej.theapi.utils;

public class StringUtils {

    /**
     * Checks if a string is null or empty.
     *
     * @param str The string to check.
     * @return True if the string is null or empty, false otherwise.
     */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * Checks if a string is null, empty, or contains only whitespace characters.
     *
     * @param str The string to check.
     * @return True if the string is null, empty, or contains only whitespace characters, false otherwise.
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Formats a string with C#-like string formatting. Example: StringUtils.format("Hello, {0}!", "world");
     *
     * @param str The string to format.
     * @param args     The arguments to replace in the string.
     * @return The formatted string.
     */
    public static String format(String str, Object... args) {
        for (int i = 0; i < args.length; i++) {
            str = str.replace("{" + i + "}", args[i].toString());
        }
        return str;
    }

    /**
     * Reverses a string.
     *
     * @param str The string to reverse.
     * @return The reversed string.
     */
    public static String reverse(String str) {
        if (str == null) {
            return null;
        }
        return new StringBuilder(str).reverse().toString();
    }

    /**
     * Capitalizes the first letter of each word in a string.
     *
     * @param str The string to capitalize.
     * @return The capitalized string.
     */
    public static String capitalizeWords(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        String[] words = str.split("\\s+");
        StringBuilder capitalized = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                capitalized.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1).toLowerCase()).append(" ");
            }
        }
        return capitalized.toString().trim();
    }
}
