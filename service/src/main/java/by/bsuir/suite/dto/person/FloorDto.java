package by.bsuir.suite.dto.person;

import by.bsuir.suite.dto.common.Dto;

/**
 * @author d.shemerey
 */
public class FloorDto implements Dto, Comparable<FloorDto> {

    private Long id;

    private String number;

    private int currentPopulation;

    private int maxPopulation;

    public Long getId() {
        return this.id;
    }

    public FloorDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNumber() {
        return this.number;
    }

    public FloorDto setNumber(String number) {
        this.number = number;
        return this;
    }

    public int getCurrentPopulation() {
        return this.currentPopulation;
    }

    public FloorDto setCurrentPopulation(int currentPopulation) {
        this.currentPopulation = currentPopulation;
        return this;
    }

    public int getMaxPopulation() {
        return this.maxPopulation;
    }

    public FloorDto setMaxPopulation(int maxPopulation) {
        this.maxPopulation = maxPopulation;
        return this;
    }

    @Override
    public int compareTo(FloorDto dto) {
        return this.number.compareTo(dto.getNumber());
    }
}
