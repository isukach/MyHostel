package by.bsuir.suite.disassembler.person;

import by.bsuir.suite.disassembler.common.BaseDisassembler;
import by.bsuir.suite.domain.person.Room;
import by.bsuir.suite.dto.person.RoomDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author d.shemerey
 */
@Component
public class RoomDtoDisassembler extends BaseDisassembler<RoomDto, Room> {

    @Autowired
    private PersonInfoDtoDisassembler personInfoDtoDisassembler;

    @Override
    public RoomDto disassemble(Room room) {
        RoomDto dto = new RoomDto();
        dto.setId(room.getId())
                .setRoomNumber(room.getNumber())
                .setFloorNumber(room.getFloor().getNumber())
                .setHostelNumber(String.valueOf(room.getFloor().getHostel().getNumber()))
                .setPersons(personInfoDtoDisassembler.disassembleToList(room.getPersons()));
        return dto;
    }
}
