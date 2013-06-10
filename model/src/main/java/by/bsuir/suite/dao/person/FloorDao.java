package by.bsuir.suite.dao.person;

import by.bsuir.suite.dao.common.GenericDao;
import by.bsuir.suite.domain.person.Floor;

import java.util.List;

/**
 * @author i.sukach
 */
public interface FloorDao extends GenericDao<Floor> {

    List<Floor> findByHostelId(Long hostelId);

    int getFloorNumberByPersonId(long id);
}
