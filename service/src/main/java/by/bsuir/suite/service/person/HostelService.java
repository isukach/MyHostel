package by.bsuir.suite.service.person;

import by.bsuir.suite.dto.person.HostelDto;

import java.util.List;

/**
 * @author d.shemerey
 */
public interface HostelService {

    List<HostelDto>  getAll();

    HostelDto get(Long id);
}
