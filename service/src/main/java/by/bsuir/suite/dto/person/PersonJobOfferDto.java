package by.bsuir.suite.dto.person;

import by.bsuir.suite.dto.common.Dto;

/**
 * User: Matveyenka Denis
 * Date: 21.07.13
 */
public class PersonJobOfferDto implements Dto {

    private Long id;

    private String fullName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
