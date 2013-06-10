package by.bsuir.suite.util;

import by.bsuir.suite.domain.person.Person;

import java.util.Calendar;
import java.util.Date;

/**
 * @author i.sukach
 */
public final class EntityUtils {

    private EntityUtils() {}

    public static String generateDutyKey(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        return String.valueOf(dayOfMonth);
    }

    public static String generatePersonCalendarName(Person person) {
        StringBuilder builder = new StringBuilder();
        builder.append(StringUtils.capitalize(person.getLastName())).append(" ")
                .append(StringUtils.getInitial(person.getFirstName()))
                .append(person.getMiddleName() != null ?
                        StringUtils.getInitial(person.getMiddleName()) : "");
        return builder.toString();
    }
}
