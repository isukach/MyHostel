package by.bsuir.suite.disassembler.lan;

import by.bsuir.suite.disassembler.common.BaseDisassembler;
import by.bsuir.suite.domain.lan.LanPayment;
import by.bsuir.suite.dto.lan.LanDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author : d.shemiarey
 */
@Component
public class LanDtoDisassembler extends BaseDisassembler<LanDto, LanPayment> {

    @Autowired
    private LanPaymentCellDtoDisassembler lanPaymentCellDtoDisassembler;

    @Override
    public LanDto disassemble(LanPayment lan) {
        LanDto dto = new LanDto();
        if (lan != null) {
            dto.setId(lan.getId());
            dto.setListLanPaymentCellDtos(lanPaymentCellDtoDisassembler.disassembleToListCells(lan));
        }
        return dto;
    }
}
