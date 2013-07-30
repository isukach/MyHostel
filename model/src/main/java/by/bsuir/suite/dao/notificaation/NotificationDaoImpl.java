package by.bsuir.suite.dao.notificaation;

import by.bsuir.suite.dao.common.GenericDaoImpl;
import by.bsuir.suite.dao.person.PersonDao;
import by.bsuir.suite.domain.Notification;
import by.bsuir.suite.domain.NotificationType;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

/**
 * @autor a.garelik
 * Date: 27/07/13
 * Time: 17:08
 */
@Repository
public class NotificationDaoImpl extends GenericDaoImpl<Notification> implements NotificationDao{

    @Autowired
    private PersonDao personDao;

    public NotificationDaoImpl() {
        super(Notification.class);
    }

    @Override
    public List<Notification> loadNotifications(Long personId, int offset, int limit) {
        Criteria criteria = getSession().createCriteria(getPersistentClass(),"notification");
        if(limit > 0 && offset > 0 ){
            criteria.setMaxResults(limit);
            criteria.setFirstResult(offset);
        }
        criteria.addOrder(Order.asc("date"));
        criteria.createAlias("notification.person", "NP");
        criteria.add(Restrictions.eq("NP.id", personId));
        return criteria.list();
    }

    @Override
    public void markAsRead(Long personId) {
        getSession().createSQLQuery("UPDATE hostel.notification " +
                "SET hostel.notification.viewed=true WHERE hostel.notification.viewed=false " +
                "AND hostel.notification.person_id = "+personId).executeUpdate();
    }

    public int notificationsCount(Long personId) {
        return ((BigInteger)getSession().createSQLQuery("SELECT count(*) FROM hostel.notification " +
                "WHERE hostel.notification.person_id = "+personId).uniqueResult()).intValue();

    }

    @Override
    public void createNotification(Long personId, NotificationType type, String text, String header, String headerParams, String textParams) {
        Notification theNotification = new Notification();
        theNotification.setHeader(header);
        theNotification.setText(text);
        theNotification.setDate(new Timestamp(System.currentTimeMillis()));
        theNotification.setViewed(false);
        theNotification.setPerson(personDao.get(personId));
        theNotification.setEntityType(type);
        theNotification.setTextParams(textParams);
        theNotification.setHeaderParams(headerParams);
        getSession().save(theNotification);
    }


}
