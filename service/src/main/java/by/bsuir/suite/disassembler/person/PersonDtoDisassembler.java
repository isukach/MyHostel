package by.bsuir.suite.disassembler.person;

import by.bsuir.suite.disassembler.common.BaseDisassembler;
import by.bsuir.suite.domain.person.Person;
import by.bsuir.suite.dto.person.FacultyDto;
import by.bsuir.suite.dto.person.PersonDto;
import by.bsuir.suite.dto.person.ResidenceStatusDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static by.bsuir.suite.util.CommonUtils.getCourseByUniversityGroup;

/**
 * @author i.sukach
 */
@Component
public class PersonDtoDisassembler extends BaseDisassembler<PersonDto, Person> {

    @Autowired
    private RoomDtoDisassembler roomDtoDisassembler;

    @Autowired
    private FloorDtoDisassembler floorDtoDisassembler;

    @Autowired
    private HostelDtoDisassembler hostelDtoDisassembler;

    @Override
    public PersonDto disassemble(Person person) {
        PersonDto dto = new PersonDto();
        dto.setId(person.getId());
        dto.setFirstName(person.getFirstName());
        dto.setLastName(person.getLastName());
        dto.setMiddleName(person.getMiddleName());
        dto.setFaculty(FacultyDto.valueOf(person.getFaculty().name()));
        dto.setGroup(person.getUniversityGroup());
        dto.setCourse(String.valueOf(getCourseByUniversityGroup(person.getUniversityGroup())));
        dto.setRole(person.getUser().getRoles().iterator().next().getDescription());
        dto.setFrom(person.getFrom());
        dto.setFacilities(person.getFacilities());
        dto.setAbout(person.getAbout());
        dto.setPhoneNumber(person.getPhoneNumber());
        dto.setAvatarPath(person.getAvatarPath());
        dto.setUsername(person.getUser().getUsername());
        dto.setResidenceStatus(ResidenceStatusDto.valueOf(person.getResidenceStatus().name()));

        if (person.getRoom() != null) {
            dto.setRoom(roomDtoDisassembler.disassemble(person.getRoom()));
            dto.setFloor(floorDtoDisassembler.disassemble(person.getRoom().getFloor()));
            dto.setHostel(hostelDtoDisassembler.disassemble(person.getRoom().getFloor().getHostel()));
        }

        if (person.getLink() != null) {
            dto.setFacebookLink(person.getLink().getFacebook());
            dto.setTwitterLink(person.getLink().getTwitter());
            dto.setVkLink(person.getLink().getVk());
            dto.setSkypeLink(person.getLink().getSkype());
            dto.setDevartLink(person.getLink().getDevart());
            dto.setVimeoLink(person.getLink().getVimeo());
            dto.setGoogleLink(person.getLink().getGoogle());
            dto.setYoutubeLink(person.getLink().getYoutube());
            dto.setLastfmLink(person.getLink().getLastfm());
        }
        return dto;
    }
}
