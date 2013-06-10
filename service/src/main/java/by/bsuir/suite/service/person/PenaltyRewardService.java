package by.bsuir.suite.service.person;

import by.bsuir.suite.dto.person.PenaltyRewardDto;

import java.util.List;

/**
 * @author a.garelik
 */
public interface PenaltyRewardService {

    List<PenaltyRewardDto>  getAll();

    void save(PenaltyRewardDto dto);

    void remove(Long id);
}
