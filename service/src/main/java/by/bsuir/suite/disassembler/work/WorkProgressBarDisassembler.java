package by.bsuir.suite.disassembler.work;

import by.bsuir.suite.disassembler.common.BaseDisassembler;
import by.bsuir.suite.domain.work.Job;
import by.bsuir.suite.domain.work.Work;
import by.bsuir.suite.dto.work.WorkProgressBarDto;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author d.matveenko
 */
@Component
public class WorkProgressBarDisassembler extends BaseDisassembler<WorkProgressBarDto, Work> {

    @Override
    public WorkProgressBarDto disassemble(Work object) {
        if (object != null) {
            WorkProgressBarDto dto = new WorkProgressBarDto();
            dto.setId(object.getId());
            int totalHours = 0;
            Set<Job> jobs = object.getJobs();
            for (Job job : jobs) {
                totalHours += job.getHours();
            }
            dto.setRequiredHours(object.getTotalRequiredHours());
            dto.setTotalHours(totalHours);
            return dto;
        }
        return new WorkProgressBarDto();

    }
}
