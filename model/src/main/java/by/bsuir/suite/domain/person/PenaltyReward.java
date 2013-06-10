package by.bsuir.suite.domain.person;


import by.bsuir.suite.domain.BaseEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Date;

/**
 * @author a.garelik
 */
@Entity
@Table(name = "penalty_reward")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PenaltyReward extends BaseEntity {
    private PenaltyOrRewardEnum type;
    private String orderNumber;
    private Date date;
    private String comment;
    private Person person;

    public PenaltyReward() {
    }

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type")
    public PenaltyOrRewardEnum getType() {
        return type;
    }

    public void setType(PenaltyOrRewardEnum type) {
        this.type = type;
    }

    @Column(name = "orderNumber")
    public String getOrderNumber() {
        return orderNumber;
    }

    @Column(name = "orderNumber")
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name = "comment")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @JoinColumn(name = "person_id", referencedColumnName = "id")
    @OneToOne(optional = true, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

}
