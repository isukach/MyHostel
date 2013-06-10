package by.bsuir.suite.disassembler.common;

import by.bsuir.suite.domain.Entity;
import by.bsuir.suite.dto.common.Dto;

import java.util.Collection;
import java.util.List;

/**
 * @author i.sukach
 */
public interface Disassembler<DTO extends Dto, ENTITY extends Entity> {

    DTO disassemble(ENTITY object);

    List<DTO> disassembleToList(Collection<ENTITY> entities);
}
