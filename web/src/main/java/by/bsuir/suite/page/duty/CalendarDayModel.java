package by.bsuir.suite.page.duty;

import by.bsuir.suite.util.DateUtils;
import org.apache.wicket.model.Model;

import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author i.sukach
 */
public class CalendarDayModel extends Model {

    private static final String TODAY_MONTH_RESOURCE_KEY = "dutyPage.today.months.";

    private static final String TODAY_DAY_RESOURCE_KEY = "dutyPage.dayOfWeek.";

    private String dayOfWeek;

    private int dayOfMonth;

    private int year;

    private String month;

    public CalendarDayModel(Locale locale) {
        Calendar calendar = Calendar.getInstance();
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        year = calendar.get(Calendar.YEAR);

        ResourceBundle bundle = ResourceBundle.getBundle("by.bsuir.suite.page.duty.panel.CalendarPanel", locale);
        month = bundle.getString(getResourceKeyForTodayMonth(calendar.get(Calendar.MONTH)));
        dayOfWeek = bundle.getString(getResourceKeyForTodayDay(calendar.get(Calendar.DAY_OF_WEEK)));
    }

    public CalendarDayModel(Calendar calendar, Locale locale) {
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        year = calendar.get(Calendar.YEAR);

        ResourceBundle bundle = ResourceBundle.getBundle("by.bsuir.suite.page.duty.panel.CalendarPanel", locale);
        month = bundle.getString(getResourceKeyForTodayMonth(calendar.get(Calendar.MONTH)));
        dayOfWeek = bundle.getString(getResourceKeyForTodayDay(calendar.get(Calendar.DAY_OF_WEEK)));
    }


    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    private String getResourceKeyForTodayMonth(int month) {
        return TODAY_MONTH_RESOURCE_KEY + DateUtils.getMonthName(month, Locale.ENGLISH).toLowerCase();
    }

    private String getResourceKeyForTodayDay(int dayOfWeek) {
        return TODAY_DAY_RESOURCE_KEY + DateUtils.getDayName(dayOfWeek, Locale.ENGLISH).toLowerCase();
    }
}
