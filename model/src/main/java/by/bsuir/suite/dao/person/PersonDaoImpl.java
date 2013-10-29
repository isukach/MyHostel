package by.bsuir.suite.dao.person;

import by.bsuir.suite.constants.PersonSearchConstants;
import by.bsuir.suite.dao.common.GenericDaoImpl;
import by.bsuir.suite.domain.person.Person;
import by.bsuir.suite.domain.person.ResidenceStatus;
import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static org.hibernate.criterion.Restrictions.*;

/**
 * @author i.sukach
 */
@Repository
public class PersonDaoImpl extends GenericDaoImpl<Person> implements PersonDao {

    private static final String ROOM_SORT = "room";

    public PersonDaoImpl() {
        super(Person.class);
    }

    @Override
    public Person getPersonByUsername(String username) {
        if (username == null) {
            throw new IllegalArgumentException("No username provided!");
        }
        Criteria criteria = getSession().createCriteria(getPersistentClass(), "person");
        criteria.createAlias("person.user", "personUser");
        criteria.add(Restrictions.eq("personUser.username", username));
        return (Person) criteria.uniqueResult();
    }

    @Override
    public Long getFloorIdByUserName(String username) {
        return (Long) getSession().getNamedQuery(Person.GET_FLOOR_ID_BY_USERNAME)
                .setParameter("username", username).uniqueResult();
    }

    @Override
    public List<Person> findByRoomId(Long roomId) {
        Criteria criteria = getSession().createCriteria(getPersistentClass(), "person");
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        if (roomId != null) {
            criteria.createAlias("person.room", "personRoom");
            criteria.add(Restrictions.eq("personRoom.id", roomId));
        }
        return criteria.list();
    }

    @Override
    public List<Person> findByFloorId(long floorId, String sortBy, boolean isAscending) {
        Criteria criteria = getSession().createCriteria(getPersistentClass(), "person");
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.createAlias("person.room", "personRoom");
        criteria.createAlias("personRoom.floor", "personFloor");
        criteria.add(Restrictions.eq("personFloor.id", floorId));

        if (sortBy != null) {
            if (sortBy.equals(ROOM_SORT)) {
                criteria.addOrder(
                        isAscending ? Order.asc("personRoom.number") : Order.desc("personRoom.number"));
            } else {
                criteria.addOrder(
                        isAscending ? Order.asc(sortBy) : Order.desc(sortBy));
            }
        }

        return criteria.list();
    }

    @Override
    public List<Person> searchForPerson(int from, String search) {
        Criteria criteria = getSession().createCriteria(getPersistentClass(), "person");
        criteria.setFirstResult(from).setMaxResults(PersonSearchConstants.NUMBER_ITEMS_ON_PAGE);
        List<Person> persons = new ArrayList<Person>();
        if(search != null && !search.equals("")) {
            if (NumberUtils.isNumber(search.substring(0, 1))) {
                if (search.length() == 3) {
                    String floor = search.substring(0, 1);
                    String room = search.substring(1, 3);

                    persons.addAll(getPersonsFromRoom(floor, room));
                }
                if (search.length() == 4) {
                    String floor = search.substring(0, 2);
                    String room = search.substring(2, 4);

                    persons.addAll(getPersonsFromRoom(floor, room));
                }
            } else {
                Conjunction conjunction = createMainQueryForPersonSearch(search);
                criteria.add(conjunction);
                persons.addAll(criteria.list());
            }
        }

        return persons;
    }

    @Override
    public Long personSearchCount(String search) {
        Criteria criteria = getSession().createCriteria(getPersistentClass(), "person");
        Long personCount = 0L;
        if(search != null && !search.equals("")) {
            if (NumberUtils.isNumber(search.substring(0, 1))) {
                if (search.length() == 3) {
                    String floor = search.substring(0, 1);
                    String room = search.substring(1, 3);

                    personCount = getCountPersonsFromRoom(floor, room);
                }
                if (search.length() == 4) {
                    String floor = search.substring(0, 2);
                    String room = search.substring(2, 4);

                    personCount = getCountPersonsFromRoom(floor, room);
                }
            } else {
                Conjunction conjunction = createMainQueryForPersonSearch(search);
                criteria.add(conjunction);
                personCount = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
            }
        }

        return personCount;
    }

    @Override
    public List getActivePersonIds() {
        return getSession().createSQLQuery("SELECT id FROM person").list();
    }

    @Override
    public int getCountForFloor(Long floorId) {
        Criteria criteria = getSession().createCriteria(getPersistentClass(), "person");
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.createAlias("person.room", "personRoom");
        criteria.createAlias("personRoom.floor", "personFloor");
        criteria.add(Restrictions.eq("personFloor.id", floorId));

        return ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
    }

    private Conjunction createMainQueryForPersonSearch(String search) {
        Conjunction conjunction = conjunction();
        conjunction.add(not(eq("person.residenceStatus", ResidenceStatus.ADMINISTRATION)));

        String[] fio = search.split(" ");
        if (fio.length > 1) {
            String first = "%" + fio[ 0 ] + "%";
            String second = "%" + fio[ 1 ] + "%";

            conjunction.add(or(and(like("person.firstName", first), like("person.lastName", second)),
                    and(like("person.firstName", second), like("person.lastName", first))));
        } else {
            search = "%" + search + "%";
            conjunction.add(or(like("person.firstName", search), like("person.lastName", search)));
        }

        return conjunction;
    }

    private List<Person> getPersonsFromRoom(String floor, String room) {
        Criteria findRoomCriteria = getSession().createCriteria(getPersistentClass(), "person");
        findRoomCriteria.createAlias("person.room", "personRoom");
        findRoomCriteria.createAlias("personRoom.floor", "roomFloor");
        findRoomCriteria.add(and(eq("personRoom.number", room), eq("roomFloor.number", floor)));

        return findRoomCriteria.list();
    }

    private Long getCountPersonsFromRoom(String floor, String room) {
        Criteria findRoomCriteria = getSession().createCriteria(getPersistentClass(), "person");
        findRoomCriteria.createAlias("person.room", "personRoom");
        findRoomCriteria.createAlias("personRoom.floor", "roomFloor");
        findRoomCriteria.add(and(eq("personRoom.number", room), eq("roomFloor.number", floor)));

        return (Long) findRoomCriteria.setProjection(Projections.rowCount()).uniqueResult();
    }
}
