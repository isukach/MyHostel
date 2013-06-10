package by.bsuir.suite.page.duty;

import by.bsuir.suite.dto.duty.PersonDutyDto;
import by.bsuir.suite.service.duty.DutyService;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author i.sukach
 */
public class PersonDutyModel extends LoadableDetachableModel<PersonDutyDto> {

    private Long dutyId;

    @SpringBean
    private DutyService dutyService;

    public PersonDutyModel(Long dutyId) {
        Injector.get().inject(this);
        this.dutyId = dutyId;
    }

    public PersonDutyModel(PersonDutyDto dto) {
        Injector.get().inject(this);
        if (dto != null) {
            this.dutyId = dto.getId();
        } else {
            throw new IllegalArgumentException("Model object cannot be null!");
        }
    }

    @Override
    protected PersonDutyDto load() {
        return dutyService.getPersonDuty(dutyId);
    }
}
