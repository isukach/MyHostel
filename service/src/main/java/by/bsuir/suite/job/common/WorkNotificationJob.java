package by.bsuir.suite.job.common;

import by.bsuir.suite.dao.work.JobOfferDao;
import by.bsuir.suite.domain.person.Person;
import by.bsuir.suite.domain.work.JobOffer;
import by.bsuir.suite.service.notifications.NotificationService;
import by.bsuir.suite.service.notifications.common.NotificationKeys;
import by.bsuir.suite.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Date;
import java.util.List;

/**
 * @autor a.garelik
 * Date: 31/07/13
 * Time: 00:16
 */

public class WorkNotificationJob implements HostelTask{


    @Autowired
    private NotificationService notificationService;

    @Qualifier("jobOfferDaoImpl")
    @Autowired
    private JobOfferDao jobOfferDao;

    private int NOTIFICATION_DAYS = 3;

    private long MSEC_IN_DAY = 86400000;

    @Override
    public void execute() {

        for (int i = 1; i < NOTIFICATION_DAYS + 1; i++) {
            Date theTargetDate = new Date(System.currentTimeMillis()+MSEC_IN_DAY*i);
            List<Person> personsList = jobOfferDao.getListJobPersonsByDate(theTargetDate);

            for (Person person : personsList)
                notificationService.createNotification(person.getId(),
                        NotificationKeys.JOB_IS_SOON,
                        new String[]{String.valueOf(i),
                                DateUtils.getFormattedDate(theTargetDate.getTime())},null);

        }

    }
}
