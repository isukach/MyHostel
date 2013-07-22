package by.bsuir.suite.dto.work;

import by.bsuir.suite.dto.common.Dto;
import by.bsuir.suite.dto.person.PersonJobOfferDto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * User: Matveyenka Denis
 * Date: 20.06.13
 */
public class JobOfferDto implements Dto {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Date date;

    private String description;

    private int hours;

    private int numberOfPeoples;

    private boolean active = true;

    private int personJobOfferSize;

    private Set<PersonJobOfferDto> personDtos = new HashSet<PersonJobOfferDto>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getNumberOfPeoples() {
        return numberOfPeoples;
    }

    public void setNumberOfPeoples(int numberOfPeoples) {
        this.numberOfPeoples = numberOfPeoples;
    }

    public int getPersonJobOfferSize() {
        return personJobOfferSize;
    }

    public void setPersonJobOfferSize(int personJobOfferSize) {
        this.personJobOfferSize = personJobOfferSize;
    }

    public Set<PersonJobOfferDto> getPersonDtos() {
        return personDtos;
    }

    public void setPersonDtos(Set<PersonJobOfferDto> personDtos) {
        this.personDtos = personDtos;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
