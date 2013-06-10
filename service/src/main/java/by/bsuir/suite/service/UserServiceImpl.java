package by.bsuir.suite.service;

import by.bsuir.suite.dao.UserDao;
import by.bsuir.suite.dao.person.PersonDao;
import by.bsuir.suite.disassembler.person.PersonInfoDtoDisassembler;
import by.bsuir.suite.dto.person.PersonInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by IntelliJ IDEA.
 * User: DenisKravchenko
 * Date: 20.07.12
 * Time: 1:37
 * To change this template use File | Settings | File Templates.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private PersonDao personDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PersonInfoDtoDisassembler personInfoDtoDisassembler;

    @Override
    public PersonInfoDto getUserByUsername(String name) {
        return personInfoDtoDisassembler.disassemble(personDao.getPersonByUsername(name));
    }

    @Override
    public Long getFloorIdByUsername(String name) {
        return personDao.getFloorIdByUserName(name);
    }

    @Override
    public boolean userNameExists(String name) {
        return userDao.userNameExists(name);
    }
}
