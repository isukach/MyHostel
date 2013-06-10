package by.bsuir.suite.assembler.common;

import by.bsuir.suite.domain.Entity;
import by.bsuir.suite.dto.common.Dto;

/**
 * @author i.sukach
 */
public interface Assembler<DTO extends Dto, ENTITY extends Entity> {

    ENTITY assemble(DTO dto);

    ENTITY updateEntityFields(DTO dto, ENTITY entity);
}
