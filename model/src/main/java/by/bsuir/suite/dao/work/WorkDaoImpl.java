package by.bsuir.suite.dao.work;

import by.bsuir.suite.dao.common.GenericDaoImpl;
import by.bsuir.suite.domain.work.Work;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.hibernate.criterion.Restrictions.eq;

/**
 * @author d.matveenko
 */
@Repository
public class WorkDaoImpl extends GenericDaoImpl<Work> implements WorkDao {

    public WorkDaoImpl() {
        super(Work.class);
    }

    @Override
    public Work getWorkByPersonId(Long id) {
        Criteria criteria = getSession().createCriteria(getPersistentClass(), "work");
        criteria.add(eq("work.person.id", id));
        List<Work> result = criteria.list();
        if (!result.isEmpty()) {
            return result.get(0);
        }

        return null;
    }
}
