package by.bsuir.suite.service.work;

import by.bsuir.suite.dao.person.PersonDao;
import by.bsuir.suite.disassembler.work.JobDtoDisassembler;
import by.bsuir.suite.domain.work.Job;
import by.bsuir.suite.domain.work.Work;
import by.bsuir.suite.dto.work.JobDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author d.matveenko
 */
@Service
@Transactional
public class JobServiceImpl implements JobService {

    @Autowired
    private JobDtoDisassembler jobDtoDisassembler;

    @Autowired
    private PersonDao personDao;

    @Override
    public List<JobDto> getJobsByPersonId(Long id) {
        List<JobDto> list = new ArrayList<JobDto>();
        Work work = personDao.get(id).getWork();
        if (work != null) {
            for (Job job : work.getJobs()){
                list.add(jobDtoDisassembler.disassemble(job));
            }
        }
        return list;
    }
}
