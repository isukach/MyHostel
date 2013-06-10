package by.bsuir.suite.page.lan;

import by.bsuir.suite.dto.lan.LanPaymentCellDto;
import org.apache.wicket.markup.repeater.data.ListDataProvider;

import java.util.List;

/**
 * @author : d.shemiarey
 */
public abstract class LanPaymentDataProvider extends ListDataProvider<LanPaymentCellDto> {

    public LanPaymentDataProvider() {
    }

    @Override
    protected List<LanPaymentCellDto> getData() {
        return getCells();
    }

    public abstract List<LanPaymentCellDto> getCells();
}
