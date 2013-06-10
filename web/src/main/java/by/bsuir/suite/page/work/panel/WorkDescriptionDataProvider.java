package by.bsuir.suite.page.work.panel;

import by.bsuir.suite.dto.work.WorkDescriptionCellDto;
import org.apache.wicket.markup.repeater.data.ListDataProvider;

import java.util.List;

/**
 * @author : a.garelik
 */
public abstract class WorkDescriptionDataProvider extends ListDataProvider<WorkDescriptionCellDto> {

    public WorkDescriptionDataProvider() {
    }

    @Override
    protected List<WorkDescriptionCellDto> getData() {
        return getCells();
    }

    public abstract List<WorkDescriptionCellDto> getCells();
}
