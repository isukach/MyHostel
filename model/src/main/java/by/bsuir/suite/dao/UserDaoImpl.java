package by.bsuir.suite.dao;

import by.bsuir.suite.dao.common.GenericDaoImpl;
import by.bsuir.suite.domain.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * @author i.sukach
 */
@Repository
public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao {

    public UserDaoImpl() {
        super(User.class);
    }

    @Override
    public User getUserByUsername(String username){
        Criteria criteria = getSession().createCriteria(getPersistentClass(), "user");
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        if (username != null) {
            criteria.add(Restrictions.eq("user.username", username));
        }
        return (User) criteria.uniqueResult();
    }

    @Override
    public boolean userNameExists(String name) {
        Long userCount = (Long) getSession().getNamedQuery(User.GET_USER_COUNT)
                .setParameter("username", name).uniqueResult();
        return userCount > 0;
    }
}
