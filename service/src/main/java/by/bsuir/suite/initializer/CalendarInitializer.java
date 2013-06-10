package by.bsuir.suite.initializer;

import by.bsuir.suite.dao.duty.MonthDao;
import by.bsuir.suite.dao.person.FloorDao;
import by.bsuir.suite.dao.person.HostelDao;
import by.bsuir.suite.domain.duty.Month;
import by.bsuir.suite.domain.person.Floor;
import by.bsuir.suite.domain.person.Hostel;
import by.bsuir.suite.job.common.HostelTask;
import by.bsuir.suite.util.MonthUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author i.sukach
 */
public class CalendarInitializer implements HostelTask {

    private static final int MONTHS_TO_ADD = 2;

    @Autowired
    private HostelDao hostelDao;

    @Autowired
    private FloorDao floorDao;

    @Autowired
    private MonthDao monthDao;

    @Override
    public void execute() {
        List<Hostel> hostels = hostelDao.findAll();
        for (Hostel hostel : hostels) {
            List<Floor> floors = floorDao.findByHostelId(hostel.getId());
            for (Floor floor : floors) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                for (int i = 0; i < MONTHS_TO_ADD; i++) {
                    calendar.set(Calendar.DAY_OF_MONTH, 1);
                    int monthNumber = calendar.get(Calendar.MONTH);
                    int yearNumber = calendar.get(Calendar.YEAR);
                    if (monthDao.findMonthByMonthYearAndFloorId(monthNumber, yearNumber, floor.getId()) == null) {
                        Month month = new Month();
                        month.setFloor(floor);
                        month.setMonth(monthNumber);
                        month.setYear(yearNumber);
                        month.setEnabled(i == 0);
                        MonthUtils.fillMonthWithDuties(month, calendar.getTime());
                        monthDao.update(month);
                    }
                    calendar.add(Calendar.MONTH, 1);
                }
            }
        }
    }
}
