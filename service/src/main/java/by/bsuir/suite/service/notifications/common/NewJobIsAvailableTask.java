package by.bsuir.suite.service.notifications.common;

import by.bsuir.suite.dao.person.PersonDao;
import by.bsuir.suite.service.notifications.NotificationService;
import by.bsuir.suite.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * @autor Harelik A on 7/31/13.
 */
@Component
@Scope("prototype")
public class NewJobIsAvailableTask extends Thread
{

    @Qualifier("notificationServiceImpl")
    @Autowired
    private NotificationService notificationService;

    @Qualifier("personDaoImpl")
    @Autowired
    private PersonDao personDao;

    private int personsCount;
    private long time;

    private static final int DELAY = 20;

    public NewJobIsAvailableTask(int personsCount, long time) {
        this.personsCount = personsCount;
        this.time = time;
    }

    public NewJobIsAvailableTask() {
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getPersonsCount() {
        return personsCount;
    }

    public void setPersonsCount(int personsCount) {
        this.personsCount = personsCount;
    }

    @Override
    public void run() {
        super.run();
        exec();
    }

    private void exec(){

        //Warning!!! possible "heap space" exception
        List personIds = personDao.getActivePersonIds();
        for (Object id : personIds){
            Long theId = ((BigDecimal)id).longValue();
            notificationService.createNotification(theId,
                    NotificationKeys.NEW_JOB_IS_AVAILABLE,
                    new String[]{DateUtils.getFormattedDate(time), String.valueOf(personsCount)}, null);
            try {
                sleep(DELAY);
            } catch (InterruptedException e) {
                //continue
            }
        }
    }
}
