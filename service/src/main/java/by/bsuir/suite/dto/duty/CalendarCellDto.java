package by.bsuir.suite.dto.duty;

import by.bsuir.suite.dto.common.Dto;

import java.util.Date;

/**
 * @author i.sukach
 */
public class CalendarCellDto implements Dto, Comparable<CalendarCellDto> {

    private Date date;

    private CalendarDutyDto firstDuty;

    private CalendarDutyDto secondDuty;

    private int dayNumber;

    private boolean enabled = true;

    public Date getDate() {
        return this.date;
    }

    public CalendarCellDto setDate(Date date) {
        this.date = date;
        return this;
    }

    public int getDayNumber() {
        return this.dayNumber;
    }

    public CalendarCellDto setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean getEnabled() {
        return this.enabled;
    }


    public CalendarDutyDto getFirstDuty() {
        return this.firstDuty;
    }

    public CalendarCellDto setFirstDuty(CalendarDutyDto firstDuty) {
        this.firstDuty = firstDuty;
        return this;
    }

    public CalendarDutyDto getSecondDuty() {
        return this.secondDuty;
    }

    public CalendarCellDto setSecondDuty(CalendarDutyDto secondDuty) {
        this.secondDuty = secondDuty;
        return this;
    }

    @Override
    public int compareTo(CalendarCellDto o) {
        if (this.date.before(o.getDate())) {
            return -1;
        } else if (this.date.after(o.getDate())) {
            return 1;
        } else {
            return 0;
        }
    }
}
