package by.bsuir.suite.job.common;

import by.bsuir.suite.dao.duty.DutyDao;
import by.bsuir.suite.dao.duty.DutyDaoImpl;
import by.bsuir.suite.dao.duty.MonthDao;
import by.bsuir.suite.dao.duty.MonthDaoImpl;
import by.bsuir.suite.domain.duty.Duty;
import by.bsuir.suite.domain.duty.Month;
import by.bsuir.suite.service.notifications.NotificationService;
import by.bsuir.suite.service.notifications.NotificationServiceImpl;
import by.bsuir.suite.service.notifications.common.NotificationKeys;
import by.bsuir.suite.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @autor a.garelik
 * Date: 31/07/13
 * Time: 00:16
 */

public class DutyNotificationJob implements HostelTask{

    @Autowired
    private DutyDao dutyDao;
    @Autowired
    private NotificationService notificationService;

    private int NOTIFICATION_DAYS = 3;

    private long MSEC_IN_DAY = 86400000;

    @Override
    public void execute() {

        for (int i = 1; i < NOTIFICATION_DAYS + 1; i++)
        {
            Date theTargetDate = new Date(System.currentTimeMillis()+MSEC_IN_DAY*i);
            List<Duty> duties = dutyDao.getDutyListByDate(theTargetDate);
            for (Duty duty : duties)
            {
                notificationService.createNotification(duty.getPerson().getId(),
                        NotificationKeys.DUTY_IS_SOON,
                        new String[]{String.valueOf(i),
                                DateUtils.getFormattedDate(duty.getDate().getTime())},null);
            }
        }

    }
}
