package by.bsuir.suite.disassembler.work;

import by.bsuir.suite.disassembler.common.BaseDisassembler;
import by.bsuir.suite.domain.work.Job;
import by.bsuir.suite.dto.work.JobDto;
import org.springframework.stereotype.Component;

/**
 * @author a.garelik
 */
@Component
public class JobDtoDisassembler extends BaseDisassembler<JobDto, Job> {
    @Override
    public JobDto disassemble(Job job) {
        JobDto jobDto = new JobDto();
        jobDto.setId(job.getId());
        jobDto.setDescription(job.getDescription());
        jobDto.setHours(String.valueOf(job.getHours()));
        jobDto.setDate(job.getDate());
        return jobDto;
    }
}
