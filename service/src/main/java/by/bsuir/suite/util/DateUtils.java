package by.bsuir.suite.util;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Contains useful methods to work with dates.
 * @author i.sukach
 */
public final class DateUtils {

    public static final String DATE_FORMAT = "yyyy.MM.dd HH:mm ";

    private DateUtils() {}

    public static int getCurrentMonthNumber() {
        return Calendar.getInstance().get(Calendar.MONTH);
    }

    public static int getCurrentYearNumber() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static boolean datesEqual(Date firstDate, Date secondDate) {
        Calendar first = Calendar.getInstance();
        first.setTime(firstDate);

        Calendar second = Calendar.getInstance();
        second.setTime(secondDate);

        return first.get(Calendar.DAY_OF_MONTH) == second.get(Calendar.DAY_OF_MONTH)
                && first.get(Calendar.MONTH) == second.get(Calendar.MONTH)
                && first.get(Calendar.YEAR) == second.get(Calendar.YEAR);

    }

    public static String getMonthName(int month, Locale locale) {
        return DateFormatSymbols.getInstance(locale).getMonths()[month];
    }

    public static String getDayName(int dayOfWeek, Locale locale) {
        return DateFormatSymbols.getInstance(locale).getWeekdays()[dayOfWeek];
    }

    public static String getMonthNameRus(int month){
        String[] months = {"январь","февраль","март", "апрель", "май", "июнь", "июль", "август", "сентябрь", "октябрь", "ноябрь", "декабрь"};
        return months[month];
    }

    public static String getFormattedDate(long msec){
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        return dateFormat.format(msec).toString();
    }
}
