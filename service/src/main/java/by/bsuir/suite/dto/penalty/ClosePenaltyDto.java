package by.bsuir.suite.dto.penalty;

import by.bsuir.suite.dto.common.Dto;

/**
 * User: CHEB
 */
public class ClosePenaltyDto implements Dto {

    private boolean dutyAdding = false;

    private  boolean workAdding = false;

    private int additionalDuties;

    private int additionalWorkHours;

    private Long personId;

    private Long dutyId;


    public Long getDutyId() {
        return dutyId;
    }

    public void setDutyId(Long dutyId) {
        this.dutyId = dutyId;
    }

    public boolean isDutyAdding() {
        return dutyAdding;
    }

    public void setDutyAdding(boolean dutyAdding) {
        this.dutyAdding = dutyAdding;
    }

    public boolean isWorkAdding() {
        return workAdding;
    }

    public void setWorkAdding(boolean workAdding) {
        this.workAdding = workAdding;
    }

    public int getAdditionalDuties() {
        return additionalDuties;
    }

    public void setAdditionalDuties(int additionalDuties) {
        if (additionalDuties > 0) {
            this.dutyAdding = true;
        }
        this.additionalDuties = additionalDuties;
    }

    public int getAdditionalWorkHours() {
        return additionalWorkHours;
    }

    public void setAdditionalWorkHours(int additionalWorkHours) {
        if (additionalWorkHours > 0) {
            this.workAdding = true;
        }
        this.additionalWorkHours = additionalWorkHours;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }
}
