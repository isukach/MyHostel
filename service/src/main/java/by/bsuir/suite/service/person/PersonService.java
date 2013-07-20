package by.bsuir.suite.service.person;

import by.bsuir.suite.dto.duty.DutyStatisticsDto;
import by.bsuir.suite.dto.person.CalendarPersonDto;
import by.bsuir.suite.dto.person.PersonDto;
import by.bsuir.suite.dto.person.PersonSearchDto;
import by.bsuir.suite.dto.work.WorkStatisticsDto;

import java.util.List;

/**
 * @author i.sukach
 */
public interface PersonService {

    void evictPerson(String login);

    CalendarPersonDto getCalendarPerson(Long id);

    PersonSearchDto getPersonSearch(Long id);

    PersonDto getPersonByLogin(String login);

    DutyStatisticsDto getDutiesStatistics(String login);
    
    WorkStatisticsDto getWorkStatistics(String login);
    
    Boolean[] getLanStatistics(String login);
    
    void savePerson(PersonDto personDto, String login);

    List<PersonSearchDto> searchForPersons(int from, String search);

    Long count();

    Long personSearchCount(String search);

    PersonDto get(long id);

    void changePassword(long personId, String newPassword);

    boolean isPasswordCorrect(long personId, String password);

    void resetPassword(String login);

    boolean isUsernameCorrect(long personId, String username);

    void changeUsername(long personId, String newUsername);
}
