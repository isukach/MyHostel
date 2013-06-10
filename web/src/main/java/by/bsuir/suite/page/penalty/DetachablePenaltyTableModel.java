package by.bsuir.suite.page.penalty;

import by.bsuir.suite.dto.penalty.PenaltyDto;
import by.bsuir.suite.service.penalty.PenaltyService;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * User: CHEB
 */
public class DetachablePenaltyTableModel extends LoadableDetachableModel<PenaltyDto> {

    @SpringBean
    @SuppressWarnings({"UnusedDeclaration"})
    private PenaltyService penaltyService;

    private final long id;


    public DetachablePenaltyTableModel(PenaltyDto penaltyDto) {
        this(penaltyDto.getDutyId());
        Injector.get().inject(this);
    }

    public DetachablePenaltyTableModel(long id) {
        if (id == 0) {
            throw new IllegalArgumentException();
        }
        this.id = id;
    }


    @Override
    protected PenaltyDto load() {
        return penaltyService.getPenaltyById(id);
    }

    @Override
    public int hashCode() {
        return Long.valueOf(id).hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (obj instanceof DetachablePenaltyTableModel) {
            DetachablePenaltyTableModel other = (DetachablePenaltyTableModel) obj;
            return other.id == id;
        }
        return false;
    }
}
