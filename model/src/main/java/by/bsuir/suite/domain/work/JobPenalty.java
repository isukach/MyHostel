package by.bsuir.suite.domain.work;

import by.bsuir.suite.domain.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author d.matveenko
 */
@Entity
@Table(name = "jobPenalty")
public class JobPenalty extends BaseEntity {

    private Date date;

    private int hours;

    private String description;

    private Work work;

    @Column(name = "date")
    @Temporal(value = TemporalType.DATE)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name = "hours")
    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "work_id")
    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }
}
