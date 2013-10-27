package by.bsuir.suite.dao.person;

import by.bsuir.suite.dao.common.GenericDao;
import by.bsuir.suite.domain.person.Person;

import java.util.List;

/**
 * @author i.sukach
 */
public interface PersonDao extends GenericDao<Person> {

    Person getPersonByUsername(String username);

    Long getFloorIdByUserName(String username);

    List<Person> findByRoomId(Long roomId);

    List<Person> findByFloorId(long floorId, String sortBy);

    List<Person> searchForPerson(int from, String search);

    Long personSearchCount(String search);

    List<Long> getActivePersonIds();

    int getCountForFloor(Long floorId);
}
