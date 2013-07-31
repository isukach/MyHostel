package by.bsuir.suite.service.notifications;

import by.bsuir.suite.dto.notifications.NotificationDto;
import by.bsuir.suite.service.notifications.common.NewJobIsAvailableTask;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Locale;

/**
 * @autor a.garelik
 * Date: 27/07/13
 * Time: 16:12
 */

public interface NotificationService {

    public List<NotificationDto> loadPartOfNotifications(Long personId, int offset, int limit, Locale locale);

    public void createNotification(Long personId, String notificationKey, Object []textParams, Object []headerParams);

    void createJobNotificationTask(int numberOfPeoples, long time);

}
