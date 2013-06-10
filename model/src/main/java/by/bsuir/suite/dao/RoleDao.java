package by.bsuir.suite.dao;

import by.bsuir.suite.dao.common.GenericDao;
import by.bsuir.suite.domain.Role;

/**
 * @author i.sukach
 */
public interface RoleDao extends GenericDao<Role> {
    Role getRoleByName(String name);
}
