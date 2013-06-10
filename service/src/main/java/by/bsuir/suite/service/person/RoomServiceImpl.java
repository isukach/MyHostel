package by.bsuir.suite.service.person;

import by.bsuir.suite.dao.person.RoomDao;
import by.bsuir.suite.disassembler.person.RoomDtoDisassembler;
import by.bsuir.suite.dto.person.RoomDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author d.shemerey
 */
@Service
@Transactional
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomDtoDisassembler roomDtoDisassembler;

    @Autowired
    private RoomDao roomDao;

    @Override
    public List<RoomDto> getRoomsForFloorId(Long floorId) {
        return roomDtoDisassembler.disassembleToList(roomDao.findByFloorId(floorId));
    }
}
