package by.bsuir.suite.initializer;

import by.bsuir.suite.dao.RoleDao;
import by.bsuir.suite.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * @author i.sukach
 */
public class RoleInitializer {

    private static final Set<String> ROLES = new HashSet<String>();

    @Autowired
    private RoleDao roleDao;

    static {
        ROLES.add(Roles.USER);
        ROLES.add(Roles.ADMIN);
        ROLES.add(Roles.COMMANDANT);
        ROLES.add(Roles.FLOOR_HEAD);
        ROLES.add(Roles.EDUCATOR);
        ROLES.add(Roles.MANAGERESS);
        ROLES.add(Roles.REGISTRAR);
        ROLES.add(Roles.YOUTH_CENTER);
        ROLES.add(Roles.SUPER_USER);
    }

    public void fillDatabaseWithRoles() {
        for (String roleName : ROLES) {
            if (roleDao.getRoleByName(roleName) == null) {
                roleDao.create(new Role(roleName, roleName));
            }
        }
    }
}
