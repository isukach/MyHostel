package by.bsuir.suite.dao.person;

import by.bsuir.suite.dao.common.GenericDaoImpl;
import by.bsuir.suite.domain.person.Link;
import org.springframework.stereotype.Repository;

/**
 * @author i.sukach
 */
@Repository
public class LinkDaoImpl extends GenericDaoImpl<Link> implements LinkDao {
    public LinkDaoImpl() {
        super(Link.class);
    }
}
