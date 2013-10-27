package by.bsuir.suite.page.statistics;

import by.bsuir.suite.dto.statistics.FloorStatisticsDto;
import by.bsuir.suite.service.statistics.StatisticsService;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author i.sukach
 */
public class DetachableFloorStatisticsTableModel extends LoadableDetachableModel<FloorStatisticsDto> {

    private final long personId;

    @SpringBean
    private StatisticsService statisticsService;

    public DetachableFloorStatisticsTableModel(long personId) {
        Injector.get().inject(this);
        this.personId = personId;
    }

    @Override
    protected FloorStatisticsDto load() {
        return statisticsService.getByPersonId(personId);
    }

    @Override
    public int hashCode() {
        return Long.valueOf(personId).hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (obj instanceof DetachableFloorStatisticsTableModel) {
            DetachableFloorStatisticsTableModel other = (DetachableFloorStatisticsTableModel) obj;
            return other.personId == personId;
        }
        return false;
    }
}
