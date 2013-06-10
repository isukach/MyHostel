package by.bsuir.suite.dao.lan;

import java.io.Serializable;

/**
 * @author i.sukach
 */
public interface LanUserDao extends Serializable {

    LanUser getByFirstNameLastNameAndGroupNumber(String firstName, String lastName, String groupNumber);
}
