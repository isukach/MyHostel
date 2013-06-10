package by.bsuir.suite.dto.duty;

import by.bsuir.suite.dto.common.Dto;

/**
 * @author i.sukach
 */
public class DutyStatisticsDto implements Dto {

    private double completedDutiesPercentage;

    private int completedDuties;

    private int duties;

    private int extraDuties;

    public double getCompletedDutiesPercentage() {
        return completedDutiesPercentage;
    }

    public void setCompletedDutiesPercentage(double completedDutiesPercentage) {
        this.completedDutiesPercentage = completedDutiesPercentage;
    }

    public int getCompletedDuties() {
        return completedDuties;
    }

    public void setCompletedDuties(int completedDuties) {
        this.completedDuties = completedDuties;
    }

    public int getDuties() {
        return duties;
    }

    public void setDuties(int duties) {
        this.duties = duties;
    }

    public int getExtraDuties() {
        return extraDuties;
    }

    public void setExtraDuties(int extraDuties) {
        this.extraDuties = extraDuties;
    }
}
