package by.bsuir.suite.comparator;

import by.bsuir.suite.dto.person.RoomDto;

import java.io.Serializable;
import java.util.Comparator;

/**
 * @author i.sukach
 */
public class RoomDtoByNumberComparator implements Comparator<RoomDto>, Serializable {

    @Override
    public int compare(RoomDto firstRoom, RoomDto secondRoom) {
        return new HostelNumberComparator().compare(firstRoom.getRoomNumber(), secondRoom.getRoomNumber());
    }
}
