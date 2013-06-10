package by.bsuir.suite.disassembler.person;

import by.bsuir.suite.disassembler.common.BaseDisassembler;
import by.bsuir.suite.domain.person.Hostel;
import by.bsuir.suite.dto.person.HostelDto;
import org.springframework.stereotype.Component;

/**
 * @author d.shemerey
 */
@Component
public class HostelDtoDisassembler extends BaseDisassembler<HostelDto, Hostel> {

    @Override
    public HostelDto disassemble(Hostel hostel) {
        HostelDto dto = new HostelDto();
        dto.setId(hostel.getId()).setNumber(hostel.getNumber());
        return dto;
    }
}
