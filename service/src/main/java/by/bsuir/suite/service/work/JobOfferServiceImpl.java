package by.bsuir.suite.service.work;

import by.bsuir.suite.assembler.work.JobAssembler;
import by.bsuir.suite.assembler.work.JobOfferAssembler;
import by.bsuir.suite.dao.work.JobDao;
import by.bsuir.suite.dao.work.JobOfferDao;
import by.bsuir.suite.dao.work.WorkDao;
import by.bsuir.suite.disassembler.work.JobOfferDisassembler;
import by.bsuir.suite.domain.work.Job;
import by.bsuir.suite.domain.work.JobOffer;
import by.bsuir.suite.domain.work.Work;
import by.bsuir.suite.dto.work.AddUserWorkDto;
import by.bsuir.suite.dto.work.CommitJobOfferDto;
import by.bsuir.suite.dto.work.JobDto;
import by.bsuir.suite.dto.work.JobOfferDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * User: Matveyenka Denis
 * Date: 21.07.13
 */
@Service
@Transactional
public class JobOfferServiceImpl implements JobOfferService {

    @Autowired
    private JobOfferDao jobOfferDao;

    @Autowired
    private JobDao jobDao;

    @Autowired
    private WorkDao workDao;

    @Autowired
    private JobAssembler jobAssembler;

    @Autowired
    private JobOfferDisassembler jobOfferDisassembler;

    @Autowired
    private JobOfferAssembler jobOfferAssembler;

    @Override
    public Long getJobOfferCount() {
        return jobOfferDao.count();
    }

    @Override
    public List<JobOfferDto> findJobOffer(int first, int count) {
        return jobOfferDisassembler.disassembleToList(jobOfferDao.findFrom(first, count));
    }

    @Override
    public JobOfferDto getJobOfferById(Long id) {
        return jobOfferDisassembler.disassemble(jobOfferDao.get(id));
    }

    @Override
    public void update(JobOfferDto dto) {
        JobOffer jobOffer = jobOfferDao.get(dto.getId());
        jobOffer = jobOfferAssembler.updateEntityFields(dto, jobOffer);
        jobOfferDao.update(jobOffer);
    }

    @Override
    public void create(JobOfferDto dto) {
        jobOfferDao.create(jobOfferAssembler.assemble(dto));
    }

    @Override
    public void addJobsForAllPerson(List<CommitJobOfferDto> jobOffers) {
        for (CommitJobOfferDto commitJobOfferDto : jobOffers) {
            JobDto jobDto = getJobDto(commitJobOfferDto);
            Job job = jobAssembler.assemble(jobDto);
            job.setWork(workDao.getWorkByPersonId(commitJobOfferDto.getPersonJobOfferDto().getId()));
            jobDao.create(job);
        }
    }

    private JobDto getJobDto(CommitJobOfferDto commitJobOfferDto) {
        JobDto jobDto = new JobDto();
        jobDto.setDate(new Date());
        jobDto.setHours(String.valueOf(commitJobOfferDto.getHours()));
        jobDto.setDescription(commitJobOfferDto.getDescription());

        return jobDto;
    }
}
