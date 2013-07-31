package by.bsuir.suite.dao.work;

import by.bsuir.suite.dao.common.GenericDao;
import by.bsuir.suite.domain.person.Person;
import by.bsuir.suite.domain.work.JobOffer;

import java.util.Date;
import java.util.List;

/**
 * User: Matveyenka Denis
 * Date: 20.06.13
 */
public interface JobOfferDao extends GenericDao<JobOffer> {

    List<JobOffer> getJobListByDate(Date theTargetDate);

    List<Person> getListJobPersonsByDate(Date targetDate);
}
