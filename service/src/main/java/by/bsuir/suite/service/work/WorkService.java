package by.bsuir.suite.service.work;

import by.bsuir.suite.dto.work.AddUserWorkDto;
import by.bsuir.suite.dto.work.JobDto;
import by.bsuir.suite.dto.work.WorkProgressBarDto;

/**
 * @author d.matveenko
 */
public interface WorkService {

    int calculateTotalHours(Long id);

    WorkProgressBarDto getProgressBarDtoById(Long id);

    WorkProgressBarDto getProgressBarDtoByPersonId(Long id);

    AddUserWorkDto getAddUserWorkDto(Long id);

    void update(AddUserWorkDto dto);

    JobDto duplicate(JobDto dto);
}
