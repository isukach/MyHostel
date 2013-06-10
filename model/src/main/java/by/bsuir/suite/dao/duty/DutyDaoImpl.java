package by.bsuir.suite.dao.duty;

import by.bsuir.suite.dao.common.GenericDaoImpl;
import by.bsuir.suite.domain.duty.Duty;
import by.bsuir.suite.domain.duty.DutyStatus;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

import static org.hibernate.criterion.Restrictions.*;

/**
 * @author i.sukach
 */
@Repository
public class DutyDaoImpl extends GenericDaoImpl<Duty> implements DutyDao {

    public DutyDaoImpl() {
        super(Duty.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Duty> getDutiesForPerson(Long personId) {
        Criteria criteria = getSession().createCriteria(getPersistentClass(), "duty");
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        if (personId != null) {
            criteria.createAlias("duty.person", "dutyPerson");
            criteria.add(Restrictions.eq("dutyPerson.id", personId));
        }
        return criteria.list();
    }

    @Override
    public Long getUnskippedCountForPerson(Long personId) {
        Criteria criteria = getSession().createCriteria(getPersistentClass(), "duty");
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        if (personId != null) {
            criteria.createAlias("duty.person", "dutyPerson");
            criteria.add(eq("dutyPerson.id", personId));
            criteria.add(not(eq("duty.status", DutyStatus.SKIPPED)));
        }
        return (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
    }

    @Override
    public List<Duty> getPenaltyByDateFrom(int first, int count) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(
                and(eq("closed", false), or(eq("status", DutyStatus.COMPLETED_BAD), eq("status", DutyStatus.SKIPPED))));
        criteria.addOrder(Order.asc("date"));
        criteria.setFirstResult(first);
        criteria.setMaxResults(count);
        return criteria.list();
    }

    @Override
    public List<Duty> getPenaltyByPersonNameFrom(int first, int count) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(
                and(eq("closed", false), or(eq("status", DutyStatus.COMPLETED_BAD), eq("status", DutyStatus.SKIPPED))));
        criteria.createAlias("person", "person");
        criteria.addOrder(Order.asc("person.lastName"));
        criteria.setFirstResult(first);
        criteria.setMaxResults(count);
        return criteria.list();
    }

    @Override
    public List<Duty> getPenaltyByFloorFrom(int first, int count) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(
                and(eq("closed", false), or(eq("status", DutyStatus.COMPLETED_BAD), eq("status", DutyStatus.SKIPPED))));
        criteria.createAlias("person", "person");
        criteria.createAlias("person.room", "person_room");
        criteria.createAlias("person_room.floor", "person_room_floor");
        criteria.addOrder(Order.asc("person_room_floor.number"));
        criteria.addOrder(Order.asc("person_room.number"));
        criteria.setFirstResult(first);
        criteria.setMaxResults(count);
        return criteria.list();
    }

    @Override
    public Long getPenaltyCount() {
        Criteria criteria = getSession().createCriteria(getPersistentClass(), "duty");
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.add(or(eq("duty.status", DutyStatus.COMPLETED_BAD), eq("duty.status", DutyStatus.SKIPPED)));
        return (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Duty> getUnevaluatedDutiesBeforeDate(Date date) {
        Criteria criteria = getSession().createCriteria(getPersistentClass(), "duty");
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.add(eq("duty.status", DutyStatus.OCCUPIED));
        criteria.add(Restrictions.le("duty.date", date));

        return (List<Duty>) criteria.list();
    }
}
