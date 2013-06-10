import by.bsuir.suite.dao.RoleDao;
import by.bsuir.suite.domain.Role;
import by.bsuir.suite.util.TestUtils;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author i.sukach
 */
@ContextConfiguration(locations = "classpath:applicationContext-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class GenericDaoTest {

    private static final int NUMBER_OF_ROLES = 20;

    private static final int NUMBER_OF_ROLES_TO_DELETE = 10;

    private static final String UPDATED_NAME = "New name";

    private static final String UPDATED_DESCRIPTION = "New description";

    private static final int NUMBER_OF_ROLES_TO_RETRIEVE = 5;

    private static final List<Role> ROLES = new ArrayList<Role>();

    private static Role role;

    private static Long roleId;

    @Autowired
    private RoleDao roleDao;

    @Test
    public void testRoleDaoNotNull(){
        Assert.assertNotNull(roleDao);
    }

    @Test
    @Rollback(false)
    public void testRoleSaving() {
        role = TestUtils.getNewRole();
        roleDao.create(role);
        roleId = role.getId();
        Assert.assertNotNull(roleId);
    }

    @Test
    public void testGetRole() {
        Role savedRole = roleDao.get(roleId);
        Assert.assertEquals(role.getName(), savedRole.getName());
        Assert.assertEquals(role.getDescription(), savedRole.getDescription());
    }

    @Test
    @Rollback(false)
    public void testUpdateRole() {
        Role roleToUpdate = roleDao.get(roleId);
        roleToUpdate.setName(UPDATED_NAME);
        roleToUpdate.setDescription(UPDATED_DESCRIPTION);
        roleDao.update(roleToUpdate);
    }

    @Test
    public void testGetUpdatedRole() {
        Role updatedRole = roleDao.get(roleId);
        Assert.assertEquals(UPDATED_DESCRIPTION, updatedRole.getDescription());
        Assert.assertEquals(UPDATED_NAME, updatedRole.getName());
    }

    @Test
    @Rollback(false)
    public void testDeleteSingleRole() {
        roleDao.delete(roleDao.get(roleId));
    }

    @Test
    @Rollback(false)
    public void testSaveRoleCollection() {
        for (int i = 0; i < NUMBER_OF_ROLES; i++) {
            Role role = TestUtils.getNewRole();
            ROLES.add(role);
            roleDao.create(role);
        }
    }

    @Test
    public void testRoleCount() {
        Assert.assertEquals(NUMBER_OF_ROLES, roleDao.count().intValue());
    }

    @Test
    @Rollback(false)
    public void testDeletePartOfSavedRoles() {
        for (int i = 0; i < NUMBER_OF_ROLES_TO_DELETE; i++) {
            roleDao.delete(ROLES.get(i));
        }
    }

    @Test
    public void testRoleCountAfterDeletion() {
        Assert.assertEquals(NUMBER_OF_ROLES - NUMBER_OF_ROLES_TO_DELETE, roleDao.count().intValue());
    }

    @Test
    public void testFindFromTo() {
        Assert.assertEquals(NUMBER_OF_ROLES_TO_RETRIEVE, roleDao.findFrom(2, NUMBER_OF_ROLES_TO_RETRIEVE).size());
    }

    @Test
    public void testFindAll() {
        Assert.assertEquals(NUMBER_OF_ROLES - NUMBER_OF_ROLES_TO_DELETE, roleDao.findAll().size());
    }
}
