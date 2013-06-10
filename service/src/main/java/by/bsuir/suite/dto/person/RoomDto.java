package by.bsuir.suite.dto.person;

import by.bsuir.suite.dto.common.Dto;

import java.util.List;

/**
 * @author d.shemerey
 */
public class RoomDto implements Dto {

    private Long id;

    private String roomNumber;

    private String floorNumber;

    private String hostelNumber;

    private List<PersonInfoDto> persons;

    public Long getId() {
        return this.id;
    }

    public RoomDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getRoomNumber() {
        return this.roomNumber;
    }

    public RoomDto setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
        return this;
    }

    public List<PersonInfoDto> getPersons() {
        return this.persons;
    }

    public RoomDto setPersons(List<PersonInfoDto> persons) {
        this.persons = persons;
        return this;
    }

    public String getFloorNumber() {
        return this.floorNumber;
    }

    public RoomDto setFloorNumber(String floorNumber) {
        this.floorNumber = floorNumber;
        return this;
    }

    public String getHostelNumber() {
        return this.hostelNumber;
    }

    public RoomDto setHostelNumber(String hostelNumber) {
        this.hostelNumber = hostelNumber;
        return this;
    }
}
