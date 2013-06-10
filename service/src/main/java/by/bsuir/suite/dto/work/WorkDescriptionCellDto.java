package by.bsuir.suite.dto.work;

import by.bsuir.suite.dto.common.Dto;

/**
 * @author : a.garelik
 */
public class WorkDescriptionCellDto implements Dto {

    private String date;
    private String hours;
    private String description;
    private int relativePosition;

    public WorkDescriptionCellDto() {
    }

    public WorkDescriptionCellDto(String date, String hours, String description, int relativePosition) {
        this.date = date;
        this.hours = hours;
        this.description = description;
        this.relativePosition = relativePosition;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public int getRelativePosition() {
        return relativePosition;
    }

    public void setRelativePosition(int relativePosition) {
        this.relativePosition = relativePosition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
