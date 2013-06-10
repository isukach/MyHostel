package by.bsuir.suite.dto.person;

import by.bsuir.suite.dto.common.Dto;

/**
 * @author d.matveenko
 */
public class PersonSearchDto implements Dto {
    
    private Long id;
    
    private String firstName;
    
    private String lastName;

    private String group;

    private String room;

    private String userName;
    
    private String firstAndLastName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getUniversityGroup() {
        return group;
    }

    public void setUniversityGroup(String group) {
        this.group = group;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getFirstAndLastName() {
        return firstName + " " + lastName;
    }

    public void setFirstAndLastName(String firstAndLastName) {
        this.firstAndLastName = firstAndLastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
