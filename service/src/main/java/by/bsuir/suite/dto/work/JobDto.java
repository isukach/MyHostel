package by.bsuir.suite.dto.work;

import by.bsuir.suite.dto.common.Dto;

import java.util.Date;

/**
 * @author d.matveenko
 */
public class JobDto implements Dto {

    private Long id;

    private String description;

    private String hours;

    private boolean validFields;

    private Date date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public boolean isValidFields() {
        return validFields;
    }

    public void setValidFields(boolean validFields) {
        this.validFields = validFields;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
