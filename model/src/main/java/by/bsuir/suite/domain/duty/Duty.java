package by.bsuir.suite.domain.duty;

import by.bsuir.suite.domain.BaseEntity;
import by.bsuir.suite.domain.person.Person;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;

/**
 * @author i.sukach
 */
@Entity
@Table(name = "duty")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Duty extends BaseEntity {

    private Date date;

    private DutyShift shift;

    private DutyStatus status = DutyStatus.FREE;

    private String statusComment;

    private Person person;

    private Long version;

    private boolean closed = false;

    @Column(name = "date", nullable = false)
    @Temporal(value = TemporalType.DATE)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Enumerated(value = EnumType.STRING)
    @Column(name = "shift", nullable = false)
    public DutyShift getShift() {
        return shift;
    }

    public void setShift(DutyShift shift) {
        this.shift = shift;
    }

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", nullable = false)
    public DutyStatus getStatus() {
        return status;
    }

    public void setStatus(DutyStatus status) {
        this.status = status;
    }

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Version
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Column(name = "status_comment")
    public String getStatusComment() {
        return statusComment;
    }

    public void setStatusComment(String statusComment) {
        this.statusComment = statusComment;
    }

    @Column(name = "closed")
    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }
}
