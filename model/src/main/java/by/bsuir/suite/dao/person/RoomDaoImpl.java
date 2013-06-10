package by.bsuir.suite.dao.person;

import by.bsuir.suite.dao.common.GenericDaoImpl;
import by.bsuir.suite.domain.person.Room;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author i.sukach
 */
@Repository
public class RoomDaoImpl extends GenericDaoImpl<Room> implements RoomDao {

    public RoomDaoImpl() {
        super(Room.class);
    }

    @Override
    public Room getByHostelFloorAndRoomNumbers(int hostelNumber, String floorNumber, String roomNumber) {
       return (Room) getSession().getNamedQuery(Room.GET_BY_HOSTEL_FLOOR_AND_ROOM_NUMBERS)
                .setParameter("hostelNumber", hostelNumber)
                .setParameter("floorNumber", floorNumber)
                .setParameter("roomNumber", roomNumber).uniqueResult();
    }

    @Override
    public List<Room> findByFloorId(Long floorId) {
        Criteria criteria  = getSession().createCriteria(getPersistentClass(), "room");
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        if (floorId != null) {
            criteria.createAlias("room.floor", "roomFloor");
            criteria.add(Restrictions.eq("roomFloor.id", floorId));
        }
        return criteria.list();
    }
}
