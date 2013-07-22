package by.bsuir.suite.dao.work;

import by.bsuir.suite.dao.common.GenericDao;
import by.bsuir.suite.domain.work.Work;

/**
 * @author d.matveenko
 */
public interface WorkDao extends GenericDao<Work> {

    Work getWorkByPersonId(Long id);
}
