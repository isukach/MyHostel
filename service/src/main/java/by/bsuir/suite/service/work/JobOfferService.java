package by.bsuir.suite.service.work;

import by.bsuir.suite.dto.work.CommitJobOfferDto;
import by.bsuir.suite.dto.work.JobOfferDto;

import java.util.List;

/**
 * User: Matveyenka Denis
 * Date: 21.07.13
 */
public interface JobOfferService {

    Long getJobOfferCount();

    List<JobOfferDto> findJobOffer(int first, int count);

    JobOfferDto getJobOfferById(Long id);

    void update(JobOfferDto dto);

    void create(JobOfferDto dto);

    void addJobsForAllPerson(List<CommitJobOfferDto> jobOffers);
}
