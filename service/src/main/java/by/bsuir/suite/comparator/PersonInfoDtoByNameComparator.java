package by.bsuir.suite.comparator;

import by.bsuir.suite.dto.person.PersonInfoDto;

import java.io.Serializable;
import java.util.Comparator;

/**
 * @author i.sukach
 */
public class PersonInfoDtoByNameComparator implements Comparator<PersonInfoDto>, Serializable {

    @Override
    public int compare(PersonInfoDto firstPerson, PersonInfoDto secondPerson) {
        return firstPerson.getFullName().compareTo(secondPerson.getFullName());
    }
}
