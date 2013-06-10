package by.bsuir.suite.dto.work;

import by.bsuir.suite.dto.common.Dto;

/**
 * @author d.matveenko
 */
public class WorkProgressBarDto implements Dto {

    private Long id;
    
    private int totalHours;

    private int requiredHours;

    public int getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(int totalHours) {
        this.totalHours = totalHours;
    }

    public int getRequiredHours() {
        return requiredHours;
    }

    public void setRequiredHours(int requiredHours) {
        this.requiredHours = requiredHours;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
