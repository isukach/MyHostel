package by.bsuir.suite.service.statistics;

import by.bsuir.suite.dao.person.PersonDao;
import by.bsuir.suite.disassembler.statistics.FloorStatisticsDtoDisassembler;
import by.bsuir.suite.domain.person.Person;
import by.bsuir.suite.dto.statistics.FloorStatisticsDto;
import by.bsuir.suite.util.EntityUtils;
import by.bsuir.suite.util.StatisticsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author i.sukach
 */
@Service
@Transactional
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private FloorStatisticsDtoDisassembler floorStatisticsDtoDisassembler;

    @Autowired
    private PersonDao personDao;

    @Override
    public List<FloorStatisticsDto> getFloorStatistics(long floorId, String sortBy, boolean isAscending) {
        List<Person> personList;
        if (StatisticsUtils.FLOOR_SORT_BY_FIRST_NAME.equals(sortBy)
                || StatisticsUtils.FLOOR_SORT_BY_LAST_NAME.equals(sortBy)
                || StatisticsUtils.FLOOR_SORT_BY_GROUP.equals(sortBy)
                || StatisticsUtils.FLOOR_SORT_BY_ROOM.equals(sortBy)) {
            personList = personDao.findByFloorId(floorId, sortBy, isAscending);
        } else {
            personList = personDao.findByFloorId(floorId, null, isAscending);
            if (StatisticsUtils.FLOOR_SORT_BY_COMPLETED_HOURS_PERCENTAGE.equals(sortBy)) {
                Collections.sort(personList,
                        isAscending
                                ? new ByWorkPercentageComparator()
                                : Collections.reverseOrder(new ByWorkPercentageComparator()));
            }
            if (StatisticsUtils.FLOOR_SORT_BY_COMPLETED_DUTIES_PERCENTAGE.equals(sortBy)) {
                Collections.sort(personList,
                        isAscending
                                ? new ByDutyPercentageComparator()
                                : Collections.reverseOrder(new ByDutyPercentageComparator()));
            }
        }
        return floorStatisticsDtoDisassembler.disassembleToList(personList);
    }

    @Override
    public FloorStatisticsDto getByPersonId(long personId) {
        Person person = personDao.get(personId);
        if (person != null) {
            return floorStatisticsDtoDisassembler.disassemble(person);
        }
        throw new IllegalArgumentException("Can't find person by provided id: " + personId);
    }

    @Override
    public int getPersonCountForFloor(long floorId) {
        return personDao.getCountForFloor(floorId);
    }

    private static class ByWorkPercentageComparator implements Comparator<Person> {

        @Override
        public int compare(Person person1, Person person2) {
            float workCompletionPercentage1 = countWorkCompletionPercentage(person1);
            float workCompletionPercentage2 = countWorkCompletionPercentage(person2);
            if (workCompletionPercentage1 < workCompletionPercentage2) {
                return -1;
            } else if (workCompletionPercentage1 > workCompletionPercentage2) {
                return 1;
            } else {
                return 0;
            }
        }

        private float countWorkCompletionPercentage(Person person) {
            float completedHours = EntityUtils.countPersonCompletedHours(person);
            return completedHours / ((float) person.getWork().getTotalRequiredHours());
        }
    }

    private static class ByDutyPercentageComparator implements Comparator<Person> {

        @Override
        public int compare(Person person1, Person person2) {
            float dutyCompletionPercentage1 = countDutyCompletionPercentage(person1);
            float dutyCompletionPercentage2 = countDutyCompletionPercentage(person2);
            if ( dutyCompletionPercentage1 < dutyCompletionPercentage2) {
                return -1;
            } else if (dutyCompletionPercentage1 > dutyCompletionPercentage2) {
                return 1;
            } else {
                return 0;
            }
        }

        private float countDutyCompletionPercentage(Person person) {
            float completedDuties = person.getDuties().size();
            return completedDuties / (float) (person.getRequiredDuties() + person.getExtraDuties());
        }
    }
}
