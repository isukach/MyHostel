package by.bsuir.suite.util;

import java.util.Calendar;

/**
 * @author i.sukach
 */
public final class CalendarUtils {

    private static final int CALENDAR_SIZE = 42;

    private CalendarUtils() {}

    public static int [] getTopAndBottomMargins(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        int dayOfWeekForMonthStart = getDayOfWeekForMonthStart(calendar);
        int [] margins = new int[2];
        int topMargin = getTopMargin(dayOfWeekForMonthStart);
        margins[0] = topMargin;
        margins[1] = getBottomMargin(topMargin, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        return margins;
    }

    private static int getTopMargin(int dayNumber) {
        switch (dayNumber) {
            case 3: return 1;
            case 4: return 2;
            case 5: return 3;
            case 6: return 4;
            case 7: return 5;
            case 1: return 6;
            case 2: return 7;
        }
        return 0;
    }

    private static int getBottomMargin(int topMargin, int daysInMonth) {
        return CALENDAR_SIZE - topMargin - daysInMonth;
    }

    private static int getDayOfWeekForMonthStart(Calendar calendar) {
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }
}
