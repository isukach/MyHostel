package by.bsuir.suite.dao.person;

import by.bsuir.suite.dao.common.GenericDaoImpl;
import by.bsuir.suite.domain.person.PenaltyReward;
import org.springframework.stereotype.Repository;

/**
 * @author i.sukach
 */
@Repository
public class PenaltyRewardDaoImpl extends GenericDaoImpl<PenaltyReward> implements PenaltyRewardDao {

    public PenaltyRewardDaoImpl() {
        super(PenaltyReward.class);
    }
}
