package by.bsuir.suite.dao.person;

import by.bsuir.suite.dao.common.GenericDao;
import by.bsuir.suite.domain.person.Hostel;

/**
 * @author i.sukach
 */
public interface HostelDao extends GenericDao<Hostel> {

    boolean checkHostelByNumber(int number);

    Hostel getHostelByNumber(int number);
}
