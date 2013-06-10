package by.bsuir.suite.initializer.property;

import java.util.List;

/**
 * @author i.sukach
 */
public class HostelProperty {

    private List<FloorProperty> floors;

    private int number;

    private String address;

    private boolean isBlockType;

    private Integer rank;

    public List<FloorProperty> getFloors() {
        return floors;
    }

    public void setFloors(List<FloorProperty> floors) {
        this.floors = floors;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isBlockType() {
        return isBlockType;
    }

    public void setBlockType(boolean blockType) {
        isBlockType = blockType;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}
