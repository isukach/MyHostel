package by.bsuir.suite.exporter;


import by.bsuir.suite.domain.duty.Duty;

import java.util.Set;

/**
 * @author a.garelik
 */
public interface DutyData {

    String getMonth();
    String getYear();
    String getFloor();
    String getFlorHead();
    String getEducator();
    String getHostel();
    Set<Duty> getDuties();
}
