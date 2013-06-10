package by.bsuir.suite.disassembler.person;

import by.bsuir.suite.disassembler.common.BaseDisassembler;
import by.bsuir.suite.domain.person.PenaltyReward;
import by.bsuir.suite.dto.person.PenaltyRewardDto;
import org.springframework.stereotype.Component;

/**
 * @author a.garelik
 */
@Component
public class PenaltyRewardDisassembler extends BaseDisassembler<PenaltyRewardDto, PenaltyReward> {

    @Override
    public PenaltyRewardDto disassemble(PenaltyReward object) {
        PenaltyRewardDto dto = new PenaltyRewardDto();
        dto.setId(object.getId());
        dto.setComment(object.getComment());
        dto.setOrderNumber(object.getOrderNumber());
        dto.setDate(object.getDate());
        dto.setType(object.getType());
        dto.setPersonId(object.getPerson().getId());
        return dto;
    }
}
