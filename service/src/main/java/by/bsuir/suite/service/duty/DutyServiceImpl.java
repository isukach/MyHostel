package by.bsuir.suite.service.duty;

import by.bsuir.suite.assembler.duty.DutyAssembler;
import by.bsuir.suite.dao.duty.DutyDao;
import by.bsuir.suite.dao.person.PersonDao;
import by.bsuir.suite.disassembler.duty.CalendarDutyDtoDisassembler;
import by.bsuir.suite.disassembler.duty.PersonDutyDtoDisassembler;
import by.bsuir.suite.domain.duty.Duty;
import by.bsuir.suite.domain.duty.DutyStatus;
import by.bsuir.suite.domain.person.Person;
import by.bsuir.suite.dto.duty.CalendarDutyDto;
import by.bsuir.suite.dto.duty.PersonDutyDto;
import by.bsuir.suite.exception.OptimisticLockException;
import org.hibernate.StaleObjectStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author i.sukach
 */
@Service
@Transactional
public class DutyServiceImpl implements DutyService {

    @Autowired
    private DutyDao dutyDao;

    @Autowired
    private PersonDao personDao;

    @Autowired
    private DutyAssembler dutyAssembler;

    @Autowired
    private CalendarDutyDtoDisassembler calendarDutyDtoDisassembler;

    @Autowired
    private PersonDutyDtoDisassembler personDutyDtoDisassembler;

    @Override
    public CalendarDutyDto occupyDuty(CalendarDutyDto dto, Long personId) {
        //TODO: implement general locking mechanism.

        Duty updatedDuty;
        try {
            Duty duty = dutyDao.get(dto.getId());

            if (!versionsEqual(dto, duty)) {
                throw new OptimisticLockException("Duty version is outdated!");
            }

            duty.setStatus(DutyStatus.OCCUPIED);
            Person person = personDao.get(personId);
            duty.setPerson(person);
            updatedDuty = dutyDao.update(duty);
        } catch (StaleObjectStateException e) {
            throw new OptimisticLockException("DutyVersion is outdated!", e);
        }
        return calendarDutyDtoDisassembler.disassemble(updatedDuty);
    }


    private boolean versionsEqual(CalendarDutyDto dto, Duty duty) {
        return dto.getVersion().equals(duty.getVersion());
    }

    @Override
    public List<PersonDutyDto> getDutiesForPerson(Long personId) {
        return personDutyDtoDisassembler.disassembleToList(dutyDao.getDutiesForPerson(personId));
    }

    @Override
    public PersonDutyDto getPersonDuty(Long dutyId) {
        return personDutyDtoDisassembler.disassemble(dutyDao.get(dutyId));
    }

    @Override
    public Long getUnskippedCountForPerson(Long personId) {
        return dutyDao.getUnskippedCountForPerson(personId);
    }

    @Override
    public CalendarDutyDto updateDuty(CalendarDutyDto dto) {
        Duty oldDuty = dutyDao.get(dto.getId());
        Duty updatedDuty = dutyDao.update(dutyAssembler.updateEntityFields(dto, oldDuty));
        return calendarDutyDtoDisassembler.disassemble(updatedDuty);
    }

    @Override
    public CalendarDutyDto freeDuty(CalendarDutyDto dto) {
        Duty oldDuty = dutyDao.get(dto.getId());
        oldDuty.setStatus(DutyStatus.FREE);
        oldDuty.setStatusComment(null);
        oldDuty.setPerson(null);
        Duty updatedDuty = dutyDao.update(oldDuty);
        CalendarDutyDto newDto = calendarDutyDtoDisassembler.disassemble(updatedDuty);
        newDto.setVersion(newDto.getVersion() + 1);
        return newDto;
    }
}
