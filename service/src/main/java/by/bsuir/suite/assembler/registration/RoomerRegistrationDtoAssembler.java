package by.bsuir.suite.assembler.registration;

import by.bsuir.suite.assembler.common.Assembler;
import by.bsuir.suite.dao.RoleDao;
import by.bsuir.suite.dao.person.RoomDao;
import by.bsuir.suite.domain.Role;
import by.bsuir.suite.domain.User;
import by.bsuir.suite.domain.lan.Lan;
import by.bsuir.suite.domain.person.Faculty;
import by.bsuir.suite.domain.person.Link;
import by.bsuir.suite.domain.person.Person;
import by.bsuir.suite.domain.person.Room;
import by.bsuir.suite.domain.work.Work;
import by.bsuir.suite.dto.registration.RoomerRegistrationDto;
import by.bsuir.suite.password.PasswordEncryptor;
import by.bsuir.suite.util.HostelConstants;
import by.bsuir.suite.util.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author i.sukach
 */
@Component
public class RoomerRegistrationDtoAssembler implements Assembler<RoomerRegistrationDto, Person> {

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private RoleDao roleDao;

    @Override
    public Person assemble(RoomerRegistrationDto dto) {
        return updateEntityFields(dto, new Person());
    }

    @Override
    public Person updateEntityFields(RoomerRegistrationDto dto, Person person) {
        Role role = roleDao.getRoleByName(dto.getRole());
        Room room = roomDao.get(dto.getRoom().getId());

        person.setFirstName(dto.getFirstName());
        person.setLastName(dto.getLastName());
        person.setMiddleName(dto.getMiddleName());
        person.setPhoneNumber(dto.getPhoneNumber());
        person.setUniversityGroup(dto.getGroupNumber());
        person.setFaculty(Faculty.valueOf(dto.getFaculty().name()));
        person.setFrom(dto.getCity());

        Link link = new Link();
        link.setPerson(person);
        person.setLink(link);

        Work work = new Work();
        work.setPerson(person);
        person.setWork(work);

        Lan lan = new Lan();
        lan.setActivated(true);
        lan.setPerson(person);
        person.setLan(lan);

        person.setRequiredDuties(HostelConstants.DEFAULT_MAX_DUTIES_FOR_PERSON);

        room.getPersons().add(person);
        person.setRoom(room);

        String username = PasswordUtils.generateDefaultUsername(dto);
        String password = PasswordEncryptor.encrypt(PasswordUtils.generateDefaultPassword(dto));
        User user = new User(username, password, true);
        user.addRole(role);
        person.setUser(user);

        return person;
    }
}
