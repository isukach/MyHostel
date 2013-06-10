package by.bsuir.suite.domain.news;

import by.bsuir.suite.domain.BaseEntity;
import by.bsuir.suite.domain.person.Floor;
import by.bsuir.suite.domain.person.Person;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * @author a.garelik
 */

@Entity
@Table(name = "news")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class News extends BaseEntity {

    private NewsCategory category = NewsCategory.OTHER;
    private Timestamp timestamp;
    private String caption;
    private String text;
    private Person person;

    private Set<Floor> floors = new HashSet<Floor>(12);
    private Set<Course> courses = new HashSet<Course>(5);


    @Enumerated(value = EnumType.STRING)
    @Column(name = "category")
    public NewsCategory  getCategory() {
        return category;
    }

    public void setCategory(NewsCategory  category) {
        this.category = category;
    }


    @Column(name = "timestamp")
    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }


    @Column(name = "caption")
    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }


    @Column(name = "text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "news")
    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    @OneToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinTable(
            name = "floor_news",
            joinColumns = @JoinColumn(name = "news_id"),
            inverseJoinColumns = @JoinColumn(name = "floor_id")
    )
    public Set<Floor> getFloors() {
        return floors;
    }

    public void setFloors(Set<Floor> duties) {
        this.floors = duties;
    }

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

}
