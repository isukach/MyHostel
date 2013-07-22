package by.bsuir.suite.page.work.model;

import by.bsuir.suite.dto.work.JobOfferDto;
import by.bsuir.suite.service.work.JobOfferService;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * User: Matveyenka Denis
 * Date: 20.07.13
 */
public class DetachableJobOfferTableModel extends LoadableDetachableModel<JobOfferDto> {

    @SpringBean
    @SuppressWarnings({"UnusedDeclaration"})
    private JobOfferService jobOfferService;

    private JobOfferDto jobOfferDto;


    public DetachableJobOfferTableModel(JobOfferDto jobOfferDto) {
        this.jobOfferDto = jobOfferDto;
        Injector.get().inject(this);
    }


    @Override
    protected JobOfferDto load() {
        return jobOfferService.getJobOfferById(jobOfferDto.getId());
    }

    @Override
    public int hashCode() {
        return jobOfferDto.getId().hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (obj instanceof DetachableJobOfferTableModel) {
            DetachableJobOfferTableModel other = (DetachableJobOfferTableModel) obj;
            return other.jobOfferDto.getId().equals(jobOfferDto.getId());
        }
        return false;
    }
}
