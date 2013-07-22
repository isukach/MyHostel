package by.bsuir.suite.dao.work;

import by.bsuir.suite.dao.common.GenericDaoImpl;
import by.bsuir.suite.domain.work.JobOffer;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;

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
}
