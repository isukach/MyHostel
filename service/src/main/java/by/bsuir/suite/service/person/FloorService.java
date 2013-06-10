package by.bsuir.suite.service.person;

import by.bsuir.suite.dto.person.FloorDto;

import java.util.List;

/**
 * @author d.shemerey
 */
public interface FloorService {

    List<FloorDto> getFloorsForHostelId(Long hostelId);

    FloorDto getFloorDtoByPersonId(Long personId);

    FloorDto get(Long id);
}
