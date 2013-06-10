package by.bsuir.suite.dao.duty;

import by.bsuir.suite.dao.common.GenericDaoImpl;
import by.bsuir.suite.domain.duty.Month;
import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author i.sukach
 */
@Repository
public class MonthDaoImpl extends GenericDaoImpl<Month> implements MonthDao {

    public MonthDaoImpl() {
        super(Month.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Month> findByEnabledAndFloorId(Long floorId) {
        return (List<Month>) getSession().getNamedQuery(Month.FIND_ENABLED_BY_FLOOR_ID)
                .setParameter("floorId", floorId).list();
    }

    @Override
    public Month findMonthByMonthYearAndFloorId(int month, int year, Long floorId) {
        Criteria criteria = getSession().createCriteria(getPersistentClass(), "month");
        Conjunction conjunction = Restrictions.conjunction();
        criteria.add(Restrictions.eq("month.month", month));
        criteria.add(Restrictions.eq("month.year", year));
        if (floorId != null) {
            criteria.createAlias("month.floor", "monthFloor");
            criteria.add(Restrictions.eq("monthFloor.id", floorId));
        }
        criteria.add(conjunction);

        return (Month) criteria.uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Month> findByFloorId(Long floorId) {
        Criteria criteria = getSession().createCriteria(getPersistentClass(), "month");
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.createAlias("month.floor", "monthFloor");
        criteria.add(Restrictions.eq("monthFloor.id", floorId));
        return criteria.list();
    }
}
