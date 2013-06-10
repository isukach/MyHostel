package by.bsuir.suite.disassembler.person;

import by.bsuir.suite.disassembler.common.BaseDisassembler;
import by.bsuir.suite.domain.person.Person;
import by.bsuir.suite.dto.person.CalendarPersonDto;
import by.bsuir.suite.util.EntityUtils;
import org.springframework.stereotype.Component;

/**
 * @author i.sukach
 */
@Component
public class CalendarPersonDtoDisassembler extends BaseDisassembler<CalendarPersonDto, Person> {

    @Override
    public CalendarPersonDto disassemble(Person person) {
        CalendarPersonDto dto = new CalendarPersonDto();
        dto.setId(person.getId())
                .setName(EntityUtils.generatePersonCalendarName(person))
                .setMaxDuties(person.getRequiredDuties() + person.getExtraDuties());
        if (person.getRoom() != null) {
            dto.setFloorId(person.getRoom().getFloor().getId());
        }
        return dto;
    }
}
