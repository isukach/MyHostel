package by.bsuir.suite.disassembler.role;

import by.bsuir.suite.disassembler.common.BaseDisassembler;
import by.bsuir.suite.domain.Role;
import by.bsuir.suite.dto.role.RoleDto;
import org.springframework.stereotype.Component;

/**
 * @author i.sukach
 */
@Component
public class RoleDtoDisassembler extends BaseDisassembler<RoleDto, Role> {

    @Override
    public RoleDto disassemble(Role role) {
        RoleDto dto = new RoleDto();
        return dto.setId(role.getId())
                .setName(role.getName())
                .setDescription(role.getDescription());
    }
}
