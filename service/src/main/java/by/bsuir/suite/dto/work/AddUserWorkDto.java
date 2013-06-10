package by.bsuir.suite.dto.work;

import by.bsuir.suite.dto.common.Dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author d.matveenko
 */
public class AddUserWorkDto implements Dto {

    private Long id;

    private List<JobDto> jobs = new ArrayList<JobDto>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<JobDto> getJobs() {
        return jobs;
    }

    public void setJobs(List<JobDto> jobs) {
        this.jobs = jobs;
    }
}
