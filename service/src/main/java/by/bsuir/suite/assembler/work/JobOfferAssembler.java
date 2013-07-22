package by.bsuir.suite.assembler.work;

import by.bsuir.suite.assembler.common.Assembler;
import by.bsuir.suite.dao.person.PersonDao;
import by.bsuir.suite.dao.work.JobOfferDao;
import by.bsuir.suite.domain.person.Person;
import by.bsuir.suite.domain.work.JobOffer;
import by.bsuir.suite.dto.person.PersonJobOfferDto;
import by.bsuir.suite.dto.work.JobOfferDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * User: Matveyenka Denis
 * Date: 20.06.13
 */
@Component
public class JobOfferAssembler implements Assembler<JobOfferDto, JobOffer> {

    @Autowired
    private PersonDao personDao;

    @Override
    public JobOffer assemble(JobOfferDto dto) {
        JobOffer jobOffer = new JobOffer();
        jobOffer.setDate(dto.getDate());
        jobOffer.setDescription(dto.getDescription());
        jobOffer.setHours(dto.getHours());
        jobOffer.setNumberOfPeople(dto.getNumberOfPeoples());
        jobOffer.setPersons(getPersonFromDto(dto));
        jobOffer.setActive(dto.isActive());

        return jobOffer;
    }

    @Override
    public JobOffer updateEntityFields(JobOfferDto dto, JobOffer entity) {
        entity.setDate(dto.getDate());
        entity.setDescription(dto.getDescription());
        entity.setHours(dto.getHours());
        entity.setNumberOfPeople(dto.getNumberOfPeoples());
        entity.setPersons(getPersonFromDto(dto));
        entity.setActive(dto.isActive());

        return entity;
    }

    private Set<Person> getPersonFromDto(JobOfferDto dto) {
        Set<Person> persons = null;
        if (dto.getPersonDtos() != null) {
            persons = new HashSet<Person>();
            for (PersonJobOfferDto item : dto.getPersonDtos()) {
                Person person = personDao.get(item.getId());
                persons.add(person);
            }
        }

        return persons;
    }
}
