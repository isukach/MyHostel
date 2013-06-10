package by.bsuir.suite.domain.duty;

import by.bsuir.suite.domain.BaseEntity;
import by.bsuir.suite.domain.person.Floor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author i.sukach
 */
@NamedQueries({
        @NamedQuery(
                name = Month.FIND_ENABLED_BY_FLOOR_ID,
                query = "SELECT m FROM Month m WHERE m.enabled = true AND m.floor.id = :floorId"
        )
})
@Entity
@Table(name = "month")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Month extends BaseEntity {

    public static final String FIND_ENABLED_BY_FLOOR_ID = "month.findEnabledByMonthId";

    private int month;

    private int year;

    private boolean enabled = false;

    private Floor floor;

    private Set<Duty> duties = new HashSet<Duty>();

    @Column(name = "month", nullable = false)
    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    @Column(name = "year", nullable = false)
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Column(columnDefinition = "tinyint", name = "enabled", nullable = false)
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "floor_id", nullable = true)
    public Floor getFloor() {
        return floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "month_duty",
            joinColumns = @JoinColumn(name = "month_id"),
            inverseJoinColumns = @JoinColumn(name = "duty_id")
    )
    public Set<Duty> getDuties() {
        return duties;
    }

    public void setDuties(Set<Duty> duties) {
        this.duties = duties;
    }
}
