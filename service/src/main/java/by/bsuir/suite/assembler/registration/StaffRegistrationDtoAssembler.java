package by.bsuir.suite.assembler.registration;

import by.bsuir.suite.assembler.common.Assembler;
import by.bsuir.suite.dao.RoleDao;
import by.bsuir.suite.domain.Role;
import by.bsuir.suite.domain.User;
import by.bsuir.suite.domain.person.Person;
import by.bsuir.suite.dto.registration.StaffRegistrationDto;
import by.bsuir.suite.password.PasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author i.sukach
 */
@Component
public class StaffRegistrationDtoAssembler implements Assembler<StaffRegistrationDto, Person> {

    @Autowired
    private RoleDao roleDao;

    @Override
    public Person assemble(StaffRegistrationDto dto) {
        return updateEntityFields(dto, new Person());
    }

    @Override
    public Person updateEntityFields(StaffRegistrationDto dto, Person person) {
        Role role = roleDao.getRoleByName(dto.getRole());

        person.setFirstName(dto.getFirstName());
        person.setLastName(dto.getLastName());
        person.setMiddleName(dto.getMiddleName());

        String password = PasswordEncryptor.encrypt(dto.getPassword());
        User user = new User(dto.getUsername(), password, true);
        user.addRole(role);
        person.setUser(user);
        return person;
    }
}
