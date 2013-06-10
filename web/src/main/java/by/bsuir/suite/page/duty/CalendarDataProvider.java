package by.bsuir.suite.page.duty;

import by.bsuir.suite.dto.duty.CalendarCellDto;
import org.apache.wicket.markup.repeater.data.ListDataProvider;

import java.util.List;

/**
 * @author i.sukach
 */
public class CalendarDataProvider extends ListDataProvider<CalendarCellDto> {

    private List<CalendarCellDto> list;

    public CalendarDataProvider(List<CalendarCellDto> list) {
        this.list = list;
    }

    @Override
    protected List<CalendarCellDto> getData() {
        return list;
    }
}
