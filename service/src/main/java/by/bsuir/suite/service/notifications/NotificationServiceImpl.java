package by.bsuir.suite.service.notifications;

import by.bsuir.suite.dao.notificaation.NotificationDao;
import by.bsuir.suite.disassembler.notifications.NotificationDisassembler;
import by.bsuir.suite.domain.Notification;
import by.bsuir.suite.domain.NotificationType;
import by.bsuir.suite.dto.notifications.NotificationDto;
import by.bsuir.suite.service.notifications.common.NewJobIsAvailableTask;
import by.bsuir.suite.util.NotificationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

/**
 * @autor a.garelik
 * Date: 27/07/13
 * Time: 16:18
 */

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    @Qualifier("notificationDisassembler")
    @Autowired
    private NotificationDisassembler disassembler;

    @Qualifier("notificationDaoImpl")
    @Autowired
    private NotificationDao dao;

    @Override
    public List<NotificationDto> loadPartOfNotifications(Long personId, int offset, int limit, Locale locale) {
        List<Notification> notificationEnt = dao.loadNotifications(personId, offset, limit);
        disassembler.setLocale(locale);
        return disassembler.disassembleToList(notificationEnt);
    }

    public void createNotification(Long personId, String notificationKey, Object []textParams, Object []headerParams) {
        dao.createNotification(personId, NotificationUtils.getInstance().getNotificationTypeFromNotificationKey(notificationKey),
                NotificationUtils.getHeaderKey(notificationKey), NotificationUtils.getTextKey(notificationKey),
                NotificationUtils.getInstance().getParametersAsString(headerParams),
                NotificationUtils.getInstance().getParametersAsString(textParams));
    }

    @Override
    public void createJobNotificationTask(int numberOfPeoples, long time) {
        NewJobIsAvailableTask newJobIsAvailableTask = new NewJobIsAvailableTask(numberOfPeoples, time);
//        newJobIsAvailableTask.exec();
    }
}
