package by.bsuir.suite.initializer;

import by.bsuir.suite.dao.RoleDao;
import by.bsuir.suite.dao.UserDao;
import by.bsuir.suite.dao.person.PersonDao;
import by.bsuir.suite.domain.User;
import by.bsuir.suite.domain.lan.Lan;
import by.bsuir.suite.domain.person.Faculty;
import by.bsuir.suite.domain.person.Person;
import by.bsuir.suite.domain.work.Work;
import by.bsuir.suite.password.PasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author i.sukach
 */
@Transactional
public class SuperUserInitializer {

    private static final String SUPER_USER_LOGIN = "ITSME";
    private static final String SUPER_USER_PASSWORD = "ILikeB00bies";

    @Autowired
    private UserDao userDao;

    @Autowired
    private PersonDao personDao;

    @Autowired
    private RoleDao roleDao;

    public void addSuperUserToDatabase() {
        if (userDao.getUserByUsername(SUPER_USER_LOGIN) == null) {
            Person person = new Person("Super", "User", Faculty.FITU, "822401");
            User user = new User(SUPER_USER_LOGIN, PasswordEncryptor.encrypt(SUPER_USER_PASSWORD), true);
            person.setUser(user);
            user.addRole(roleDao.getRoleByName(Roles.SUPER_USER));
            Lan lan = new Lan();
            lan.setActivated(true);
            person.setLan(lan);
            lan.setPerson(person);
            Work work = new Work();
            person.setWork(work);
            work.setPerson(person);
            personDao.update(person);
        }
    }
}
