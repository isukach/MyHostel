package by.bsuir.suite.assembler.work;

import by.bsuir.suite.assembler.common.Assembler;
import by.bsuir.suite.dao.work.JobDao;
import by.bsuir.suite.domain.work.Job;
import by.bsuir.suite.dto.work.JobDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author d.matveenko
 */
@Component
public class JobAssembler implements Assembler<JobDto, Job> {

    @Autowired
    private JobDao jobDao;

    @Override
    public Job assemble(JobDto jobDto) {
        return updateEntityFields(jobDto, new Job());
    }

    @Override
    public Job updateEntityFields(JobDto jobDto, Job job) {
        job.setDescription(jobDto.getDescription());
        job.setHours(Integer.parseInt(jobDto.getHours()));
        job.setDate(new Date());
        return job;
    }

    public Set<Job> assembleToSet(Collection<JobDto> collection) {
        Set<Job> jobs = new HashSet<Job>();
        for (JobDto dto : collection) {
            if (dto.getId() != null) {
                jobs.add(updateEntityFields(dto, jobDao.get(dto.getId())));
            } else {
                jobs.add(assemble(dto));
            }
        }
        return jobs;
    }
}
