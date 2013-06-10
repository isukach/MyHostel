package by.bsuir.suite.service.person;

import by.bsuir.suite.assembler.person.PenaltyRewardAssembler;
import by.bsuir.suite.dao.person.PenaltyRewardDao;
import by.bsuir.suite.dao.person.PersonDao;
import by.bsuir.suite.disassembler.person.PenaltyRewardDisassembler;
import by.bsuir.suite.domain.person.PenaltyReward;
import by.bsuir.suite.dto.person.PenaltyRewardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author a.garelik
 */
@Service
@Transactional
public class PenaltyRewardServiceImpl implements PenaltyRewardService
{
    @Autowired
    private PenaltyRewardDao penaltyRewardDao;
    @Autowired
    private PenaltyRewardDisassembler disassembler;
    @Autowired
    private PenaltyRewardAssembler assembler;
    @Autowired
    private PersonDao personDao;


    @Override
    public List<PenaltyRewardDto> getAll() {
        List<PenaltyRewardDto> result = new ArrayList<PenaltyRewardDto>();
        for (PenaltyReward entity : penaltyRewardDao.findAll()){
            result.add(disassembler.disassemble(entity));
        }
        return result;
    }

    @Override
    public void save(PenaltyRewardDto dto) {
        PenaltyReward entity = assembler.assemble(dto);
        entity.setPerson(personDao.get(dto.getPersonId()));
        penaltyRewardDao.update(entity);
    }

    @Override
    public void remove(Long id) {
        penaltyRewardDao.delete(
                penaltyRewardDao.get(id));
    }
}
