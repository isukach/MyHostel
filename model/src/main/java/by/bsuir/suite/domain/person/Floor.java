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
@Entity
@Table(name = "floor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Floor extends BaseEntity {
    private String number;
    private String floorHead;
    private String educator;
    private Integer roomNumber;
    private Hostel hostel;
    private Set<Room> rooms = new HashSet<Room>();

    public Floor() {
    }

    public Floor(String number, Integer roomNumber) {
        this.number = number;
        this.roomNumber = roomNumber;
    }

    @Column(name = "number")
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
    @Column(name = "floor_head")
    public String getFloorHead() {
        return floorHead;
    }

    public void setFloorHead(String floorHead) {
        this.floorHead = floorHead;
    }
    @Column(name = "educator")
    public String getEducator() {
        return educator;
    }

    public void setEducator(String educator) {
        this.educator = educator;
    }

    @Column(name = "rooms")
    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "floor")
    public Set<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "hostel_id")
    public Hostel getHostel() {
        return hostel;
    }

    public void setHostel(Hostel hostel) {
        this.hostel = hostel;
    }
}
