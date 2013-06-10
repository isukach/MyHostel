package by.bsuir.suite.disassembler.person;

import by.bsuir.suite.disassembler.common.BaseDisassembler;
import by.bsuir.suite.domain.person.Person;
import by.bsuir.suite.dto.person.PersonSearchDto;
import org.springframework.stereotype.Component;

/**
 * @author d.matveenko
 */
@Component
public class PersonSearchDtoDisassembler extends BaseDisassembler<PersonSearchDto, Person> {
    
    @Override
    public PersonSearchDto disassemble(Person person) {
        PersonSearchDto dto = new PersonSearchDto();
        dto.setId(person.getId());
        dto.setFirstName(person.getFirstName());
        dto.setLastName(person.getLastName());
        dto.setUserName(person.getUser().getUsername());
        if (person.getRoom() != null) {
            dto.setRoom(person.getRoom().getFloor().getNumber() + person.getRoom().getNumber());
        }
        dto.setUniversityGroup(person.getUniversityGroup());

        return dto;
    }
}
