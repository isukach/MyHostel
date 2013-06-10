package by.bsuir.suite.dao.person;

import by.bsuir.suite.dao.common.GenericDao;
import by.bsuir.suite.domain.person.Room;

import java.util.List;

/**
 * @author i.sukach
 */
public interface RoomDao extends GenericDao<Room> {

    Room getByHostelFloorAndRoomNumbers(int hostelNumber, String floorNumber, String roomNumber);

    List<Room> findByFloorId(Long floorId);
}
