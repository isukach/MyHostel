package by.bsuir.suite.dto.person;

import by.bsuir.suite.dto.common.Dto;

/**
 * @author i.sukach
 */
public class PersonInfoDto implements Dto {

    private Long id;

    private String username;

    private String fullName;

    private String roomNumber;

    private Long floorId;

    public Long getId() {
        return this.id;
    }

    public PersonInfoDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return this.username;
    }

    public PersonInfoDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFullName() {
        return this.fullName;
    }

    public PersonInfoDto setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public Long getFloorId() {
        return this.floorId;
    }

    public PersonInfoDto setFloorId(Long floorId) {
        this.floorId = floorId;
        return this;
    }

    public String getRoomNumber() {
        return this.roomNumber;
    }

    public PersonInfoDto setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
        return this;
    }
}
