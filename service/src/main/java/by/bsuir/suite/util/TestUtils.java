package by.bsuir.suite.util;

import by.bsuir.suite.domain.Role;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains useful methods for testing.
 * @author i.sukach
 */
public final class TestUtils {

    private TestUtils() {}

    public static List<Role> generateRoleList(int number) {
        List<Role> roles = new ArrayList<Role>();
        for (int i = 0; i < number; i++) {
            roles.add(getNewRole());
        }
        return roles;
    }

    public static Role getNewRole() {
        Role role = new Role();
        role.setName(generateString());
        role.setDescription(generateString());
        return role;
    }

    public static String generateString() {
        int maxSymbolNumber = 10;
        int minSymbolNumber = 5;
        int numberOfSymbols = (int) (Math.random() * (maxSymbolNumber - minSymbolNumber) + minSymbolNumber);
        StringBuilder result = new StringBuilder("");
        int aLetterCode = 65;
        int zLetterCode = 90;
        for (int i = 0; i < numberOfSymbols; i++) {
            result.append((int)(Math.random() * (zLetterCode - aLetterCode) + aLetterCode));
        }
        return result.toString();
    }
}
