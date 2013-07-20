package by.bsuir.suite.domain.person;

import by.bsuir.suite.domain.BaseEntity;
import by.bsuir.suite.domain.User;
import by.bsuir.suite.domain.duty.Duty;
import by.bsuir.suite.domain.lan.Lan;
import by.bsuir.suite.domain.work.Work;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Set;

/**
 * @author i.sukach
 */
@NamedQueries({
        @NamedQuery(
                name = Person.GET_FLOOR_ID_BY_USERNAME,
                query = "SELECT p.room.floor.id FROM Person p WHERE p.user.username = :username"
        )
})
@Entity
@Table(name = "person")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Person extends BaseEntity {

    public static final String GET_FLOOR_ID_BY_USERNAME = "person.getFloorIdByUsername";

    private String firstName;
    private String lastName;
    private String middleName;
    private Faculty faculty;
    private ResidenceStatus residenceStatus;
    private String universityGroup;
    private String about;
    private String email;
    private String phoneNumber;
    private String facilities;
    private String from;
    private String avatarPath;
    private User user;
    private Room room;
    private Link link;
    private Set<Duty> duties;
    private int requiredDuties;
    private int extraDuties;
    private Work work;
    private Lan lan;

    public Person() {
    }

    public Person(String firstName, String lastName, Faculty faculty, String universityGroup) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.faculty = faculty;
        this.universityGroup = universityGroup;
    }

    @Column(name = "firstName")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "lastName")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "middleName")
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Column(name = "faculty")
    @Enumerated(value = EnumType.STRING)
    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    @Column(name = "univ_group")
    public String getUniversityGroup() {
        return universityGroup;
    }

    public void setUniversityGroup(String group) {
        this.universityGroup = group;
    }

    @Column(name = "about")
    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "tel")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id")
    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @OneToOne(optional = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "person")
    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "person")
    public Set<Duty> getDuties() {
        return duties;
    }

    public void setDuties(Set<Duty> duties) {
        this.duties = duties;
    }

    @Column(name = "required_duties")
    public int getRequiredDuties() {
        return requiredDuties;
    }

    public void setRequiredDuties(int requiredDuties) {
        this.requiredDuties = requiredDuties;
    }

    @Column(name = "extra_duties")
    public int getExtraDuties() {
        return extraDuties;
    }

    public void setExtraDuties(int extraDuties) {
        this.extraDuties = extraDuties;
    }

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "person")
    public Lan getLan() {
        return lan;
    }

    public void setLan(Lan lan) {
        this.lan = lan;
    }

    @OneToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "person")
    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    @Column(name = "from_city")
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Column(name = "avatar_path")
    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    @Column(name = "facilities")
    public String getFacilities() {
        return facilities;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }

    @Column(name = "residence_status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    public ResidenceStatus getResidenceStatus() {
        return residenceStatus;
    }

    public void setResidenceStatus(ResidenceStatus residenceStatus) {
        this.residenceStatus = residenceStatus;
    }
}
