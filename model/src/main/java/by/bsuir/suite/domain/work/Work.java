package by.bsuir.suite.domain.work;

import by.bsuir.suite.domain.BaseEntity;
import by.bsuir.suite.domain.person.Person;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author d.matveenko
 */
@Entity
@Table(name = "work")
public class Work extends BaseEntity {

    private int requiredHours;

    private int penaltyHours = 0;

    private Set<Job> jobs = new HashSet<Job>();

    private Person person;

    @Transient
    public int getTotalRequiredHours() {
        return getRequiredHours() + getPenaltyHours();
    }

    @Column(name = "requiredHours")
    public int getRequiredHours() {
        return requiredHours;
    }

    public void setRequiredHours(int requiredHours) {
        this.requiredHours = requiredHours;
    }

    @Column(name = "penaltyHours")
    public int getPenaltyHours() {
        return penaltyHours;
    }

    public void setPenaltyHours(int penaltyHours) {
        this.penaltyHours = penaltyHours;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "work")
    public Set<Job> getJobs() {
        return jobs;
    }

    public void setJobs(Set<Job> jobs) {
        this.jobs = jobs;
    }

    @JoinColumn(name = "person_id", referencedColumnName = "id")
    @OneToOne(optional = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
