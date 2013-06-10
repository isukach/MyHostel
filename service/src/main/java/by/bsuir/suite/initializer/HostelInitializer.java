package by.bsuir.suite.initializer;

import by.bsuir.suite.dao.person.HostelDao;
import by.bsuir.suite.domain.person.Floor;
import by.bsuir.suite.domain.person.Hostel;
import by.bsuir.suite.domain.person.Room;
import by.bsuir.suite.initializer.property.FloorProperty;
import by.bsuir.suite.initializer.property.HostelProperty;
import by.bsuir.suite.initializer.property.RoomProperty;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author i.sukach
 */
public class HostelInitializer {

    private List<HostelProperty> hostels;
    
    @Autowired
    private HostelDao hostelDao;

    public void init() {
        for (HostelProperty currentHostel : hostels) {
            if (!hostelDao.checkHostelByNumber(currentHostel.getNumber())) {
                Hostel hostel = new Hostel();
                fillHostelWithFloors(hostel, currentHostel);
                hostelDao.create(hostel);
            }
        }
    }

    private void fillHostelWithFloors(Hostel hostel, HostelProperty data) {
        hostel.setNumber(data.getNumber());
        hostel.setAddress(data.getAddress());
        hostel.setStages(data.getFloors().size());
        hostel.setIsBlockType(data.isBlockType());
        hostel.setRank(data.getRank());
        Set<Floor> floors = new HashSet<Floor>();
        for (FloorProperty floorProperty : data.getFloors()) {
            Floor floor = new Floor();
            fillFloorWithRooms(floor, floorProperty);
            floor.setHostel(hostel);
            floors.add(floor);
        }
        hostel.setFloors(floors);
    }

    private void fillFloorWithRooms(Floor floor, FloorProperty data) {
        floor.setNumber(data.getName());
        floor.setRoomNumber(data.getRooms().getRoomCollection().size());
        Set<Room> rooms = new HashSet<Room>();
        for (RoomProperty roomProperty : data.getRooms().getRoomCollection()) {
            Room room = new Room();
            room.setNumber(roomProperty.getNumber());
            room.setPersonNumber(roomProperty.getMaxPopulation());
            room.setFloor(floor);
            rooms.add(room);
        }
        floor.setRooms(rooms);
    }

    public void setHostels(List<HostelProperty> hostels) {
        this.hostels = hostels;
    }
}
