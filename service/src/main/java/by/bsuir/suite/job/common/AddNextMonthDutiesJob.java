package by.bsuir.suite.job.common;

import by.bsuir.suite.dao.duty.MonthDao;
import by.bsuir.suite.dao.person.FloorDao;
import by.bsuir.suite.dao.person.HostelDao;
import by.bsuir.suite.domain.duty.Month;
import by.bsuir.suite.domain.person.Floor;
import by.bsuir.suite.domain.person.Hostel;
import by.bsuir.suite.util.MonthUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author i.sukach
 */
public class AddNextMonthDutiesJob {

    private static final Logger LOG = Logger.getLogger(AddNextMonthDutiesJob.class);

    @Autowired
    private HostelDao hostelDao;

    @Autowired
    private FloorDao floorDao;

    @Autowired
    private MonthDao monthDao;

    public void addNextMonthCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        List<Hostel> hostels = hostelDao.findAll();
        for (Hostel hostel : hostels) {
            List<Floor> floors = floorDao.findByHostelId(hostel.getId());
            for (Floor floor : floors) {
                int yearNumber = calendar.get(Calendar.YEAR);
                int monthNumber = calendar.get(Calendar.MONTH);
                // check NEXT month existence
                if (monthDao.findMonthByMonthYearAndFloorId(monthNumber, yearNumber, floor.getId()) == null) {
                    LOG.debug(String.format("Adding month to calendar. Month: %s. Year: %s. Floor: %s",
                            monthNumber, yearNumber, floor.getNumber()));
                    Month month = new Month();
                    month.setFloor(floor);
                    month.setEnabled(false);
                    month.setMonth(monthNumber);
                    month.setYear(yearNumber);
                    MonthUtils.fillMonthWithDuties(month, calendar.getTime());
                    monthDao.update(month);
                }
            }
        }

    }
}
