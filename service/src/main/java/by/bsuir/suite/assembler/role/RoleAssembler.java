package by.bsuir.suite.assembler.role;

import by.bsuir.suite.assembler.common.Assembler;
import by.bsuir.suite.domain.Role;
import by.bsuir.suite.dto.role.RoleDto;
import org.springframework.stereotype.Component;

/**
 * @author i.sukach
 */
@Component
public class RoleAssembler implements Assembler<RoleDto, Role> {

    @Override
    public Role assemble(RoleDto roleDto) {
        return updateEntityFields(roleDto, new Role());
    }

    @Override
    public Role updateEntityFields(RoleDto roleDto, Role role) {
        role.setName(roleDto.getName());
        role.setDescription(roleDto.getDescription());
        return role;
    }
}
