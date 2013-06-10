package by.bsuir.suite.dto.person;

import by.bsuir.suite.dto.common.Dto;

/**
 * @author i.sukach
 */
public class CalendarPersonDto implements Dto {

    private Long id;

    private String name;

    private int maxDuties;

    private Long floorId;

    public Long getId() {
        return this.id;
    }

    public CalendarPersonDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public CalendarPersonDto setName(String name) {
        this.name = name;
        return this;
    }

    public Long getFloorId() {
        return this.floorId;
    }

    public CalendarPersonDto setFloorId(Long floorId) {
        this.floorId = floorId;
        return this;
    }

    public int getMaxDuties() {
        return this.maxDuties;
    }

    public CalendarPersonDto setMaxDuties(int maxDuties) {
        this.maxDuties = maxDuties;
        return this;
    }
}
