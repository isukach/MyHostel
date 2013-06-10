package by.bsuir.suite.domain.lan;

import by.bsuir.suite.domain.BaseEntity;
import by.bsuir.suite.domain.person.Person;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * @author : d.shemiarey
 */
@Entity
@Table(name = "lan")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Lan extends BaseEntity {

    private String ip;

    private boolean activated = false;

    private Person person;

    private boolean january = false;
    private boolean february = false;
    private boolean march = false;
    private boolean april = false;
    private boolean may = false;
    private boolean june = false;
    private boolean july = false;
    private boolean august = false;
    private boolean september = false;
    private boolean october = false;
    private boolean november = false;
    private boolean december = false;

    private int year;

    private Long version;

    @Column(name = "ip", nullable = true)
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Column(name = "january")
    public boolean isJanuary() {
        return january;
    }

    public void setJanuary(boolean january) {
        this.january = january;
    }

    @Column(name = "february")
    public boolean isFebruary() {
        return february;
    }

    public void setFebruary(boolean february) {
        this.february = february;
    }

    @Column(name = "march")
    public boolean isMarch() {
        return march;
    }

    public void setMarch(boolean march) {
        this.march = march;
    }

    @Column(name = "april")
    public boolean isApril() {
        return april;
    }

    public void setApril(boolean april) {
        this.april = april;
    }

    @Column(name = "may")
    public boolean isMay() {
        return may;
    }

    public void setMay(boolean may) {
        this.may = may;
    }

    @Column(name = "june")
    public boolean isJune() {
        return june;
    }

    public void setJune(boolean june) {
        this.june = june;
    }

    @Column(name = "july")
    public boolean isJuly() {
        return july;
    }

    public void setJuly(boolean july) {
        this.july = july;
    }

    @Column(name = "august")
    public boolean isAugust() {
        return august;
    }

    public void setAugust(boolean august) {
        this.august = august;
    }

    @Column(name = "september")
    public boolean isSeptember() {
        return september;
    }

    public void setSeptember(boolean september) {
        this.september = september;
    }

    @Column(name = "october")
    public boolean isOctober() {
        return october;
    }

    public void setOctober(boolean october) {
        this.october = october;
    }

    @Column(name = "november")
    public boolean isNovember() {
        return november;
    }

    public void setNovember(boolean november) {
        this.november = november;
    }

    @Column(name = "december")
    public boolean isDecember() {
        return december;
    }

    public void setDecember(boolean december) {
        this.december = december;
    }

    @Column(name = "year",  nullable = false)
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Column(name = "activated")
    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    @JoinColumn(name = "person_id", referencedColumnName = "id")
    @OneToOne(optional = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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
}
