package by.bsuir.suite.disassembler.duty;

import by.bsuir.suite.domain.duty.Duty;
import by.bsuir.suite.dto.duty.CalendarCellDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author i.sukach
 */
@Component
public class CalendarCellDtoDisassembler {

    @Autowired
    private CalendarDutyDtoDisassembler calendarDutyDtoDisassembler;

    public CalendarCellDto disassemble(Duty firstDuty, Duty secondDuty) {
        CalendarCellDto calendarCellDto = new CalendarCellDto()
                .setFirstDuty(calendarDutyDtoDisassembler.disassemble(firstDuty))
                .setSecondDuty(calendarDutyDtoDisassembler.disassemble(secondDuty));

        if (firstDuty.getDate() != null) {
            calendarCellDto.setDate(firstDuty.getDate());
        } else {
            calendarCellDto.setDate(secondDuty.getDate());
        }

        return calendarCellDto;
    }
}
