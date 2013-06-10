package by.bsuir.suite.service.duty;

import by.bsuir.suite.dto.duty.CalendarMonthDto;

/**
 * @author i.sukach
 */
public interface MonthService {

    CalendarMonthDto findFirstEnabledMonthByFloorId(Long floorId);

    CalendarMonthDto switchMonthAccess(CalendarMonthDto month);

    CalendarMonthDto findByMonthYearAndFloorId(int month, int year, Long floorId);
}
