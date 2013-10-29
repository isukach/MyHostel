package by.bsuir.suite.dto.statistics;

import by.bsuir.suite.dto.common.Dto;
import org.apache.wicket.IClusterable;

/**
 * @author i.sukach
 */
public class FloorStatisticsDto implements IClusterable, Dto {

    private long personId;

    private String firstName;

    private String lastName;

    private String workCompletion;

    private String dutyCompletion;

    private String room;

    private String group;

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getWorkCompletion() {
        return workCompletion;
    }

    public void setWorkCompletion(String workCompletion) {
        this.workCompletion = workCompletion;
    }

    public String getDutyCompletion() {
        return dutyCompletion;
    }

    public void setDutyCompletion(String dutyCompletion) {
        this.dutyCompletion = dutyCompletion;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
