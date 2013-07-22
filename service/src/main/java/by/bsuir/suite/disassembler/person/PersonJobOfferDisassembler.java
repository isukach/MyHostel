package by.bsuir.suite.disassembler.person;

import by.bsuir.suite.disassembler.common.BaseDisassembler;
import by.bsuir.suite.domain.person.Person;
import by.bsuir.suite.dto.person.PersonJobOfferDto;
import by.bsuir.suite.util.EntityUtils;
import org.springframework.stereotype.Component;

/**
 * User: Matveyenka Denis
 * Date: 21.07.13
 */
@Component
public class PersonJobOfferDisassembler extends BaseDisassembler<PersonJobOfferDto, Person > {

    @Override
    public PersonJobOfferDto disassemble(Person object) {
        PersonJobOfferDto dto = new PersonJobOfferDto();
        dto.setId(object.getId());
        dto.setFullName(EntityUtils.generatePersonCalendarName(object));

        return dto;
    }
}
