package by.bsuir.suite.service.work;

import by.bsuir.suite.dto.work.JobDto;

import java.util.List;

/**
 * @author d.matveenko
 */
public interface JobService {

    List<JobDto> getJobsByPersonId(Long id);
}
