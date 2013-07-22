package by.bsuir.suite.domain.work;

import by.bsuir.suite.domain.BaseEntity;
import by.bsuir.suite.domain.person.Person;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * User: Matveyenka Denis
 * Date: 20.06.13
 */
@Entity
@Table(name = "job_offer")
public class JobOffer extends BaseEntity {

    private Date date;

    private String description;

    private int hours;

    private int numberOfPeople;

    private boolean active;

    private Set<Person> persons;

    @Column(name = "date")
    @Temporal(value = TemporalType.DATE)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "hours")
    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    @Column(name = "number_of_people")
    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    @Column(columnDefinition = "tinyint", name = "isActive")
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name="person_job_offer",
            joinColumns = @JoinColumn(name="jobOffer_id"),
            inverseJoinColumns = @JoinColumn(name="person_id")
    )
    public Set<Person> getPersons() {
        return persons;
    }

    public void setPersons(Set<Person> persons) {
        this.persons = persons;
    }
}
