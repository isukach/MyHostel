package by.bsuir.suite.dto.penalty;

import by.bsuir.suite.dto.common.Dto;
import org.apache.wicket.IClusterable;

import java.util.Date;

/**
 * User: CHEB
 */
public class PenaltyDto implements IClusterable, Dto {

    private Long dutyId;

    private Long personId;

    private String fullName;

    private String room;

    private Date date;

    private String description;

    public Long getDutyId() {
        return dutyId;
    }

    public void setDutyId(Long dutyId) {
        this.dutyId = dutyId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PenaltyDto that = (PenaltyDto) o;

        if (description != null ? !description.equals(that.description) : that.description != null) {
            return false;
        }
        if (fullName != null ? !fullName.equals(that.fullName) : that.fullName != null) {
            return false;
        }
        if (dutyId != null ? !dutyId.equals(that.dutyId) : that.dutyId != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = dutyId != null ? dutyId.hashCode() : 0;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
