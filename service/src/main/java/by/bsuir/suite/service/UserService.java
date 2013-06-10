package by.bsuir.suite.service;

import by.bsuir.suite.dto.person.PersonInfoDto;

/**
 * Created by IntelliJ IDEA.
 * User: DenisKravchenko
 * Date: 20.07.12
 * Time: 1:34
 * To change this template use File | Settings | File Templates.
 */
public interface UserService {

    PersonInfoDto getUserByUsername(String name);

    Long getFloorIdByUsername(String name);

    boolean userNameExists(String name);
}
