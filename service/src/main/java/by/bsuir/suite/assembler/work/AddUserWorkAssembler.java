package by.bsuir.suite.assembler.work;

import by.bsuir.suite.assembler.common.Assembler;
import by.bsuir.suite.domain.work.Job;
import by.bsuir.suite.domain.work.Work;
import by.bsuir.suite.dto.work.AddUserWorkDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author d.matveenko
 */
@Component
public class AddUserWorkAssembler implements Assembler<AddUserWorkDto, Work> {

    @Autowired
    private JobAssembler jobAssembler;

    @Override
    public Work assemble(AddUserWorkDto addUserWorkDto) {
        return updateEntityFields(addUserWorkDto, new Work());
    }

    @Override
    public Work updateEntityFields(AddUserWorkDto addUserWorkDto, Work work) {
        Set<Job> jobs = jobAssembler.assembleToSet(addUserWorkDto.getJobs());
        work.setJobs(jobs);
        for (Job job : jobs) {
            if(job.getWork() == null) {
                job.setWork(work);
            }
        }
        return work;
    }
}
