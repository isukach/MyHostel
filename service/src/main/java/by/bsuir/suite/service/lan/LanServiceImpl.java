package by.bsuir.suite.service.lan;

import by.bsuir.suite.assembler.lan.LanAssembler;
import by.bsuir.suite.dao.lan.LanDao;
import by.bsuir.suite.dao.lan.LanPaymentDao;
import by.bsuir.suite.dao.lan.LanUser;
import by.bsuir.suite.dao.lan.LanUserDao;
import by.bsuir.suite.dao.person.PersonDao;
import by.bsuir.suite.disassembler.lan.LanDtoDisassembler;
import by.bsuir.suite.domain.lan.Lan;
import by.bsuir.suite.domain.lan.LanPayment;
import by.bsuir.suite.domain.person.Person;
import by.bsuir.suite.dto.lan.LanDto;
import by.bsuir.suite.exception.OptimisticLockException;
import org.hibernate.StaleObjectStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : d.shemiarey
 */
@Service
@Transactional
public class LanServiceImpl implements LanService {

    @Autowired
    private LanDao lanDao;

    @Autowired
    private PersonDao personDao;

    @Autowired
    private LanDtoDisassembler lanDtoDisassembler;

    @Autowired
    private LanAssembler lanAssembler;

    @Autowired
    private LanPaymentDao lanPaymentDao;

    @Autowired
    private LanUserDao lanUserDao;

    @Override
    public LanDto getLanPaymentForPerson(Long personId) {
        Person person = personDao.get(personId);
        LanPayment lanPayment = lanPaymentDao.getByFirstNameLastNameAndGroupNumber(
                person.getFirstName(), person.getLastName(), person.getUniversityGroup());
        return lanPayment != null ? lanDtoDisassembler.disassemble(lanPayment) : new LanDto();
    }

    @Override
    public void changeLanPayment(LanDto dto, Long personId) {
        //TODO: implement general locking mechanism.

        Lan updatedLan;
        try {
            Lan lan = lanDao.get(dto.getId());

            if (!versionsEqual(dto, lan)) {
                throw new OptimisticLockException("Lan version is outdated!");
            }
            lanAssembler.updateEntityFields(dto, lan);
            Person person = personDao.get(personId);
            lan.setPerson(person);
            updatedLan = lanDao.update(lan);
            dto.setVersion(updatedLan.getVersion() + 1);
        } catch (StaleObjectStateException e) {
            throw new OptimisticLockException("LanVersion is outdated!", e);
        }
    }

    @Override
    public boolean hasContract(Long personId) {
        Person person = personDao.get(personId);
        LanUser user = lanUserDao.getByFirstNameLastNameAndGroupNumber(
                person.getFirstName(), person.getLastName(), person.getUniversityGroup());
        return user != null && user.isHasContract();
    }

    private boolean versionsEqual(LanDto dto, Lan lan) {
        return dto.getVersion().equals(lan.getVersion());
    }
}
