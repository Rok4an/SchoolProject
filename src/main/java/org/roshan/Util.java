package org.roshan;

public class Util {

    /**
     * Converts each word in a string to title case.
     * Each word is assumed to be separated by a single space.
     * The first letter of each word is converted to uppercase,
     * and the remaining letters are converted to lowercase.
     * @param str the input string to convert
     * @return the converted string in title case,
     *         or the original string if it is null or empty
     */
    public static String toTitleCase(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        String[] words = str.split(" ");
        String result = "";

        for (String word : words) {
            if (!word.isEmpty()) {
                String firstLetter = word.substring(0, 1).toUpperCase();
                String restOfName = word.substring(1).toLowerCase();
                result = result + firstLetter + restOfName + " ";
            }
        }

        return result.trim();
    }
}