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
@NamedQueries(value = {
        @NamedQuery(
            name = Hostel.GET_COUNT_BY_NUMBER,
            query = "SELECT COUNT(h) FROM Hostel h WHERE h.number = :number"
        )
})
@Entity
@Table(name = "hostel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Hostel extends BaseEntity {

    public static final String GET_COUNT_BY_NUMBER = "hostel.getCountByNumber";

    private Integer number;
    private String address;
    private Integer stages;
    private boolean isBlockType;
    private Integer rank;
    private Set<Floor> floors = new HashSet<Floor>();

    public Hostel() {
    }

    public Hostel(Integer number, String address, Integer stages, boolean isBlockType, Integer rank) {
        this.number = number;
        this.address = address;
        this.stages = stages;
        this.isBlockType = isBlockType;
        this.rank = rank;
    }

    @Column(name = "number", unique = true)
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "stages")
    public Integer getStages() {
        return stages;
    }

    public void setStages(Integer stages) {
        this.stages = stages;
    }

    @Column(columnDefinition = "tinyint", name = "isBlockType")
    public boolean getIsBlockType() {
        return isBlockType;
    }

    public void setIsBlockType(boolean isBlockType) {
        this.isBlockType = isBlockType;
    }

    @Column(name = "rank")
    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "hostel")
    public Set<Floor> getFloors() {
        return floors;
    }

    public void setFloors(Set<Floor> floors) {
        this.floors = floors;
    }
}
