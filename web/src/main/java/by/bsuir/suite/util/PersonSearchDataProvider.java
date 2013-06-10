package by.bsuir.suite.util;

import by.bsuir.suite.dto.person.PersonSearchDto;
import by.bsuir.suite.service.person.PersonService;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import java.util.Iterator;

/**
 * @author d.matveenko
 */
public class PersonSearchDataProvider extends SortableDataProvider<PersonSearchDto> {
    
    private PersonService personService;
    
    private String search;
    
    public PersonSearchDataProvider(String search, PersonService personService) {
        this.personService = personService;
        this.search = search;
    }
    
    @Override
    public Iterator<? extends PersonSearchDto> iterator(int first, int count) {
        return personService.searchForPersons(first, search).iterator();
    }

    @Override
    public int size() {
        return personService.personSearchCount(search).intValue();
    }

    @Override
    public IModel<PersonSearchDto> model(final PersonSearchDto personSearchDto) {
        return new LoadableDetachableModel<PersonSearchDto>() {
            @Override
            protected PersonSearchDto load() {
                return personService.getPersonSearch(personSearchDto.getId());
            }
        };
    }
}
