package by.bsuir.suite.dto.role;

import by.bsuir.suite.dto.common.Dto;

/**
 * @author i.sukach
 */
public class RoleDto implements Dto {

    private Long id;

    private String name;

    private String description;

    public String getName() {
        return this.name;
    }

    public RoleDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public RoleDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public Long getId() {
        return this.id;
    }

    public RoleDto setId(Long id) {
        this.id = id;
        return this;
    }
}
