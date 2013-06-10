package by.bsuir.suite.assembler.person;

import by.bsuir.suite.assembler.common.Assembler;
import by.bsuir.suite.domain.person.PenaltyReward;
import by.bsuir.suite.dto.person.PenaltyRewardDto;
import org.springframework.stereotype.Component;

/**
 * @author a.garelik
 */
@Component
public class PenaltyRewardAssembler implements Assembler<PenaltyRewardDto, PenaltyReward> {

    @Override
    public PenaltyReward assemble(PenaltyRewardDto dto) {
        PenaltyReward entity = new PenaltyReward();
        entity.setId(dto.getId());
        entity.setComment(dto.getComment());
        entity.setDate(dto.getDate());
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setType(dto.getType());
        return entity;
    }

    @Override
    public PenaltyReward updateEntityFields(PenaltyRewardDto dto, PenaltyReward entity) {
        return null;  //ToDo implement if need it
    }
}
