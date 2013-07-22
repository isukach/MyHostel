package by.bsuir.suite.initializer;

import by.bsuir.suite.dao.person.PersonDao;
import by.bsuir.suite.dao.work.JobOfferDao;
import by.bsuir.suite.domain.person.Person;
import by.bsuir.suite.domain.work.JobOffer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * User: Matveyenka Denis
 * Date: 20.07.13
 */
public class JobOfferInitializer {

    @Autowired
    private JobOfferDao jobOfferDao;

    public void init() {
        if (jobOfferDao.count() == 0) {
            for (int i = 0; i < 10; i++) {
                JobOffer jobOffer = new JobOffer();
                jobOffer.setDescription("Description" + (i + 1));
                jobOffer.setNumberOfPeople(i + 1);
                jobOffer.setDate(new Date());
                jobOffer.setHours(i + 1);
                jobOffer.setActive(true);
                jobOfferDao.create(jobOffer);
            }
        }
    }
}
