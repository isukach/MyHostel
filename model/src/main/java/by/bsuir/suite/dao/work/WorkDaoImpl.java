package by.bsuir.suite.dao.work;

import by.bsuir.suite.dao.common.GenericDaoImpl;
import by.bsuir.suite.domain.work.Work;
import org.springframework.stereotype.Repository;

/**
 * @author d.matveenko
 */
@Repository
public class WorkDaoImpl extends GenericDaoImpl<Work> implements WorkDao {

    public WorkDaoImpl() {
        super(Work.class);
    }
}
