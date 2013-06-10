package by.bsuir.suite.dto.lan;

import by.bsuir.suite.dto.common.Dto;

/**
 * @author : d.shemiarey
 */
public class LanPaymentCellDto implements Dto {

    private String monthName;

    private boolean paid;

    public LanPaymentCellDto() {
    }

    public LanPaymentCellDto(String nameMonth) {
        monthName = nameMonth;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
