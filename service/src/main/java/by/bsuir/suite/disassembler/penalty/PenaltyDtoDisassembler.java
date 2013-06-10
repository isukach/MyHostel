package by.bsuir.suite.disassembler.penalty;

import by.bsuir.suite.disassembler.common.BaseDisassembler;
import by.bsuir.suite.domain.duty.Duty;
import by.bsuir.suite.dto.penalty.PenaltyDto;
import by.bsuir.suite.util.EntityUtils;
import org.springframework.stereotype.Component;

/**
 * User: CHEB
 */
@Component
public class PenaltyDtoDisassembler extends BaseDisassembler<PenaltyDto, Duty> {

    @Override
    public PenaltyDto disassemble(Duty duty) {
        PenaltyDto dto = new PenaltyDto();
        dto.setDutyId(duty.getId());
        dto.setDate(duty.getDate());
        dto.setDescription(duty.getStatusComment());
        if (duty.getPerson() != null) {
            dto.setPersonId(duty.getPerson().getId());
            dto.setFullName(EntityUtils.generatePersonCalendarName(duty.getPerson()));
            if (duty.getPerson().getRoom() != null) {
                dto.setRoom(duty.getPerson().getRoom().getFloor().getNumber() + duty.getPerson().getRoom().getNumber());
            }
        }
        return dto;
    }
}
