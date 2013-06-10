package by.bsuir.suite.dto.duty;

import by.bsuir.suite.dto.common.Dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author i.sukach
 */
public class CalendarMonthDto implements Dto {

    private Long id;

    private int month;

    private int year;

    private boolean enabled;

    private List<CalendarCellDto> cells = new ArrayList<CalendarCellDto>();

    public Long getId() {
        return this.id;
    }

    public CalendarMonthDto setId(Long id) {
        this.id = id;
        return this;
    }

    public int getMonth() {
        return this.month;
    }

    public CalendarMonthDto setMonth(int month) {
        this.month = month;
        return this;
    }

    public int getYear() {
        return this.year;
    }

    public CalendarMonthDto setYear(int year) {
        this.year = year;
        return this;
    }

    public boolean getEnabled() {
        return this.enabled;
    }

    public CalendarMonthDto setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public List<CalendarCellDto> getCells() {
        return this.cells;
    }

    public CalendarMonthDto setCells(List<CalendarCellDto> cells) {
        this.cells = cells;
        return this;
    }
}
