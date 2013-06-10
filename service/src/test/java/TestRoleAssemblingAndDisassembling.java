import by.bsuir.suite.assembler.role.RoleAssembler;
import by.bsuir.suite.disassembler.role.RoleDtoDisassembler;
import by.bsuir.suite.domain.Role;
import by.bsuir.suite.dto.role.RoleDto;
import by.bsuir.suite.util.TestUtils;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author i.sukach
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext-test.xml")
public class TestRoleAssemblingAndDisassembling {

    private static final int ROLE_COUNT = 20;

    @Autowired
    private RoleDtoDisassembler roleDtoDisassembler;

    @Autowired
    private RoleAssembler roleAssembler;

    @Test
    public void testDisassemblerNotNull() {
        Assert.assertNotNull(roleDtoDisassembler);
    }

    @Test
    public void testDisassemble() {
        Role role = TestUtils.getNewRole();
        RoleDto disassembledRole = roleDtoDisassembler.disassemble(role);
        Assert.assertEquals(role.getDescription(), disassembledRole.getDescription());
        Assert.assertEquals(role.getName(), disassembledRole.getName());
        Assert.assertNull(disassembledRole.getId());
    }

    @Test
    public void testAssemble() {
        Long id = 1L;
        String name = TestUtils.generateString();
        String description = TestUtils.generateString();
        RoleDto dto = new RoleDto().setId(id).setName(name).setDescription(description);

        Role role = roleAssembler.assemble(dto);

        Assert.assertNull(role.getId());
        Assert.assertEquals(role.getName(), name);
        Assert.assertEquals(role.getDescription(), description);
    }

    @Test
    public void testUpdateEntityFields() {
        String name = TestUtils.generateString();
        String description = TestUtils.generateString();
        RoleDto dto = new RoleDto().setName(name).setDescription(description);

        Role roleBeforeUpdate = TestUtils.getNewRole();
        Long id = 1L;
        roleBeforeUpdate.setId(id);

        Role roleAfterUpdate = roleAssembler.updateEntityFields(dto, roleBeforeUpdate);

        Assert.assertEquals(roleAfterUpdate.getId(), id);
        Assert.assertEquals(roleAfterUpdate.getName(), name);
        Assert.assertEquals(roleAfterUpdate.getDescription(), description);
    }

    @Test
    public void testDisassembleToList() {
        List<Role> roles = TestUtils.generateRoleList(ROLE_COUNT);
        List<RoleDto> roleDtos = roleDtoDisassembler.disassembleToList(roles);
        Assert.assertEquals(ROLE_COUNT, roleDtos.size());
        for (int i = 0; i < ROLE_COUNT; i++) {
            Assert.assertNull(roleDtos.get(i).getId());
        }
    }
}
