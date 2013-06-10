package by.bsuir.suite.service.penalty;

import by.bsuir.suite.dao.duty.DutyDao;
import by.bsuir.suite.dao.person.PersonDao;
import by.bsuir.suite.disassembler.penalty.PenaltyDtoDisassembler;
import by.bsuir.suite.domain.duty.Duty;
import by.bsuir.suite.domain.duty.DutyStatus;
import by.bsuir.suite.domain.person.Person;
import by.bsuir.suite.dto.penalty.ClosePenaltyDto;
import by.bsuir.suite.dto.penalty.PenaltyDto;
import by.bsuir.suite.util.PenaltyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: CHEB
 */
@Service
@Transactional
public class PenaltyServiceImpl implements PenaltyService {

    @Autowired
    private PenaltyDtoDisassembler disassembler;

    @Autowired
    private DutyDao dutyDao;

    @Autowired
    private PersonDao personDao;


    @Override
    public Long getPenaltyCount() {
        return dutyDao.getPenaltyCount();
    }

    @Override
    public List<PenaltyDto> findPenalty(int first, int count, String sortBy) {
        if (PenaltyUtils.DUTY_SORT_BY_DATE.equals(sortBy)) {
            return disassembler.disassembleToList(dutyDao.getPenaltyByDateFrom(first,count));
        } else if (PenaltyUtils.DUTY_SORT_BY_PERSON_NAME.equals(sortBy)) {
            return disassembler.disassembleToList(dutyDao.getPenaltyByPersonNameFrom(first, count));
        } else if (PenaltyUtils.DUTY_SORT_BY_FLOOR.equals(sortBy)) {
            return disassembler.disassembleToList(dutyDao.getPenaltyByFloorFrom(first, count));
        }
        return null;
    }

    @Override
    public PenaltyDto getPenaltyById(Long id) {
        return disassembler.disassemble(dutyDao.get(id));
    }

    @Override
    public void closePenalty(ClosePenaltyDto closePenaltyDto) {
        if(closePenaltyDto.getDutyId() != null) {
            Duty duty = dutyDao.get(closePenaltyDto.getDutyId());
            if (duty.getStatus() != DutyStatus.SKIPPED) {
                duty.setStatus(DutyStatus.COMPLETED_PUNISHED);
            }
            duty.setClosed(true);
            dutyDao.update(duty);
        }
    }

    @Override
    public void makePunishment(ClosePenaltyDto closePenaltyDto) {
        if(closePenaltyDto.isDutyAdding() || closePenaltyDto.isWorkAdding()) {
            Person person = personDao.get(closePenaltyDto.getPersonId());
            if(closePenaltyDto.isDutyAdding()) {
                person.setExtraDuties(person.getExtraDuties() + closePenaltyDto.getAdditionalDuties());
            }
            if(closePenaltyDto.isWorkAdding()) {
                person.getWork().setPenaltyHours(person.getWork().getPenaltyHours()
                        + closePenaltyDto.getAdditionalWorkHours());
            }
            personDao.update(person);
        }
    }
}
