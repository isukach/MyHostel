package by.bsuir.suite.service.work;

import by.bsuir.suite.assembler.work.AddUserWorkAssembler;
import by.bsuir.suite.dao.person.PersonDao;
import by.bsuir.suite.dao.work.JobOfferDao;
import by.bsuir.suite.dao.work.WorkDao;
import by.bsuir.suite.disassembler.work.AddUserWorkDisassembler;
import by.bsuir.suite.disassembler.work.JobOfferDisassembler;
import by.bsuir.suite.disassembler.work.WorkProgressBarDisassembler;
import by.bsuir.suite.domain.work.Job;
import by.bsuir.suite.domain.work.Work;
import by.bsuir.suite.dto.work.AddUserWorkDto;
import by.bsuir.suite.dto.work.JobDto;
import by.bsuir.suite.dto.work.JobOfferDto;
import by.bsuir.suite.dto.work.WorkProgressBarDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author d.matveenko
 */
@Service
@Transactional
public class WorkServiceImpl implements WorkService {

    @Autowired
    private WorkDao workDao;

    @Autowired
    private PersonDao personDao;

    @Autowired
    private WorkProgressBarDisassembler workProgressBarDisassembler;

    @Autowired
    private AddUserWorkDisassembler addUserWorkDisassembler;

    @Autowired
    private AddUserWorkAssembler addUserWorkAssembler;

    @Override
    public int calculateTotalHours(Long id) {
        Work work = workDao.get(id);
        int jobHours = 0;
        if (work != null) {
            for(Job job : work.getJobs()) {
                jobHours += job.getHours();
            }
        }
        return jobHours;
    }

    @Override
    public WorkProgressBarDto getProgressBarDtoById(Long id) {
        return workProgressBarDisassembler.disassemble(workDao.get(id));
    }

    @Override
    public WorkProgressBarDto getProgressBarDtoByPersonId(Long id) {
        return workProgressBarDisassembler.disassemble(workDao.get(personDao.get(id).getWork().getId()));
    }

    @Override
    public AddUserWorkDto getAddUserWorkDto(Long id) {
        return addUserWorkDisassembler.disassemble(workDao.get(id));
    }    

    @Override
    public void update(AddUserWorkDto dto) {
        if (dto.getId() != null) {
            Work work = workDao.get(dto.getId());
            addUserWorkAssembler.updateEntityFields(dto, work);
            workDao.update(work);
        } else {
            Work newWork = addUserWorkAssembler.assemble(dto);
            workDao.update(newWork);
        }
    }

    @Override
    public JobDto duplicate(JobDto dto) {
        JobDto duplicate = new JobDto();
        duplicate.setDescription(dto.getDescription());
        duplicate.setHours(dto.getHours());
        duplicate.setDate(new Date());
        return duplicate;
    }
}
