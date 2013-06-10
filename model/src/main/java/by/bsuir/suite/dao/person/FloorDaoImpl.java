package by.bsuir.suite.dao.person;

import by.bsuir.suite.dao.common.GenericDaoImpl;
import by.bsuir.suite.domain.person.Floor;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author i.sukach
 */
@Repository
public class FloorDaoImpl extends GenericDaoImpl<Floor> implements FloorDao {

    public FloorDaoImpl() {
        super(Floor.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Floor> findByHostelId(Long hostelId) {
        Criteria criteria  = getSession().createCriteria(getPersistentClass(), "floor");
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        if (hostelId != null) {
            criteria.createAlias("floor.hostel", "floorHostel");
            criteria.add(Restrictions.eq("floorHostel.id", hostelId));
        }
        return criteria.list();
    }

    @Override
    public int getFloorNumberByPersonId(long id) {
        Query query = getSession().createSQLQuery(" SELECT number FROM floor WHERE id =" +
                "(SELECT floor_id FROM room WHERE id = " +
                    "(SELECT room_id FROM person WHERE id ="+id+" ))");
        String result = (String)query.uniqueResult();
        int floor = -1;
        if (result != null) {
            floor =Integer.valueOf(result);
        }
        return floor;
    }
}
