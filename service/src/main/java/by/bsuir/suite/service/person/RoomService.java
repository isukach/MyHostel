package by.bsuir.suite.service.person;

import by.bsuir.suite.dto.person.RoomDto;

import java.util.List;

/**
 * @author d.shemerey
 */
public interface RoomService  {

    List<RoomDto> getRoomsForFloorId(Long floorId);

}
