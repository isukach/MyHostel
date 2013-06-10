package by.bsuir.suite.disassembler.common;

import by.bsuir.suite.domain.Entity;
import by.bsuir.suite.dto.common.Dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Used to convert entities to data transfer objects.
 * @author i.sukach
 */
public abstract class BaseDisassembler<DTO extends Dto, ENTITY extends Entity> implements Disassembler<DTO, ENTITY> {

    @Override
    public abstract DTO disassemble(ENTITY object);

    @Override
    public List<DTO> disassembleToList(Collection<ENTITY> entities) {
        List<DTO> resultList = new ArrayList<DTO>();
        for (ENTITY entity : entities) {
            resultList.add(disassemble(entity));
        }
        return resultList;
    }
}
