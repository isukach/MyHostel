package by.bsuir.suite.service;

import by.bsuir.suite.assembler.role.RoleAssembler;
import by.bsuir.suite.dao.RoleDao;
import by.bsuir.suite.disassembler.role.RoleDtoDisassembler;
import by.bsuir.suite.dto.role.RoleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author i.sukach
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RoleDtoDisassembler roleDtoDisassembler;

    @Autowired
    private RoleAssembler roleAssembler;

    @Override
    public RoleDto get(Long id) {
        return roleDtoDisassembler.disassemble(roleDao.get(id));
    }

    @Override
    public void save(RoleDto roleDto) {
        if (roleDto.getId() == null) {
            roleDao.create(roleAssembler.assemble(roleDto));
        } else {
            roleDao.update(roleAssembler.updateEntityFields(roleDto, roleDao.get(roleDto.getId())));
        }
    }

    @Override
    public List<RoleDto> findFrom(int from, int count) {
        return roleDtoDisassembler.disassembleToList(roleDao.findFrom(from, count));
    }

    @Override
    public Long count() {
        return roleDao.count();
    }

    @Override
    public void delete(Long id) {
        roleDao.delete(roleDao.get(id));
    }
}
