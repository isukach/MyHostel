package by.bsuir.suite.dto.person;

import by.bsuir.suite.dto.common.Dto;

/**
 * @author d.shemerey
 */
public class HostelDto implements Dto {

    private Long id;
    private Integer number;


    public Long getId() {
        return this.id;
    }

    public HostelDto setId(Long id) {
        this.id = id;
        return this;
    }

    public Integer getNumber() {
        return this.number;
    }

    public HostelDto setNumber(Integer number) {
        this.number = number;
        return this;
    }
}
