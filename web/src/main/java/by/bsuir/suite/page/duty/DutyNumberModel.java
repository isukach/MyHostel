package by.bsuir.suite.page.duty;

import org.apache.wicket.model.Model;

/**
 * @author i.sukach
 */
public class DutyNumberModel extends Model {

    private Integer dutyNumber;

    private Integer maxDutyNumber;

    public DutyNumberModel(Integer dutyNumber, Integer maxDutyNumber) {
        this.dutyNumber = dutyNumber;
        this.maxDutyNumber = maxDutyNumber;
    }

    public String getDutyNumber() {
        return dutyNumber.toString();
    }

    public String getMaxDutyNumber() {
        return maxDutyNumber.toString();
    }
}
