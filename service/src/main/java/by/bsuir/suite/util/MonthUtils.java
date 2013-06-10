package by.bsuir.suite.util;

import by.bsuir.suite.domain.duty.Duty;
import by.bsuir.suite.domain.duty.DutyShift;
import by.bsuir.suite.domain.duty.Month;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author i.sukach
 */
public final class MonthUtils {

    private MonthUtils() {
    }

    public static void fillMonthWithDuties(Month month, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        Set<Duty> duties = new HashSet<Duty>();
        for (int i = 1; i <= daysInMonth; i++) {
            calendar.set(Calendar.DAY_OF_MONTH, i);
            for (int j = 1; j <= 2; j++) {
                Duty duty = new Duty();
                duty.setDate(calendar.getTime());
                duty.setShift(DutyShift.fromNumber(j));
                duties.add(duty);
            }
        }
        month.setDuties(duties);
    }
}
