package by.bsuir.suite.service.duty;

import by.bsuir.suite.dto.duty.CalendarDutyDto;
import by.bsuir.suite.dto.duty.PersonDutyDto;

import java.util.List;

/**
 * @author i.sukach
 */
public interface DutyService {

    CalendarDutyDto occupyDuty(CalendarDutyDto dto, Long personId);

    List<PersonDutyDto> getDutiesForPerson(Long personId);

    PersonDutyDto getPersonDuty(Long dutyId);

    Long getUnskippedCountForPerson(Long personId);

    CalendarDutyDto updateDuty(CalendarDutyDto dto);

    CalendarDutyDto freeDuty(CalendarDutyDto dto);
}
