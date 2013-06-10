package by.bsuir.suite.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author a.garelik
 */
public final class CommonUtils {

    private static final String HOURS_REGEX = "\\d+";

    private CommonUtils() {
    }

    public static int getCourseByUniversityGroup(String group){
        if (isNumber(group)) {
            int firstNumberOfGroup = Integer.valueOf(group.substring(0,1));
            int year = Integer.valueOf(String.valueOf(DateUtils.getCurrentYearNumber()).substring(3,4));
            int month = DateUtils.getCurrentMonthNumber();
            int correction = 0;
            if (month >= 9 && month <= 12) {
                correction = 1;
            }
            if (year - firstNumberOfGroup >= 0) {
                return year - firstNumberOfGroup + correction;
            } else {
                return year - firstNumberOfGroup + 10 + correction;
            }
        }
        return -1;
    }


    public static boolean isNumber(String string) {
        Pattern pattern = Pattern.compile(HOURS_REGEX);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }
}
