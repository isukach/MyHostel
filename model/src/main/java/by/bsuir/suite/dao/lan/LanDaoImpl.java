package by.bsuir.suite.dao.lan;

import by.bsuir.suite.dao.common.GenericDaoImpl;
import by.bsuir.suite.domain.lan.Lan;
import org.springframework.stereotype.Repository;

/**
 * @author : d.shemiarey
 */
@Repository
public class LanDaoImpl extends GenericDaoImpl<Lan> implements LanDao {

    public LanDaoImpl() {
        super(Lan.class);
    }
}
