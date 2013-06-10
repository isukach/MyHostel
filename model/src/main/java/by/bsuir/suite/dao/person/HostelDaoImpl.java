package by.bsuir.suite.dao.person;

import by.bsuir.suite.dao.common.GenericDaoImpl;
import by.bsuir.suite.domain.person.Hostel;
import org.springframework.stereotype.Repository;

/**
 * @author i.sukach
 */
@Repository
public class HostelDaoImpl extends GenericDaoImpl<Hostel> implements HostelDao {

    public HostelDaoImpl() {
        super(Hostel.class);
    }

    @Override
    public boolean checkHostelByNumber(int number) {
        long count = (Long) getSession().getNamedQuery(Hostel.GET_COUNT_BY_NUMBER)
                .setParameter("number", number).uniqueResult();
        return count != 0;
    }

    @Override
    public Hostel getHostelByNumber(int number) {
        return (Hostel) getSession().createQuery("from Hostel where number ="+number).uniqueResult();
    }
}
