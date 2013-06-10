package by.bsuir.suite.assembler.duty;

import by.bsuir.suite.assembler.common.Assembler;
import by.bsuir.suite.dao.person.PersonDao;
import by.bsuir.suite.domain.duty.Duty;
import by.bsuir.suite.domain.duty.DutyStatus;
import by.bsuir.suite.dto.duty.CalendarDutyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author i.sukach
 */
@Component
public class DutyAssembler implements Assembler<CalendarDutyDto, Duty> {

    @Autowired
    private PersonDao personDao;

    @Override
    public Duty assemble(CalendarDutyDto dto) {
        return updateEntityFields(dto, new Duty());
    }

    @Override
    public Duty updateEntityFields(CalendarDutyDto dto, Duty duty) {
        duty.setStatusComment(dto.getStatusComment());
        if (dto.getStatus() != null) {
            duty.setStatus(DutyStatus.valueOf(dto.getStatus().toString()));
        }
        if (dto.getPerson() != null) {
            duty.setPerson(personDao.get(dto.getPerson().getId()));
        }
        return duty;
    }
}
