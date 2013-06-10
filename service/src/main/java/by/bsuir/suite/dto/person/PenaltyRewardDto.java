package by.bsuir.suite.dto.person;


import by.bsuir.suite.domain.person.PenaltyOrRewardEnum;
import by.bsuir.suite.dto.common.Dto;

import java.sql.Date;

/**
 * @author a.garelik
 */

public class PenaltyRewardDto implements Dto {

    private Long id;
    private PenaltyOrRewardEnum type;
    private String orderNumber;
    private Date date;
    private String comment;
    private Long personId;

    public PenaltyRewardDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public PenaltyOrRewardEnum getType() {
        return type;
    }

    public void setType(PenaltyOrRewardEnum type) {
        this.type = type;
    }


    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }
}
