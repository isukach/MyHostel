package by.bsuir.suite.dao.duty;

import by.bsuir.suite.dao.common.GenericDao;
import by.bsuir.suite.domain.duty.Duty;

import java.util.Date;
import java.util.List;

/**
 * @author i.sukach
 */
public interface DutyDao extends GenericDao<Duty> {

    List<Duty> getDutiesForPerson(Long personId);

    Long getUnskippedCountForPerson(Long personId);

    List<Duty> getPenaltyByDateFrom(int first, int count);

    List<Duty> getPenaltyByPersonNameFrom(int first, int count);

    List<Duty> getPenaltyByFloorFrom(int first, int count);

    Long getPenaltyCount();

    List<Duty> getUnevaluatedDutiesBeforeDate(Date date);

    List<Duty> getDutyListByDate(Date date);


}
