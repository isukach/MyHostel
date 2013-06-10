package by.bsuir.suite.service;

import by.bsuir.suite.dto.role.RoleDto;

import java.util.List;

/**
 * @author i.sukach
 */
public interface RoleService {

    RoleDto get(Long id);

    /**
     * Creates or updates role entity in the database.
     * Depending on the presence of `id` field value.
     * @param roleDto contains data to save.
     */
    void save(RoleDto roleDto);

    Long count();

    void delete(Long id);

    List<RoleDto> findFrom(int from, int count);
}
