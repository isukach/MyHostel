package by.bsuir.suite.page.duty;

import by.bsuir.suite.dto.duty.PersonDutyDto;
import by.bsuir.suite.service.duty.DutyService;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author i.sukach
 */
public class PersonDutyDataProvider implements IDataProvider<PersonDutyDto> {

    private Long personId;

    @SpringBean
    private DutyService dutyService;

    public PersonDutyDataProvider(Long personId) {
        Injector.get().inject(this);
        if (personId == null) {
            throw new IllegalArgumentException("Person id cannot be null in provider");
        }
        this.personId = personId;
    }

    @Override
    public Iterator<PersonDutyDto> iterator(int first, int count) {
        List<PersonDutyDto> duties = dutyService.getDutiesForPerson(personId);
        Collections.sort(duties);
        return duties.iterator();
    }

    @Override
    public int size() {
        return dutyService.getUnskippedCountForPerson(personId).intValue();
    }

    @Override
    public IModel<PersonDutyDto> model(PersonDutyDto dto) {
        return new PersonDutyModel(dto);
    }

    @Override
    public void detach() {
    }
}
