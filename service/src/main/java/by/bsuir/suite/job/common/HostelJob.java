package by.bsuir.suite.job.common;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author i.sukach
 */
public class HostelJob extends QuartzJobBean {
    private HostelTask hostelTask;

    public void setHostelTask(HostelTask hostelTask) {
        this.hostelTask = hostelTask;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        hostelTask.execute();
    }
}
