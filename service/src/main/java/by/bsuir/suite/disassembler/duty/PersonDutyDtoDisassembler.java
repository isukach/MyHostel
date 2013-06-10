package by.bsuir.suite.disassembler.duty;

import by.bsuir.suite.disassembler.common.BaseDisassembler;
import by.bsuir.suite.domain.duty.Duty;
import by.bsuir.suite.dto.duty.PersonDutyDto;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * @author i.sukach
 */
@Component
public class PersonDutyDtoDisassembler extends BaseDisassembler<PersonDutyDto, Duty> {

    @Override
    public PersonDutyDto disassemble(Duty duty) {
        PersonDutyDto dto = new PersonDutyDto();
        Calendar dutyDate = Calendar.getInstance();
        dutyDate.setTime(duty.getDate());
        dto.setId(duty.getId())
                .setStatus(DutyStatusDto.valueOf(duty.getStatus().toString()))
                .setDutyDate(dutyDate)
                .setComment(duty.getStatusComment());
        return dto;
    }
}
