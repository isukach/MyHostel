package by.bsuir.suite.dao.duty;

import by.bsuir.suite.dao.common.GenericDao;
import by.bsuir.suite.domain.duty.Month;

import java.util.List;

/**
 * @author i.sukach
 */
public interface MonthDao extends GenericDao<Month> {

    List<Month> findByEnabledAndFloorId(Long floorId);

    Month findMonthByMonthYearAndFloorId(int month, int year, Long floorId);

    List<Month> findByFloorId(Long floorId);
}
