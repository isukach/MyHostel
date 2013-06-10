package by.bsuir.suite.assembler.person;

import by.bsuir.suite.assembler.common.Assembler;
import by.bsuir.suite.dao.RoleDao;
import by.bsuir.suite.dao.person.RoomDao;
import by.bsuir.suite.domain.Role;
import by.bsuir.suite.domain.person.Faculty;
import by.bsuir.suite.domain.person.Person;
import by.bsuir.suite.domain.person.Room;
import by.bsuir.suite.dto.person.PersonDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @author i.sukach
 */
@Component
public class PersonDtoAssembler implements Assembler<PersonDto, Person> {
    @Autowired
    private RoleDao roleDao;
    
    @Autowired
    private RoomDao roomDao;
    
    @Override
    public Person assemble(PersonDto personDto) {
        return updateEntityFields(personDto, new Person());
    }

    @Override
    public Person updateEntityFields(PersonDto dto, Person person) {
        person.setFirstName(dto.getFirstName());
        person.setLastName(dto.getLastName());
        person.setAbout(dto.getAbout());
        person.setFrom(dto.getFrom());
        person.setFacilities(dto.getFacilities());
        person.setMiddleName(dto.getMiddleName());
        person.setUniversityGroup(dto.getGroup());
        person.setFaculty(Faculty.valueOf(dto.getFaculty().name()));
        person.setPhoneNumber(dto.getPhoneNumber());
        person.setAvatarPath(dto.getAvatarPath());

        person.getLink().setFacebook(dto.getFacebookLink());
        person.getLink().setTwitter(dto.getTwitterLink());
        person.getLink().setVk(dto.getVkLink());
        person.getLink().setSkype(dto.getSkypeLink());
        person.getLink().setDevart(dto.getDevartLink());
        person.getLink().setVimeo(dto.getVimeoLink());
        person.getLink().setGoogle(dto.getGoogleLink());
        person.getLink().setYoutube(dto.getYoutubeLink());
        person.getLink().setLastfm(dto.getLastfmLink());
        
        Role role = roleDao.getRoleByName(dto.getRole());
        Set<Role> roles = new HashSet<Role>();
        roles.add(role);
        person.getUser().setRoles(roles);
        
        Integer hostelNumber = dto.getHostel().getNumber();
        String floorNumber = dto.getFloor().getNumber();
        String roomNumber = dto.getRoom().getRoomNumber();
        Room room = roomDao.getByHostelFloorAndRoomNumbers(hostelNumber, floorNumber, roomNumber);
        person.setRoom(room);
        return person;
    }
}
