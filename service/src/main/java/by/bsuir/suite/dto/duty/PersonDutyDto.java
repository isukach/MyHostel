package by.bsuir.suite.dto.duty;

import by.bsuir.suite.disassembler.duty.DutyStatusDto;
import by.bsuir.suite.dto.common.Dto;

import java.util.Calendar;

/**
 * @author i.sukach
 */
public class PersonDutyDto implements Dto, Comparable<PersonDutyDto> {

    private Long id;

    private DutyStatusDto status;

    private Calendar dutyDate;

    private String comment;

    public Long getId() {
        return this.id;
    }

    public PersonDutyDto setId(Long id) {
        this.id = id;
        return this;
    }

    public DutyStatusDto getStatus() {
        return this.status;
    }

    public PersonDutyDto setStatus(DutyStatusDto status) {
        this.status = status;
        return this;
    }

    public Calendar getDutyDate() {
        return this.dutyDate;
    }

    public PersonDutyDto setDutyDate(Calendar dutyDate) {
        this.dutyDate = dutyDate;
        return this;
    }

    @Override
    public int compareTo(PersonDutyDto o) {
        return this.dutyDate.compareTo(o.getDutyDate());
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
