package by.bsuir.suite.util;

import by.bsuir.suite.domain.person.Person;
import by.bsuir.suite.dto.registration.RoomerRegistrationDto;

/**
 * @author i.sukach
 */
public final class PasswordUtils {

    private PasswordUtils() {
    }

    public static String generateDefaultPassword(Person person) {
        return person.getUniversityGroup() + person.getLastName()
                + person.getRoom().getFloor().getNumber() + person.getRoom().getNumber();
    }

    public static String generateDefaultPassword(RoomerRegistrationDto dto) {
        return dto.getGroupNumber() + dto.getLastName()
                + dto.getRoom().getFloorNumber() + dto.getRoom().getRoomNumber();
    }

    public static String generateDefaultUsername(RoomerRegistrationDto dto) {
        return dto.getFirstName() + dto.getLastName()
                + dto.getRoom().getFloorNumber() + dto.getRoom().getRoomNumber();
    }
}
