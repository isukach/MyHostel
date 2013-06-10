package by.bsuir.suite.dao;

import by.bsuir.suite.dao.common.GenericDao;
import by.bsuir.suite.domain.User;

/**
 * Created by IntelliJ IDEA.
 * User: DenisKravchenko
 * Date: 20.07.12
 * Time: 1:29
 * To change this template use File | Settings | File Templates.
 */
public interface UserDao extends GenericDao<User> {

    User getUserByUsername(String username);

    boolean userNameExists(String name);
}
