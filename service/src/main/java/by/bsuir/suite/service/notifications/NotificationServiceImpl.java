package by.bsuir.suite.service.notifications;

import by.bsuir.suite.dao.notificaation.NotificationDao;
import by.bsuir.suite.disassembler.notifications.NotificationDisassembler;
import by.bsuir.suite.domain.Notification;
import by.bsuir.suite.domain.NotificationType;
import by.bsuir.suite.dto.notifications.NotificationDto;
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

    @Override
    public void createDutyNotification(Long personId, Object []textParams, Object []headerParams) {
        dao.createNotification(personId, NotificationType.DUTY,
                "notifications.duty.text", "notifications.duty.text",
                NotificationUtils.getInstance().getParametersAsString(headerParams),
                NotificationUtils.getInstance().getParametersAsString(textParams));
    }
}
