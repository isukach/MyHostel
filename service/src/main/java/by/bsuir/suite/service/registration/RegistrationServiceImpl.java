package by.bsuir.suite.service.registration;

import by.bsuir.suite.assembler.registration.StaffRegistrationDtoAssembler;
import by.bsuir.suite.assembler.registration.RoomerRegistrationDtoAssembler;
import by.bsuir.suite.dao.person.PersonDao;
import by.bsuir.suite.domain.person.Person;
import by.bsuir.suite.dto.registration.StaffRegistrationDto;
import by.bsuir.suite.dto.registration.RoomerRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author i.sukach
 */
@Service
@Transactional
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private RoomerRegistrationDtoAssembler roomerRegistrationDtoAssembler;

    @Autowired
    private StaffRegistrationDtoAssembler staffRegistrationDtoAssembler;

    @Autowired
    private PersonDao personDao;

    @Override
    public void registerRoomer(RoomerRegistrationDto dto) {
        Person roomer = roomerRegistrationDtoAssembler.assemble(dto);
        personDao.create(roomer);
    }

    @Override
    public void registerAdministrator(StaffRegistrationDto dto) {
        Person admin = staffRegistrationDtoAssembler.assemble(dto);
        personDao.create(admin);
    }
}
