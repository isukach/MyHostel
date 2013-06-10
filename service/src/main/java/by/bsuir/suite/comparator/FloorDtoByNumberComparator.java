package by.bsuir.suite.comparator;

import by.bsuir.suite.dto.person.FloorDto;

import java.io.Serializable;
import java.util.Comparator;

/**
 * @author i.sukach
 */
public class FloorDtoByNumberComparator implements Comparator<FloorDto>, Serializable {

    @Override
    public int compare(FloorDto firstFloor, FloorDto secondFloor) {
        return new HostelNumberComparator().compare(firstFloor.getNumber(), secondFloor.getNumber());
    }
}
