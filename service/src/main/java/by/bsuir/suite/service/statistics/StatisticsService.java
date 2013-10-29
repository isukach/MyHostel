package by.bsuir.suite.service.statistics;

import by.bsuir.suite.dto.statistics.FloorStatisticsDto;

import java.util.List;

/**
 * @author i.sukach
 */
public interface StatisticsService {

    List<FloorStatisticsDto> getFloorStatistics(long floorId, String sort, boolean isAscending);

    FloorStatisticsDto getByPersonId(long personId);

    int getPersonCountForFloor(long floorId);
}
