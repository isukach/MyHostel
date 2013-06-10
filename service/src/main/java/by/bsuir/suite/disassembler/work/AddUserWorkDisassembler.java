package by.bsuir.suite.disassembler.work;

import by.bsuir.suite.disassembler.common.BaseDisassembler;
import by.bsuir.suite.domain.work.Work;
import by.bsuir.suite.dto.work.AddUserWorkDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author d.matveenko
 */
@Component
public class AddUserWorkDisassembler extends BaseDisassembler<AddUserWorkDto, Work> {

    @Autowired
    private JobDtoDisassembler jobDtoDisassembler;

    @Override
    public AddUserWorkDto disassemble(Work object) {
        AddUserWorkDto dto = new AddUserWorkDto();
        dto.setId(object.getId());
        dto.setJobs(jobDtoDisassembler.disassembleToList(object.getJobs()));
        return dto;
    }
}
