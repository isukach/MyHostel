package by.bsuir.suite.dao.lan;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * @author i.sukach
 */
@Repository
public class LanUserDaoImpl extends HibernateDaoSupport implements LanUserDao {

    private Class<LanUser> persistentClass = LanUser.class;

    @Autowired
    @Qualifier("lanSessionFactory")
    public void init(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }

    @Override
    public LanUser getByFirstNameLastNameAndGroupNumber(String firstName, String lastName, String groupNumber) {
        Criteria criteria = getSession().createCriteria(persistentClass, "user");
        criteria.add(Restrictions.eq("user.firstName", firstName));
        criteria.add(Restrictions.eq("user.lastName", lastName));
        criteria.add(Restrictions.eq("user.groupNumber", groupNumber));

        return (LanUser) criteria.uniqueResult();
    }
}
