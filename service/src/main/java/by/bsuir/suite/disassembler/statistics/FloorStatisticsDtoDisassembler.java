package by.bsuir.suite.disassembler.statistics;

import by.bsuir.suite.disassembler.common.BaseDisassembler;
import by.bsuir.suite.domain.person.Person;
import by.bsuir.suite.dto.statistics.FloorStatisticsDto;
import by.bsuir.suite.util.EntityUtils;
import org.springframework.stereotype.Component;

/**
 * @author i.sukach
 */
@Component
public class FloorStatisticsDtoDisassembler extends BaseDisassembler<FloorStatisticsDto, Person> {

    @Override
    public FloorStatisticsDto disassemble(Person person) {
        FloorStatisticsDto dto = new FloorStatisticsDto();
        dto.setPersonId(person.getId());
        dto.setFirstName(person.getFirstName());
        dto.setLastName(person.getLastName());
        dto.setGroup(person.getUniversityGroup());
        dto.setRoom(person.getRoom().getFloor().getNumber() + person.getRoom().getNumber());

        String workCompletion = EntityUtils.countPersonCompletedHours(person) + "/" + (person.getWork().getTotalRequiredHours());
        String dutyCompletion = person.getDuties().size() + "/" + person.getTotalRequiredDuties();
        dto.setDutyCompletion(dutyCompletion);
        dto.setWorkCompletion(workCompletion);

        return dto;
    }
}
