package by.bsuir.suite.dao;

import by.bsuir.suite.dao.common.GenericDaoImpl;
import by.bsuir.suite.domain.Role;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * @author i.sukach
 */
@Repository
public class RoleDaoImpl extends GenericDaoImpl<Role> implements RoleDao {

    public RoleDaoImpl() {
        super(Role.class);
    }

    @Override
    public Role getRoleByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("No role provided!");
        }
        Criteria criteria = getSession().createCriteria(getPersistentClass(), "role");
        criteria.add(Restrictions.eq("role.name", name));
        return (Role) criteria.uniqueResult();
    }
}
