package by.bsuir.suite.service.person;

import by.bsuir.suite.assembler.person.PersonDtoAssembler;
import by.bsuir.suite.dao.lan.LanPaymentDao;
import by.bsuir.suite.dao.person.PersonDao;
import by.bsuir.suite.disassembler.person.CalendarPersonDtoDisassembler;
import by.bsuir.suite.disassembler.person.PersonDtoDisassembler;
import by.bsuir.suite.disassembler.person.PersonSearchDtoDisassembler;
import by.bsuir.suite.domain.duty.Duty;
import by.bsuir.suite.domain.duty.DutyStatus;
import by.bsuir.suite.domain.lan.LanPayment;
import by.bsuir.suite.domain.person.Person;
import by.bsuir.suite.domain.work.Job;
import by.bsuir.suite.dto.duty.DutyStatisticsDto;
import by.bsuir.suite.dto.person.CalendarPersonDto;
import by.bsuir.suite.dto.person.PersonDto;
import by.bsuir.suite.dto.person.PersonSearchDto;
import by.bsuir.suite.dto.work.WorkStatisticsDto;
import by.bsuir.suite.password.PasswordEncryptor;
import by.bsuir.suite.util.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author i.sukach
 */
@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonDao personDao;

    @Autowired
    private CalendarPersonDtoDisassembler calendarPersonDtoDisassembler;

    @Autowired
    private PersonDtoDisassembler personDtoDisassembler;

    @Autowired
    private PersonDtoAssembler personDtoAssembler;

    @Autowired
    private PersonSearchDtoDisassembler personSearchDtoDisassembler;

    @Autowired
    private LanPaymentDao lanPaymentDao;

    @Override
    public CalendarPersonDto getCalendarPerson(Long id) {
        return calendarPersonDtoDisassembler.disassemble(personDao.get(id));
    }

    @Override
    public PersonSearchDto getPersonSearch(Long id) {
        return personSearchDtoDisassembler.disassemble(personDao.get(id));
    }

    @Override
    public PersonDto getPersonByLogin(String login) {
        Person person = personDao.getPersonByUsername(login);
        return personDtoDisassembler.disassemble(person);
    }

    @Override
    public DutyStatisticsDto getDutiesStatistics(String login) {
        DutyStatisticsDto dutyStatisticsDto = new DutyStatisticsDto();
        Person person = personDao.getPersonByUsername(login);
        int completedDutiesCount = 0;
        for (Duty duty : person.getDuties()) {
            if (duty.getStatus() != DutyStatus.SKIPPED) {
                completedDutiesCount++;
            }
        }
        if(person.getRequiredDuties() + person.getExtraDuties() == 0) {
            dutyStatisticsDto.setCompletedDutiesPercentage(0);
        }
        else {
            Double count = ((double) completedDutiesCount
                    / ((double) person.getRequiredDuties() + person.getExtraDuties()) * 100);
            dutyStatisticsDto.setCompletedDutiesPercentage(count);
        }
        dutyStatisticsDto.setCompletedDuties(completedDutiesCount);
        dutyStatisticsDto.setDuties(person.getRequiredDuties());
        dutyStatisticsDto.setExtraDuties(person.getExtraDuties());
        return dutyStatisticsDto;
    }

    @Override
    public WorkStatisticsDto getWorkStatistics(String login) {
        WorkStatisticsDto dto = new WorkStatisticsDto();
        Person person = personDao.getPersonByUsername(login);
        int totalHours = 0;
        for (Job job : person.getWork().getJobs()) {
            totalHours += job.getHours();
        }
        if(person.getWork().getTotalRequiredHours() == 0) {
            dto.setWorkCompletedPercentage(0);
        } else {

            double completePercentage = ((double) totalHours / (double) person.getWork().getTotalRequiredHours() * 100);
            dto.setWorkCompletedPercentage(completePercentage);
        }
        dto.setCompletedHours(totalHours);
        dto.setRequiredHours(person.getWork().getRequiredHours());
        dto.setExtraHours(person.getWork().getPenaltyHours());
        return dto;
    }

    @Override
    public Boolean[] getLanStatistics(String login) {
        Person person = personDao.getPersonByUsername(login);
        LanPayment lanPayment = lanPaymentDao.getByFirstNameLastNameAndGroupNumber(
                person.getFirstName(), person.getLastName(), person.getUniversityGroup());
        Boolean[] mass = new Boolean[12];
        Arrays.fill(mass, Boolean.FALSE);
        if (lanPayment != null) {
            mass[0] = lanPayment.isJanuary();
            mass[1] = lanPayment.isFebruary();
            mass[2] = lanPayment.isMarch();
            mass[3] = lanPayment.isApril();
            mass[4] = lanPayment.isMay();
            mass[5] = lanPayment.isJune();
            mass[6] = lanPayment.isJuly();
            mass[7] = lanPayment.isAugust();
            mass[8] = lanPayment.isSeptember();
            mass[9] = lanPayment.isOctober();
            mass[10] = lanPayment.isNovember();
            mass[11] = lanPayment.isDecember();
        }

        return mass;
    }

    @Override
    public void savePerson(PersonDto personDto, String login) {
        Person person = personDao.getPersonByUsername(login);
        personDtoAssembler.updateEntityFields(personDto, person);
        personDao.update(person);
    }

    @Override
    public List<PersonSearchDto> searchForPersons(int from, String search) {
        if (search == null || "".equals(search)) {
            return new ArrayList<PersonSearchDto>();
        }
        return personSearchDtoDisassembler.disassembleToList(personDao.searchForPerson(from, search));
    }

    @Override
    public Long count() {
        return personDao.count();
    }

    @Override
    public Long personSearchCount(String search) {
        return personDao.personSearchCount(search);
    }


    @Override
    public PersonDto get(long id) {
        Person person = personDao.get(id);
        if (person == null) {
            throw new IllegalArgumentException("Could not find person by provided id");
        }
        return personDtoDisassembler.disassemble(person);
    }

    @Override
    public void changePassword(long personId, String newPassword) {
        Person person = personDao.get(personId);
        if (person == null) {
            throw new IllegalArgumentException("Could not find person by provided id");
        }
        person.getUser().setPassword(PasswordEncryptor.encrypt(newPassword));
        personDao.update(person);
    }

    @Override
    public boolean isPasswordCorrect(long personId, String password) {
        Person person = personDao.get(personId);
        if (person == null) {
            throw new IllegalArgumentException("Could not find person by provided id");
        }
        return person.getUser().getPassword().equals(PasswordEncryptor.encrypt(password));
    }

    @Override
    public void resetPassword(String login) {
        Person person = personDao.getPersonByUsername(login);
        if (person == null) {
            throw new IllegalArgumentException("Could not find person by provided username");
        }
        person.getUser().setPassword(PasswordEncryptor.encrypt(PasswordUtils.generateDefaultPassword(person)));
    }

    @Override
    public boolean isUsernameCorrect(long personId, String username) {
        Person person = personDao.get(personId);
        if (person == null) {
            throw new IllegalArgumentException("Could not find person by provided username");
        }
        return person.getUser().getUsername().equals(username);
    }

    @Override
    public void changeUsername(long personId, String newUsername) {
        Person person = personDao.get(personId);
        if (person == null) {
            throw new IllegalArgumentException("Could not find person by provided id");
        }
        person.getUser().setUsername(newUsername);
        personDao.update(person);
    }
}
