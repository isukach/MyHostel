package by.bsuir.suite.service.person;

import by.bsuir.suite.dao.person.HostelDao;
import by.bsuir.suite.disassembler.person.HostelDtoDisassembler;
import by.bsuir.suite.domain.person.Hostel;
import by.bsuir.suite.dto.person.HostelDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author d.shemerey
 */
@Service
@Transactional
public class HostelServiceImpl implements HostelService {

    @Autowired
    private HostelDtoDisassembler hostelDtoDisassembler;

    @Autowired
    private HostelDao hostelDao;

    @Override
    public List<HostelDto> getAll() {
         return hostelDtoDisassembler.disassembleToList(hostelDao.findAll());
    }

    @Override
    public HostelDto get(Long id) {
        Hostel hostel = hostelDao.get(id);
        if (hostel == null) {
            throw new IllegalArgumentException("Could not find hostel by provided id");
        }
        return hostelDtoDisassembler.disassemble(hostel);
    }
}
