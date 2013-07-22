package by.bsuir.suite.dto.work;

import by.bsuir.suite.dto.common.Dto;
import by.bsuir.suite.dto.person.PersonJobOfferDto;

/**
 * User: Matveyenka Denis
 * Date: 21.07.13
 */
public class CommitJobOfferDto implements Dto {

    private PersonJobOfferDto personJobOfferDto;

    private int hours;

    private String description;

    public PersonJobOfferDto getPersonJobOfferDto() {
        return personJobOfferDto;
    }

    public void setPersonJobOfferDto(PersonJobOfferDto personJobOfferDto) {
        this.personJobOfferDto = personJobOfferDto;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
