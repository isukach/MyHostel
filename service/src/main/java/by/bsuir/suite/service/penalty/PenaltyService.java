package by.bsuir.suite.service.penalty;

import by.bsuir.suite.dto.penalty.ClosePenaltyDto;
import by.bsuir.suite.dto.penalty.PenaltyDto;

import java.util.List;

/**
 * User: CHEB
 */
public interface PenaltyService {

    Long getPenaltyCount();

    List<PenaltyDto> findPenalty(int first, int count, String sortBy);

    PenaltyDto getPenaltyById(Long id);

    void closePenalty(ClosePenaltyDto closePenaltyDto);

    void makePunishment(ClosePenaltyDto closePenaltyDto);

}
