package by.bsuir.suite.page.work.model;

import org.apache.wicket.model.Model;

/**
 * @author d.matveenko
 */
public class ProgressBarModel extends Model {

    private Integer totalHours;

    private Integer requiredHours;

    public ProgressBarModel(Integer totalHours, Integer requiredHours) {
        this.totalHours = totalHours;
        this.requiredHours = requiredHours;
    }

    public Integer getTotalHours() {
        return totalHours;
    }

    public Integer getRequiredHours() {
        return requiredHours;
    }
}
