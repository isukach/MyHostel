import by.bsuir.suite.dao.duty.MonthDao;
import by.bsuir.suite.dao.person.FloorDao;
import by.bsuir.suite.disassembler.duty.CalendarMonthDtoDisassembler;
import by.bsuir.suite.domain.duty.Duty;
import by.bsuir.suite.domain.duty.DutyShift;
import by.bsuir.suite.domain.duty.Month;
import by.bsuir.suite.domain.person.Floor;
import by.bsuir.suite.util.DateUtils;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * @author i.sukach
 */
@ContextConfiguration(locations = "classpath:applicationContext-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CalendarMonthTest {

    @Autowired
    private CalendarMonthDtoDisassembler calendarMonthDtoDisassembler;

    @Autowired
    private MonthDao monthDao;

    @Autowired
    private FloorDao floorDao;

    private static Long monthId;

    private static Long floorId;

    private static Boolean ENABLED = true;

    private static int DAYS_IN_CURRENT_MONTH = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);

    @Test
    public void testMonthDaoNotNull() {
        Assert.assertNotNull(monthDao);
    }

    @Test
    @Rollback(false)
    public void createFloor() {
        Floor floor = new Floor();
        floor.setNumber("6");
        floor.setRoomNumber(23);
        floorDao.create(floor);
        floorId = floor.getId();
    }

    @Test
    public void testGetFloor() {
        Assert.assertNotNull(floorDao.get(floorId));
    }

    @Test
    @Rollback(false)
    public void saveTestData() {
        Month month = new Month();
        month.setMonth(DateUtils.getCurrentMonthNumber());
        month.setYear(DateUtils.getCurrentYearNumber());
        month.setEnabled(ENABLED);
        month.setDuties(generateDuties());
        month.setFloor(floorDao.get(floorId));
        monthDao.create(month);
        monthId = month.getId();
    }

    @Test
    public void monthIdNotNull() {
        Assert.assertNotNull(monthId);
    }

    @Test
    public void testGetMonth() {
        Month month = monthDao.get(monthId);
        Assert.assertNotNull(month);
        Assert.assertEquals(DateUtils.getCurrentMonthNumber(), month.getMonth());
        Assert.assertEquals(DateUtils.getCurrentYearNumber(), month.getYear());
        Assert.assertEquals(ENABLED, Boolean.valueOf(month.isEnabled()));
        Assert.assertEquals(2 * DAYS_IN_CURRENT_MONTH, month.getDuties().size());
    }

    @Test
    public void testGetMonthByEnabledAndFloorId() {
        Month month = monthDao.findByEnabledAndFloorId(floorId).get(0);
        Assert.assertNotNull(month);
    }

    @Test
    public void testGetMonthByMonthYearAndFloorId() {
        Month month = monthDao.findMonthByMonthYearAndFloorId(
                DateUtils.getCurrentMonthNumber(), DateUtils.getCurrentYearNumber(), floorId);
        Assert.assertEquals(month.getMonth(), DateUtils.getCurrentMonthNumber());
        Assert.assertEquals(month.getYear(), DateUtils.getCurrentYearNumber());
        Assert.assertEquals(month.getFloor().getId(), floorId);
    }

    private Set<Duty> generateDuties() {
        Set<Duty> duties = new HashSet<Duty>();
        Calendar calendar = Calendar.getInstance();
        for (int i = 1; i <= DAYS_IN_CURRENT_MONTH; i++) {
            for (int j = 1; j <= 2; j++) {
                calendar.set(Calendar.DAY_OF_MONTH, i);
                Duty duty = new Duty();
                duty.setDate(calendar.getTime());
                duty.setShift(DutyShift.fromNumber(j));
                duties.add(duty);
            }
        }
        return duties;
    }
}
