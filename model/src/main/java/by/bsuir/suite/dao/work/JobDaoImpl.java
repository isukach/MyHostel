package by.bsuir.suite.dao.work;

import by.bsuir.suite.dao.common.GenericDaoImpl;
import by.bsuir.suite.domain.work.Job;
import org.springframework.stereotype.Repository;

/**
 * @author d.matveenko
 */
@Repository
public class JobDaoImpl extends GenericDaoImpl<Job> implements JobDao {

    public JobDaoImpl() {
        super(Job.class);
    }
}
