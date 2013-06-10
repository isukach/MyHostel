package by.bsuir.suite.service.person;

import by.bsuir.suite.dao.person.FloorDao;
import by.bsuir.suite.dao.person.PersonDao;
import by.bsuir.suite.disassembler.person.FloorDtoDisassembler;
import by.bsuir.suite.domain.person.Floor;
import by.bsuir.suite.domain.person.Room;
import by.bsuir.suite.dto.person.FloorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author d.shemerey
 */
@Service
@Transactional
public class FloorServiceImpl implements FloorService {

    @Autowired
    private FloorDao floorDao;

    @Autowired
    private PersonDao personDao;

    @Autowired
    private FloorDtoDisassembler floorDtoDisassembler;

    @Override
    public List<FloorDto> getFloorsForHostelId(Long hostelId) {
        return floorDtoDisassembler.disassembleToList(floorDao.findByHostelId(hostelId));
    }

    @Override
    public FloorDto getFloorDtoByPersonId(Long personId) {
        Room room = personDao.get(personId).getRoom();
        if (room != null) {
            return floorDtoDisassembler.disassemble(room.getFloor());
        }
        throw new IllegalArgumentException("Person has no room!");
    }

    @Override
    public FloorDto get(Long id) {
        Floor floor = floorDao.get(id);
        if (floor == null) {
            throw new IllegalArgumentException("Could not find floor by provided id");
        }
        return floorDtoDisassembler.disassemble(floor);
    }
}
