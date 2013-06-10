package by.bsuir.suite.domain.person;

import by.bsuir.suite.domain.BaseEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author i.sukach
 */
@NamedQueries(
        @NamedQuery(
                name = Room.GET_BY_HOSTEL_FLOOR_AND_ROOM_NUMBERS,
                query = "SELECT r FROM Room r WHERE r.number = :roomNumber"
                        + " AND r.floor.number = :floorNumber"
                        + " AND r.floor.hostel.number = :hostelNumber"
        )
)
@Entity
@Table(name = "room")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Room extends BaseEntity {

    public static final String GET_BY_HOSTEL_FLOOR_AND_ROOM_NUMBERS = "room.getByHostelFloorAndRoomNumbers";

    public static final int MAX_ROOM_POPULATION = 4;

    private String number;
    private Integer personNumber;
    private Floor floor;
    private Set<Person> persons = new HashSet<Person>();

    public Room() {
    }

    public Room(String number, Integer personNumber) {
        this.number = number;
        this.personNumber = personNumber;
    }

    @Column(name = "number")
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Column(name = "persons")
    public Integer getPersonNumber() {
        return personNumber;
    }

    public void setPersonNumber(Integer personNumber) {
        this.personNumber = personNumber;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "room")
    public Set<Person> getPersons() {
        return persons;
    }

    public void setPersons(Set<Person> persons) {
        this.persons = persons;
    }

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "floor_id")
    public Floor getFloor() {
        return floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }
}

