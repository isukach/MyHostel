package by.bsuir.suite.disassembler.person;

import by.bsuir.suite.disassembler.common.BaseDisassembler;
import by.bsuir.suite.domain.person.Floor;
import by.bsuir.suite.domain.person.Room;
import by.bsuir.suite.dto.person.FloorDto;
import org.springframework.stereotype.Component;

/**
 * @author d.shemerey
 */
@Component
public class FloorDtoDisassembler extends BaseDisassembler<FloorDto, Floor> {

    @Override
    public FloorDto disassemble(Floor floor) {
        FloorDto dto = new FloorDto();
        dto.setId(floor.getId())
                .setNumber(floor.getNumber())
                .setMaxPopulation(calculateMaxFloorPopulation(floor))
                .setCurrentPopulation(calculateCurrentFloorPopulation(floor));
        return dto;
    }

    private int calculateMaxFloorPopulation(Floor floor) {
       return floor.getRooms().size() * Room.MAX_ROOM_POPULATION;
    }

    private int calculateCurrentFloorPopulation(Floor floor) {
        int result = 0;
        for (Room room : floor.getRooms()) {
            result += room.getPersons().size();
        }
        return result;
    }
}
