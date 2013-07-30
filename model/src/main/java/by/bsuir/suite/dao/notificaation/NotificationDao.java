package by.bsuir.suite.dao.notificaation;

import by.bsuir.suite.dao.common.GenericDao;
import by.bsuir.suite.domain.Notification;
import by.bsuir.suite.domain.NotificationType;

import java.util.List;

/**
 * @autor a.garelik
 * Date: 27/07/13
 * Time: 17:03
 */

public interface NotificationDao extends GenericDao<Notification> {

    public List<Notification> loadNotifications(Long personId, int offset, int limit) ;

    public void markAsRead(Long personId);

    public int notificationsCount(Long personId);

    public void createNotification(Long personId, NotificationType type, String header, String text, String headerParams, String textParams);

}
