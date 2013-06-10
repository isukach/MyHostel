package by.bsuir.suite.domain.lan;

import by.bsuir.suite.dao.lan.LanUser;

import javax.persistence.*;
import javax.persistence.Entity;

/**
 * @author i.sukach
 */
@Entity
@Table(name = "hna_pays")
public class LanPayment implements by.bsuir.suite.domain.Entity {

    private Long id;

    private LanUser user;

    private int year;

    private boolean connected;

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

    @Id
    @Column(name = "pay_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    public LanUser getUser() {
        return user;
    }

    public void setUser(LanUser user) {
        this.user = user;
    }

    @Column(name = "year")
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Column(name = "connect")
    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    @Column(name = "1")
    public boolean isJanuary() {
        return january;
    }

    public void setJanuary(boolean january) {
        this.january = january;
    }

    @Column(name = "2")
    public boolean isFebruary() {
        return february;
    }

    public void setFebruary(boolean february) {
        this.february = february;
    }

    @Column(name = "3")
    public boolean isMarch() {
        return march;
    }

    public void setMarch(boolean march) {
        this.march = march;
    }

    @Column(name = "4")
    public boolean isApril() {
        return april;
    }

    public void setApril(boolean april) {
        this.april = april;
    }

    @Column(name = "5")
    public boolean isMay() {
        return may;
    }

    public void setMay(boolean may) {
        this.may = may;
    }

    @Column(name = "6")
    public boolean isJune() {
        return june;
    }

    public void setJune(boolean june) {
        this.june = june;
    }

    @Column(name = "7")
    public boolean isJuly() {
        return july;
    }

    public void setJuly(boolean july) {
        this.july = july;
    }

    @Column(name = "8")
    public boolean isAugust() {
        return august;
    }

    public void setAugust(boolean august) {
        this.august = august;
    }

    @Column(name = "9")
    public boolean isSeptember() {
        return september;
    }

    public void setSeptember(boolean september) {
        this.september = september;
    }

    @Column(name = "10")
    public boolean isOctober() {
        return october;
    }

    public void setOctober(boolean october) {
        this.october = october;
    }

    @Column(name = "11")
    public boolean isNovember() {
        return november;
    }

    public void setNovember(boolean november) {
        this.november = november;
    }

    @Column(name = "12")
    public boolean isDecember() {
        return december;
    }

    public void setDecember(boolean december) {
        this.december = december;
    }
}
