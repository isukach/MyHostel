package by.bsuir.suite.disassembler.duty;

import by.bsuir.suite.disassembler.common.BaseDisassembler;
import by.bsuir.suite.disassembler.person.CalendarPersonDtoDisassembler;
import by.bsuir.suite.domain.duty.Duty;
import by.bsuir.suite.dto.duty.CalendarDutyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author i.sukach
 */
@Component
public class CalendarDutyDtoDisassembler extends BaseDisassembler<CalendarDutyDto, Duty> {

    @Autowired
    private CalendarPersonDtoDisassembler calendarPersonDtoDisassembler;

    @Override
    public CalendarDutyDto disassemble(Duty duty) {
        CalendarDutyDto dto = new CalendarDutyDto();
        dto.setId(duty.getId())
                .setStatus(DutyStatusDto.valueOf(duty.getStatus().toString()))
                .setVersion(duty.getVersion())
                .setStatusComment(duty.getStatusComment());

        if (duty.getPerson() != null) {
            dto.setPerson(calendarPersonDtoDisassembler.disassemble(duty.getPerson()));
        }
        return dto;
    }
}
