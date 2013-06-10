package by.bsuir.suite.dto.duty;

import by.bsuir.suite.disassembler.duty.DutyStatusDto;
import by.bsuir.suite.dto.common.Dto;
import by.bsuir.suite.dto.common.Versioned;
import by.bsuir.suite.dto.person.CalendarPersonDto;

/**
 * @author i.sukach
 */
public class CalendarDutyDto implements Dto, Versioned {

    private Long id;

    private DutyStatusDto status;

    private CalendarPersonDto person;

    private Long version;

    private String statusComment;

    public Long getId() {
        return this.id;
    }

    public CalendarDutyDto setId(Long id) {
        this.id = id;
        return this;
    }

    public DutyStatusDto getStatus() {
        return this.status;
    }

    public CalendarDutyDto setStatus(DutyStatusDto status) {
        this.status = status;
        return this;
    }

    public CalendarPersonDto getPerson() {
        return this.person;
    }

    public CalendarDutyDto setPerson(CalendarPersonDto person) {
        this.person = person;
        return this;
    }

    @Override
    public Long getVersion() {
        return version;
    }

    public CalendarDutyDto setVersion(Long version) {
        this.version = version;
        return this;
    }

    public String getStatusComment() {
        return statusComment;
    }

    public void setStatusComment(String statusComment) {
        this.statusComment = statusComment;
    }
}
