package by.bsuir.suite.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author i.sukach
 */
public final class StringUtils {

    private StringUtils() {}

    public static String capitalize(String word) {
        if (word == null || word.length() == 0) {
            return word;
        }
        word = word.toLowerCase();
        char [] characters = word.toCharArray();
        characters[0] = Character.toUpperCase(characters[0]);
        return new String(characters);
    }

    public static String getInitial(String word) {
        if (word == null || word.length() == 0) {
            return word;
        }
        char [] chars = word.toCharArray();
        return Character.toUpperCase(chars[0]) + ".";
    }

    public static boolean notNullNotEmpty(String string) {
        return string != null && string.length() != 0;
    }


    public static boolean isNumeric(String number){
        boolean isValid = false;

        String expression = "[-+]?[0-9]*\\.?[0-9]+$";
        CharSequence inputStr = number;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if(matcher.matches()){
            isValid = true;
        }
        return isValid;
    }

    public static String replaceNewLineSymbols(String string) {
        return string.replace("\n", "<br/>");
    }

    public static String emptyString() {
        return "";
    }
}
