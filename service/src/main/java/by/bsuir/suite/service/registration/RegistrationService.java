package by.bsuir.suite.service.registration;

import by.bsuir.suite.dto.registration.StaffRegistrationDto;
import by.bsuir.suite.dto.registration.RoomerRegistrationDto;

/**
 * @author i.sukach
 */
public interface RegistrationService {

    void registerRoomer(RoomerRegistrationDto roomer);

    void registerAdministrator(StaffRegistrationDto staff);
}
