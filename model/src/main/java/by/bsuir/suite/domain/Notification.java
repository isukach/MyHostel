package by.bsuir.suite.domain;

import by.bsuir.suite.domain.person.Person;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @autor a.garelik
 * Date: 27/07/13
 * Time: 16:23
 */

@javax.persistence.Entity
@Table(name = "notification")
public class Notification extends BaseEntity {

    private String text;
    private String header;
    private Boolean viewed;
    private Timestamp date;
    private NotificationType entityType;
    private Person person;
    private String headerParams;
    private String textParams;

    @Column(name = "header_params")
    public String getHeaderParams() {
        return headerParams;
    }

    public void setHeaderParams(String headerParams) {
        this.headerParams = headerParams;
    }

    @Column(name = "text_params")
    public String getTextParams() {
        return textParams;
    }

    public void setTextParams(String textParams) {
        this.textParams = textParams;
    }

    @Column(name = "text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Column(name = "header")
    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    @Column(name = "viewed")
    public Boolean getViewed() {
        return viewed;
    }

    public void setViewed(Boolean viewed) {
        this.viewed = viewed;
    }

    @Column(name = "date")
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type")
    public NotificationType getEntityType() {
        return entityType;
    }

    public void setEntityType(NotificationType entityType) {
        this.entityType = entityType;
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
