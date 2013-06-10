package by.bsuir.suite.dao.lan;

import by.bsuir.suite.domain.lan.LanPayment;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.Calendar;

/**
 * @author i.sukach
 */
@Repository
public class LanPaymentDaoImpl extends HibernateDaoSupport implements LanPaymentDao {

    private Class<LanPayment> persistentClass = LanPayment.class;

    @Autowired
    @Qualifier("lanSessionFactory")
    public void init(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }

    @Override
    public LanPayment getByFirstNameLastNameAndGroupNumber(String firstName, String lastName, String groupNumber) {
        Criteria criteria = getSession().createCriteria(persistentClass, "payment");
        criteria.createAlias("payment.user", "paymentUser");
        criteria.add(Restrictions.eq("paymentUser.firstName", firstName));
        criteria.add(Restrictions.eq("paymentUser.lastName", lastName));
        criteria.add(Restrictions.eq("paymentUser.groupNumber", groupNumber));

        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        if (month >= 9 && month <= 12) {
            criteria.add(Restrictions.eq("year", calendar.get(Calendar.YEAR)));
        } else {
            criteria.add(Restrictions.eq("year", calendar.get(Calendar.YEAR) - 1));
        }

        return (LanPayment) criteria.uniqueResult();
    }
}
