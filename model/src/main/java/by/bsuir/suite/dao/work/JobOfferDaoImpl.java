package by.bsuir.suite.dao.work;

import by.bsuir.suite.dao.common.GenericDaoImpl;
import by.bsuir.suite.domain.person.Person;
import by.bsuir.suite.domain.work.JobOffer;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hibernate.criterion.Restrictions.eq;

/**
 * User: Matveyenka Denis
 * Date: 20.06.13
 */
@Repository
public class JobOfferDaoImpl extends GenericDaoImpl<JobOffer> implements JobOfferDao {

    public JobOfferDaoImpl() {
        super(JobOffer.class);
    }

    @Override
    public Long count(){
        Criteria criteria = getSession().createCriteria(getPersistentClass(), "jobOffer");
        criteria.add(eq("jobOffer.active", true));
        return (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<JobOffer> findFrom(final Integer from, final Integer count){
        Criteria criteria = getSession().createCriteria(getPersistentClass(), "jobOffer");
        criteria.addOrder(Order.desc("date"));
        criteria.add(eq("jobOffer.active", true));
        criteria.setFirstResult(from);
        criteria.setMaxResults(count);

        return criteria.list();
    }

    @Override
    public List<JobOffer> getJobListByDate(Date targetDate) {
        Criteria criteria = getSession().createCriteria(getPersistentClass(), "jobOffer");
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.add(Restrictions.eq("jobOffer.date", targetDate));
        return (List<JobOffer>) criteria.list();
    }

    @Override
    public List<Person> getListJobPersonsByDate(Date targetDate) {
        Criteria criteria = getSession().createCriteria(getPersistentClass(), "jobOffer");
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.add(Restrictions.eq("jobOffer.date", targetDate));
        List<Person> theRes = new ArrayList<Person>();
        for (Object job : criteria.list())
            theRes.addAll(((JobOffer)job).getPersons());
        return theRes;
    }
}
