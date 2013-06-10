package by.bsuir.suite.disassembler.person;

import by.bsuir.suite.disassembler.common.BaseDisassembler;
import by.bsuir.suite.domain.person.Person;
import by.bsuir.suite.dto.person.PersonInfoDto;
import by.bsuir.suite.util.EntityUtils;
import org.springframework.stereotype.Component;

/**
 * @author i.sukach
 */
@Component
public class PersonInfoDtoDisassembler extends BaseDisassembler<PersonInfoDto, Person> {

    @Override
    public PersonInfoDto disassemble(Person person) {
        PersonInfoDto dto = new PersonInfoDto();
        dto.setId(person.getId())
                .setUsername(person.getUser().getUsername())
                .setFullName(EntityUtils.generatePersonCalendarName(person));
        if (person.getRoom() != null) {
            dto.setFloorId(person.getRoom().getFloor().getId())
            .setRoomNumber(person.getRoom().getNumber());
        }

        return dto;
    }
}
