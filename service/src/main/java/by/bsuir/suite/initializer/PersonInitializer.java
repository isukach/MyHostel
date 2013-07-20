package by.bsuir.suite.initializer;

import by.bsuir.suite.dao.person.PersonDao;
import by.bsuir.suite.dao.person.RoomDao;
import by.bsuir.suite.domain.Role;
import by.bsuir.suite.domain.User;
import by.bsuir.suite.domain.lan.Lan;
import by.bsuir.suite.domain.person.Faculty;
import by.bsuir.suite.domain.person.Link;
import by.bsuir.suite.domain.person.Person;
import by.bsuir.suite.domain.person.Room;
import by.bsuir.suite.domain.work.Work;
import by.bsuir.suite.initializer.property.PersonProperty;
import by.bsuir.suite.initializer.property.RoleProperty;
import by.bsuir.suite.password.PasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;

/**
 * @author i.sukach
 */
public class PersonInitializer {

    private List<PersonProperty> persons;

    private boolean initializationEnabled;

    @Autowired
    private PersonDao personDao;

    @Autowired
    private RoomDao roomDao;

    public void init() {
        if (initializationEnabled) {
            for (PersonProperty personProperty : persons) {
                if (personDao.getPersonByUsername(personProperty.getUsername()) == null) {
                    Person person = new Person();
                    person.setLastName(personProperty.getLastName());
                    person.setMiddleName(personProperty.getMiddleName());
                    person.setAbout(personProperty.getAbout());
                    person.setFirstName(personProperty.getFirstName());
                    person.setEmail(personProperty.getEmail());
                    person.setUniversityGroup(personProperty.getUniversityGroup());
                    person.setPhoneNumber(personProperty.getTel());
                    person.setFaculty(Faculty.FITU);
                    person.setRequiredDuties(personProperty.getMaxDuties());

                    Work work = new Work();
                    work.setPerson(person);
                    person.setWork(work);

                    User user = new User();
                    user.setActive(true);
                    user.setUsername(personProperty.getUsername());
                    user.setPassword(PasswordEncryptor.encrypt(personProperty.getPassword()));
                    fillUserWithRoles(user, personProperty);
                    person.setUser(user);

                    Lan lan = new Lan();
                    lan.setActivated(true);
                    lan.setIp("172.31.0.00");
                    lan.setPerson(person);
                    person.setLan(lan);

                    Link link = new Link();
                    link.setPerson(person);
                    person.setLink(link);

                    person.setResidenceStatus(personProperty.getResidenceStatus());

                    if (personProperty.getHostel() != 0 && personProperty.getRoom()
                            != null && personProperty.getFloor() != null) {
                        Room room = roomDao.getByHostelFloorAndRoomNumbers(
                                personProperty.getHostel(), personProperty.getFloor(), personProperty.getRoom());
                        person.setRoom(room);
                        List<Person> personList = personDao.findByRoomId(room.getId());
                        personList.add(person);
                        room.setPersons(new HashSet<Person>(personList));
                    }


                    personDao.update(person);
                }
            }
        }
    }

    private void fillUserWithRoles(User user, PersonProperty data) {
        for (RoleProperty roleProperty : data.getRoles()) {
            Role role = new Role();
            role.setName(roleProperty.getName());
            role.getUsers().add(user);
            role.setDescription(roleProperty.getDescription());
            user.getRoles().add(role);
        }
    }

    public List<PersonProperty> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonProperty> persons) {
        this.persons = persons;
    }

    public boolean isInitializationEnabled() {
        return initializationEnabled;
    }

    public void setInitializationEnabled(boolean initializationEnabled) {
        this.initializationEnabled = initializationEnabled;
    }
}
