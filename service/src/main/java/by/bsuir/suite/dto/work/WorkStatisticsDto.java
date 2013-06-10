package by.bsuir.suite.dto.work;

import by.bsuir.suite.dto.common.Dto;

/**
 * @author i.sukach
 */
public class WorkStatisticsDto implements Dto {

    private int completedHours;

    private int requiredHours;

    private int extraHours;

    private double workCompletedPercentage;

    public int getCompletedHours() {
        return completedHours;
    }

    public void setCompletedHours(int completedHours) {
        this.completedHours = completedHours;
    }

    public int getRequiredHours() {
        return requiredHours;
    }

    public void setRequiredHours(int requiredHours) {
        this.requiredHours = requiredHours;
    }

    public int getExtraHours() {
        return extraHours;
    }

    public void setExtraHours(int extraHours) {
        this.extraHours = extraHours;
    }

    public double getWorkCompletedPercentage() {
        return workCompletedPercentage;
    }

    public void setWorkCompletedPercentage(double workCompletedPercentage) {
        this.workCompletedPercentage = workCompletedPercentage;
    }
}
